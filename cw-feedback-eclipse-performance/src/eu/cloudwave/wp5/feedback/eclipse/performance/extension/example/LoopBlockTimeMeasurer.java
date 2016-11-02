package eu.cloudwave.wp5.feedback.eclipse.performance.extension.example;

import java.util.List;

import com.google.common.collect.Lists;

import eu.cloudwave.wp5.feedback.eclipse.performance.core.builders.participants.CriticalLoopBuilderParticipant.ProcedureExecutionData;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.properties.PerformanceFeedbackProperties;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ProgrammMarkerContext;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.Expression;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.Invocation;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.LoopStatement;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.visitor.ProgrammMarkerVisitor;
import eu.cloudwave.wp5.feedback.eclipse.performance.infrastructure.config.PerformanceConfigs;

class LoopBlockTimeMeasurer extends ProgrammMarkerVisitor{
	
    @FunctionalInterface public interface ExcecutionTimeSummaryCallBack{
		  public void callBack(double avgExecTimePerIteration,List<ProcedureExecutionData> procedureExecutionTimes);
    }
	  
	
	private final ExcecutionTimeSummaryCallBack cb;
	private final ProgrammMarkerContext context;
	private final List<Expression> headerExprs; //ether the foreach source or the initializer
  	private double avgExecTimePerIteration = 0.0;
  	private List<ProcedureExecutionData> procedureExecutionTimes = Lists.newArrayList();
 	
	public LoopBlockTimeMeasurer(ProgrammMarkerContext context, List<Expression> headerExprs, ExcecutionTimeSummaryCallBack cb) {
		this.cb = cb;
		this.context = context;
		this.headerExprs = headerExprs;
	}



	@Override
	public ProgrammMarkerVisitor visit(Expression expr) {
		//later, create a new one which measures once
		if(headerExprs.contains(expr)) return SKIP_CHILDS; 	//Todo: latter add to some count once

		return CONTINUE;
	}



	@Override
	public ProgrammMarkerVisitor visit(Invocation invocation) {
		if(visit((Expression)invocation) == SKIP_CHILDS) return SKIP_CHILDS; 	//Todo: latter add to some count once
		List<Double> tags = invocation.getDoubleTags(CriticalLoopProgrammMarker.AVG_EXEC_TIME_TAG);
		if(tags.isEmpty()) return CONTINUE;
		double avgExecTime = 0.0;
		for(double avgT : tags)avgExecTime+=avgT;
		avgExecTime /= tags.size();
		avgExecTimePerIteration+= avgExecTime;
    	procedureExecutionTimes.add(ProcedureExecutionData.of(invocation.createCorrespondingMethodLocation(), avgExecTime));
		return CONTINUE;
	}
	
	@Override
	public ProgrammMarkerVisitor visit(LoopStatement loop) {
		  Double averageSize = CriticalLoopProgrammMarker.findNumOfIterations(loop, context);
		  if(averageSize == null) return CONTINUE;
		  if(averageSize == 0) return SKIP_CHILDS;

		  final double avgSize = averageSize; //so we can use it in inner
		  final double threshold = context.getProject().getFeedbackProperties().getDouble(PerformanceFeedbackProperties.TRESHOLD__LOOPS, PerformanceConfigs.DEFAULT_THRESHOLD_LOOPS);

		  return new LoopBlockTimeMeasurer(context,loop.getInitExpressions(),(avgExecTimePerIteration, procedureExecutionTimes) ->{

	         //todo: correct? should it be total?
         	 if (avgExecTimePerIteration >= threshold) {
         		//Create independent marker if higher then threshold 
         		CriticalLoopProgrammMarker.createCriticalLoopMarker(loop,context.getTemplateHandler(),avgSize,avgExecTimePerIteration,procedureExecutionTimes);
         	 }
         	 //Integrate results into current loop
         	 this.avgExecTimePerIteration+=(avgExecTimePerIteration*avgSize);
         	 //Todo: Add one with ExcecutedLoop instead of Methode
         	 this.procedureExecutionTimes.addAll(procedureExecutionTimes);
	    });
	}

	@Override
	public void finish() {
      cb.callBack(avgExecTimePerIteration, procedureExecutionTimes);
	}
  }