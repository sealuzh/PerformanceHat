package eu.cloudwave.wp5.feedback.eclipse.performance.extension.example;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import eu.cloudwave.wp5.common.util.Numbers;
import eu.cloudwave.wp5.common.util.TimeValues;
import eu.cloudwave.wp5.feedback.eclipse.base.infrastructure.template.TemplateHandler;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.FeedbackMarkerType;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.MarkerAttributes;
import eu.cloudwave.wp5.feedback.eclipse.performance.Ids;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.markers.PerformanceMarkerTypes;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.properties.PerformanceFeedbackProperties;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.tag.MethodLocator;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ProgrammMarker;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ProgrammMarkerContext;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.Branching;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.IAstNode;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.Invocation;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.Loop;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.MethodDeclaration;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.Try;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.timestats.AvgTimeLeafe;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.timestats.AvgTimeNode;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.timestats.DataAvgTimeNode;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.visitor.ProgrammMarkerVisitor;
import eu.cloudwave.wp5.feedback.eclipse.performance.infrastructure.config.PerformanceConfigs;

//TODO: DOES NOT WORK FIND OUT WHY
public class BlockPredictionProgrammMarker implements ProgrammMarker, BlockTimeCollectorCallback{

	  private static final String PROCEDURE_EXECUTIONS = "procedureExecutions";
	  private static final String AVG_TIME_PER_ITERATION = "avgTimePerIteration";
	  private static final String AVG_INTERATIONS = "avgInterations";
	  private static final String AVG_TOTAL = "avgTotal";
	  private static final String LOOP = "loop";
	  private static final String METHOD = "method";

	  private static final int DECIMAL_PLACES = 3;
	  private static final String LOOP_MESSAGE_PATTERN = "Critical Loop: Average Total Time is %s (Average Iterations: %s).";
	  private static final String METHOD_MESSAGE_PATTERN = "Critical method: Average Total Time is %s.";

	  private static final String COLLECTION_SIZE_TAG = "CollectionSize";
	  static final String AVG_EXEC_TIME_TAG = "AvgExcecutionTime";

	  @Override
	  public List<String> getRequiredTags() {
		  	return Lists.asList(COLLECTION_SIZE_TAG,AVG_EXEC_TIME_TAG, new String[]{});
	  }
	  
	private static void createMarker(IAstNode node,String message, String desc, FeedbackMarkerType type){
        final Map<String, Object> additionalAttributes = Maps.newHashMap();
		additionalAttributes.put(MarkerAttributes.DESCRIPTION, desc);
		node.markWarning(Ids.PERFORMANCE_MARKER,type,message, additionalAttributes);
	}

	private static void createCriticalLoopMarker(Loop loop, TemplateHandler template, double averageSize, double avgExecTimePerIteration,  AvgTimeNode procedureExecutionSummary ){
          final String avgIterationsText = new Double(Numbers.round(averageSize, DECIMAL_PLACES)).toString();
          final String avgTotalExecTimeText = TimeValues.toText(procedureExecutionSummary.getAvgTime(), DECIMAL_PLACES);
          final String msg = String.format(LOOP_MESSAGE_PATTERN, avgTotalExecTimeText, avgIterationsText);
          final Map<String, Object> context = Maps.newHashMap();
          context.put(AVG_TOTAL, avgTotalExecTimeText);
          context.put(AVG_INTERATIONS, avgIterationsText);
          context.put(AVG_TIME_PER_ITERATION, TimeValues.toText(avgExecTimePerIteration, DECIMAL_PLACES));
          context.put(PROCEDURE_EXECUTIONS,procedureExecutionSummary);
          final String desc = template.getContent(LOOP, context);
          createMarker(loop,msg,desc,PerformanceMarkerTypes.COLLECTION_SIZE);
	}
	
	private static void createCriticalMetodMarker(MethodDeclaration decl, TemplateHandler template,AvgTimeNode procedureExecutionSummary ){
        final String avgTotalExecTimeText = TimeValues.toText(procedureExecutionSummary.getAvgTime(), DECIMAL_PLACES);
        final String msg = String.format(METHOD_MESSAGE_PATTERN, avgTotalExecTimeText);
        final Map<String, Object> context = Maps.newHashMap();
        context.put(AVG_TOTAL, avgTotalExecTimeText);
        context.put(PROCEDURE_EXECUTIONS,procedureExecutionSummary);
        final String desc = template.getContent(METHOD, context);
        createMarker(decl,msg,desc,PerformanceMarkerTypes.COLLECTION_SIZE);
	 }
	 
	  //Same as BlockTimeMeasurer, but react's only on loops, if we would like predictions for Methods, then we could just add them here
	  //But then Hotspot could fastly get irrelevant, basically it would be easy todo everithing here
	  @Override
	  public ProgrammMarkerVisitor createFileVisitor(final ProgrammMarkerContext rootContext) {
		  return new ProgrammMarkerVisitor() {
				@Override
				public ProgrammMarkerVisitor visit(MethodDeclaration method) {
					  return new BlockTimeCollector(BlockPredictionProgrammMarker.this,rootContext){
						@Override
						public AvgTimeNode generateResults() {
							final AvgTimeNode methodN = new DataAvgTimeNode("method declaration", avgExcecutionTime, excecutionTimeStats);	
							final double threshold = context.getProject().getFeedbackProperties().getDouble(PerformanceFeedbackProperties.TRESHOLD__LOOPS, PerformanceConfigs.DEFAULT_THRESHOLD_LOOPS);
							if (avgExcecutionTime >= threshold) createCriticalMetodMarker(method,context.getTemplateHandler(),methodN);
							return methodN;
						} 
					  };
			     }
		  };
	} 
	  
	@Override
	public AvgTimeNode invocationEncountered(Invocation invocation, ProgrammMarkerContext context) {
		List<Double> tags = invocation.getDoubleTags(BlockPredictionProgrammMarker.AVG_EXEC_TIME_TAG);
		if(tags.isEmpty()) return null;
		double avgExecTime = 0.0;
		for(double avgT : tags)avgExecTime+=avgT;
		avgExecTime /= tags.size();
		MethodLocator loc = invocation.createCorrespondingMethodLocation();
		return new AvgTimeLeafe(loc.methodName, avgExecTime);
	}

	@Override
	public AvgTimeNode loopMeasured(ExecutionStats iterationExecutionTime, ExecutionStats headerExecutionTime, Loop loop, ProgrammMarkerContext context){

		
		Double avgItersLookup = LoopUtils.findNumOfIterations(loop, context);
		double avgIters = (avgItersLookup == null)?0:avgItersLookup;
		final double bodyT = (iterationExecutionTime.avgExcecutionTime*avgIters);
		final double totalT = headerExecutionTime.avgExcecutionTime+bodyT;

		final AvgTimeNode headerN = new DataAvgTimeNode("loop header", headerExecutionTime.avgExcecutionTime, headerExecutionTime.excecutionTimeStats);
		AvgTimeNode bodyN;
		if(avgItersLookup == null) bodyN = new AvgTimeLeafe("<Iteration prediction failed>",0);
		else bodyN = new DataAvgTimeNode("loop body("+(int)avgIters+"x)", bodyT, iterationExecutionTime.excecutionTimeStats);		
		final AvgTimeNode loopN = new DataAvgTimeNode("loop", totalT, headerN, bodyN);
		
		final double threshold = context.getProject().getFeedbackProperties().getDouble(PerformanceFeedbackProperties.TRESHOLD__LOOPS, PerformanceConfigs.DEFAULT_THRESHOLD_LOOPS);
		if (totalT >= threshold) createCriticalLoopMarker(loop,context.getTemplateHandler(),avgIters,iterationExecutionTime.avgExcecutionTime, loopN);
		return loopN;
	}

	@Override
	public AvgTimeNode branchMeasured(ExecutionStats conditionExecutionTime, List<ExecutionStats> branchExecutionTimes, Branching branch, ProgrammMarkerContext context){
		 final List<AvgTimeNode> subNodes = Lists.newArrayList();
		 subNodes.add(new DataAvgTimeNode("condition", conditionExecutionTime.avgExcecutionTime, conditionExecutionTime.excecutionTimeStats));
		 double tot = 0;
		 int branchCount = branchExecutionTimes.size();
		 if(branch.isSkippable())branchCount++;
		 int i = 0;
		 double part = new BigDecimal((100.0/branchCount)).setScale(2, RoundingMode.HALF_UP).doubleValue();
		 for(ExecutionStats bet:branchExecutionTimes) {
			 subNodes.add(new DataAvgTimeNode("branch "+(++i)+"("+part+"%)", bet.avgExcecutionTime/branchCount, bet.excecutionTimeStats));
			 tot+=bet.avgExcecutionTime;
		 }
		 tot /= branchCount;
		 final AvgTimeNode loopN = new DataAvgTimeNode("branching", tot, subNodes);

		 //Todo: markig/tagging
		 
		 return loopN;
	}

	@Override
	public AvgTimeNode tryMeasured(ExecutionStats tryExecutionTime, ExecutionStats finnalyExecutionTime, List<ExecutionStats> catchExccutionTimes, Try tryStm, ProgrammMarkerContext context) {
		if(finnalyExecutionTime == null){
			return new DataAvgTimeNode("try",tryExecutionTime.avgExcecutionTime, tryExecutionTime.excecutionTimeStats);
		} else {
			final AvgTimeNode bodyN = new DataAvgTimeNode("body", tryExecutionTime.avgExcecutionTime, tryExecutionTime.excecutionTimeStats);
			final AvgTimeNode finallyN = new DataAvgTimeNode("finally", finnalyExecutionTime.avgExcecutionTime, finnalyExecutionTime.excecutionTimeStats);		
			final AvgTimeNode tryN = new DataAvgTimeNode("try", tryExecutionTime.avgExcecutionTime+finnalyExecutionTime.avgExcecutionTime, bodyN, finallyN);
			 //Todo: markig/tagging

			
			return tryN;
		}
	}	  
	
	
}
