package eu.cloudwave.wp5.feedback.eclipse.performance.extension.example;

import java.util.List;
import java.util.Map;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import eu.cloudwave.wp5.common.util.Numbers;
import eu.cloudwave.wp5.common.util.TimeValues;
import eu.cloudwave.wp5.feedback.eclipse.base.infrastructure.template.TemplateHandler;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.MarkerAttributes;
import eu.cloudwave.wp5.feedback.eclipse.performance.Ids;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.builders.participants.CriticalLoopBuilderParticipant.ProcedureExecutionData;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.markers.PerformanceMarkerTypes;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.properties.PerformanceFeedbackProperties;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ProgrammMarker;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ProgrammMarkerContext;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.IAstNode;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.Invocation;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.LoopStatement;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.visitor.ProgrammMarkerVisitor;
import eu.cloudwave.wp5.feedback.eclipse.performance.infrastructure.config.PerformanceConfigs;

//TODO: DOES NOT WORK FIND OUT WHY
public class CriticalLoopProgrammMarker implements ProgrammMarker{
	

	  private static final String PROCEDURE_EXECUTIONS = "procedureExecutions";
	  private static final String AVG_TIME_PER_ITERATION = "avgTimePerIteration";
	  private static final String AVG_INTERATIONS = "avgInterations";
	  private static final String AVG_TOTAL = "avgTotal";
	  private static final String LOOP = "loop";
	  private static final int DECIMAL_PLACES = 3;
	  private static final String MESSAGE_PATTERN = "Critical Loop: Average Total Time is %s (Average Iterations: %s).";
	  
	  public static void createCriticalLoopMarker(LoopStatement loop, TemplateHandler template, double averageSize, double avgExecTimePerIteration, List<ProcedureExecutionData> procedureExecutionTimes ){
		  final Double avgTotalExecTime = averageSize * avgExecTimePerIteration;
          final String avgIterationsText = new Double(Numbers.round(averageSize, DECIMAL_PLACES)).toString();
          final String avgExecTimePerIterationText = TimeValues.toText(avgExecTimePerIteration, DECIMAL_PLACES);
          final String avgTotalExecTimeText = TimeValues.toText(avgTotalExecTime, DECIMAL_PLACES);
          final String msg = String.format(MESSAGE_PATTERN, avgTotalExecTimeText, avgIterationsText);
          final Map<String, Object> context = Maps.newHashMap();
          context.put(AVG_TOTAL, avgTotalExecTimeText);
          context.put(AVG_INTERATIONS, avgIterationsText);
          context.put(AVG_TIME_PER_ITERATION, avgExecTimePerIterationText);
          context.put(PROCEDURE_EXECUTIONS,procedureExecutionTimes);
          final String desc = template.getContent(LOOP, context);
          final Map<String, Object> additionalAttributes = Maps.newHashMap();
		  additionalAttributes.put(MarkerAttributes.DESCRIPTION, desc);
		  loop.markWarning(Ids.PERFORMANCE_MARKER,PerformanceMarkerTypes.COLLECTION_SIZE,msg, additionalAttributes);
	  }
	  
	  public static Double findNumOfIterations(LoopStatement loop, ProgrammMarkerContext context){
		  final Optional<IAstNode> collectionSource = loop.getSourceNode();
		  if (collectionSource.isPresent()) {
			 List<Double> colSize = collectionSource.get().getDoubleTags("CollectionSize");
			 if(!colSize.isEmpty()){
				 double averageSize = 0.0; 
				 for(double size :colSize)averageSize+=size;
				 averageSize /= colSize.size();
	        	 if(averageSize == 0) return averageSize;	        	
			 }
		 }
		 return null;
	  }

	  @FunctionalInterface
	  private interface ExcecutionTimeSummaryCallBack{
		  public void callBack(double avgExecTimePerIteration,List<ProcedureExecutionData> procedureExecutionTimes);
	  }
	  
	  private static class BlockTimeMeasurer extends ProgrammMarkerVisitor{
		private final ExcecutionTimeSummaryCallBack cb;
		private final ProgrammMarkerContext context;
      	private double avgExecTimePerIteration = 0.0;
      	private List<ProcedureExecutionData> procedureExecutionTimes = Lists.newArrayList();
     	
		public BlockTimeMeasurer(ProgrammMarkerContext context, ExcecutionTimeSummaryCallBack cb) {
			this.cb = cb;
			this.context = context;
		}

		@Override
		public ProgrammMarkerVisitor visit(Invocation invocation) {
			List<Double> tags = invocation.getDoubleTags("AvgExcecutionTime");
			if(tags.isEmpty()) return CONTINUE;
			double avgExecTime = 0.0;
			for(double avgT : tags)avgExecTime+=avgT;
			avgExecTime /= tags.size();
			avgExecTimePerIteration+= avgExecTime;
	    	procedureExecutionTimes.add(ProcedureExecutionData.of(invocation.createCorrelatingProcedure(), avgExecTime));
			return CONTINUE;
		}
		
		@Override
		public ProgrammMarkerVisitor visit(LoopStatement loop) {
			  Double averageSize = findNumOfIterations(loop, context);
			  if(averageSize == null) return CONTINUE;
			  if(averageSize == 0) return SKIP_CHILDS;
			  final double avgSize = averageSize; //so we can use it in inner
			  final double threshold = context.getProject().getFeedbackProperties().getDouble(PerformanceFeedbackProperties.TRESHOLD__LOOPS, PerformanceConfigs.DEFAULT_THRESHOLD_LOOPS);
	     
			  return new BlockTimeMeasurer(context,(avgExecTimePerIteration, procedureExecutionTimes) ->{
		         //todo: correct? should it be total?
	         	 if (avgExecTimePerIteration >= threshold) {
	         		//Create independent marker if higher then threshold 
	         		createCriticalLoopMarker(loop,context.getTemplateHandler(),avgSize,avgExecTimePerIteration,procedureExecutionTimes);
	         	 }
	         	 //Integrate results into current loop
	         	 avgExecTimePerIteration+=(avgExecTimePerIteration*avgSize);
	         	 procedureExecutionTimes.addAll(procedureExecutionTimes);
		    });
		}

		@Override
		public void finish() {
          cb.callBack(avgExecTimePerIteration, procedureExecutionTimes);
		}
	  }

	  //Same as BlockTimeMeasurer, but react's only on loops, if we would like predictions for Methods, then we could just add them here
	  //But then Hotspot could fastly get irrelevant, basically it would be easy todo everithing here
	  @Override
	  public ProgrammMarkerVisitor createFileVisitor(final ProgrammMarkerContext rootContext) {
		  return new ProgrammMarkerVisitor() {
				@Override
				public ProgrammMarkerVisitor visit(LoopStatement loop) {
					  Double averageSize = findNumOfIterations(loop, rootContext);
					  if(averageSize == null) return CONTINUE;
					  if(averageSize == 0) return SKIP_CHILDS;
					  final double avgSize = averageSize; //so we can use it in inner
					  final double threshold = rootContext.getProject().getFeedbackProperties().getDouble(PerformanceFeedbackProperties.TRESHOLD__LOOPS, PerformanceConfigs.DEFAULT_THRESHOLD_LOOPS);
			     
					  return new BlockTimeMeasurer(rootContext,(avgExecTimePerIteration, procedureExecutionTimes) ->{
				         //todo: correct? should it be total?
			         	 if (avgExecTimePerIteration >= threshold) {
			         		createCriticalLoopMarker(loop,rootContext.getTemplateHandler(),avgSize,avgExecTimePerIteration,procedureExecutionTimes);
			         	 }
				    });	
			     }
		  };
	}
}
