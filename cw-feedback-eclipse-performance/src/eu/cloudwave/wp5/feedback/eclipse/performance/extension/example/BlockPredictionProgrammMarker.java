package eu.cloudwave.wp5.feedback.eclipse.performance.extension.example;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import eu.cloudwave.wp5.feedback.eclipse.performance.core.tag.MethodLocator;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ProgrammMarker;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ProgrammMarkerContext;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.Branching;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.Invocation;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.Loop;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.MethodDeclaration;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.Try;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.timestats.AvgTimeNode;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.timestats.BlockTimeNode;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.timestats.BranchTimeNode;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.timestats.BranchingTimeNode;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.timestats.LoopErrorTimeNode;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.timestats.LoopTimeNode;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.timestats.MethodCallTimeNode;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.visitor.ProgrammMarkerVisitor;

public class BlockPredictionProgrammMarker implements ProgrammMarker, BlockTimeCollectorCallback{


	  private static final String COLLECTION_SIZE_TAG = "CollectionSize";
	  private static final String AVG_EXEC_TIME_TAG = "AvgExcecutionTime";
	  public static final String AVG_PRED_TIME_TAG = "AvgPredictionTime";

	  @Override
	  public List<String> getRequiredTags() {
		  	return Lists.asList(COLLECTION_SIZE_TAG,AVG_EXEC_TIME_TAG, new String[]{});
	  }
	  
	  public List<String> getProvidedTags(){
		  return Collections.singletonList(AVG_PRED_TIME_TAG);
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
							final double methodTimeSum = sum(excecutionTimeStats);
							final AvgTimeNode methodN = new BlockTimeNode("method declaration", methodTimeSum, excecutionTimeStats);	
							//final double threshold = context.getProject().getFeedbackProperties().getDouble(PerformanceFeedbackProperties.TRESHOLD__LOOPS, PerformanceConfigs.DEFAULT_THRESHOLD_LOOPS);
							//if (methodTimeSum >= threshold) createCriticalMetodMarker(method,context.getTemplateHandler(),methodN);
							method.attachTag(AVG_PRED_TIME_TAG, methodN);
							return methodN;
						} 
					  };
			     }
		  };
	} 
	  
	private static double sum(List<AvgTimeNode> nodes){
		return nodes.stream().mapToDouble(n -> n.getAvgTime()).sum();
	}
	  
	@Override
	public AvgTimeNode invocationEncountered(Invocation invocation, ProgrammMarkerContext context) {
		List<Double> tags = invocation.getDoubleTags(BlockPredictionProgrammMarker.AVG_EXEC_TIME_TAG);
		if(tags.isEmpty()) return null;
		double avgExecTime = 0.0;
		for(double avgT : tags) {
			avgExecTime+=avgT;
		}
		avgExecTime /= tags.size();
		MethodLocator loc = invocation.createCorrespondingMethodLocation();
		return new MethodCallTimeNode(loc, avgExecTime);
	}

	@Override
	public AvgTimeNode loopMeasured(List<AvgTimeNode> iterationExecutionTime, List<AvgTimeNode> headerExecutionTime, Loop loop, ProgrammMarkerContext context){

		
		Double avgItersLookup = LoopUtils.findNumOfIterations(loop, context);
		double avgIters = (avgItersLookup == null)?0:avgItersLookup;
		final double iterExcecTime = sum(iterationExecutionTime);
		final double headerExectime =  sum(headerExecutionTime);

		final AvgTimeNode headerN = new BlockTimeNode("loop header", headerExectime, headerExecutionTime);
		AvgTimeNode bodyN;
		if(avgItersLookup == null) bodyN = new LoopErrorTimeNode();
		else bodyN = new BlockTimeNode("loop body", iterExcecTime, iterationExecutionTime);		
		final AvgTimeNode loopN = new LoopTimeNode((avgIters*bodyN.getAvgTime())+headerN.getAvgTime(),avgIters, bodyN, headerN);	
		
		//final double threshold = context.getProject().getFeedbackProperties().getDouble(PerformanceFeedbackProperties.TRESHOLD__LOOPS, PerformanceConfigs.DEFAULT_THRESHOLD_LOOPS);
		loop.attachTag(AVG_PRED_TIME_TAG, loopN);
		//if (totalT >= threshold) createCriticalLoopMarker(loop,context.getTemplateHandler(),avgIters,iterExcecTime, loopN);
		return loopN;
	}

	@Override
	public AvgTimeNode branchMeasured(List<AvgTimeNode> conditionExecutionTime, List<List<AvgTimeNode>> branchExecutionTimes, Branching branch, ProgrammMarkerContext context){
		 final List<AvgTimeNode> branchNodes = Lists.newArrayList();
		 final AvgTimeNode conditionN = new BlockTimeNode("condition", sum(conditionExecutionTime), conditionExecutionTime);
		 double tot = 0;
		 int branchCount = branchExecutionTimes.size();
		 if(branch.isSkippable())branchCount++;
		 int i = 0;
		 double part = 1.0/branchCount;
		 for(List<AvgTimeNode> bet:branchExecutionTimes) {
			 final double betSum = sum(bet);
			 branchNodes.add(new BranchTimeNode((++i),part, betSum/branchCount, bet));
			 tot+=betSum;
		 }
		 tot /= branchCount;
		 final AvgTimeNode branchN = new BranchingTimeNode(tot, conditionN, branchNodes);

		 //Todo: markig/tagging
		 
		 return branchN;
	}

	@Override
	public AvgTimeNode tryMeasured(List<AvgTimeNode> tryExecutionTime, List<AvgTimeNode> finnalyExecutionTime, List<List<AvgTimeNode>> catchExccutionTimes, Try tryStm, ProgrammMarkerContext context) {
		//todo: make try node
		if(finnalyExecutionTime == null){
			return new BlockTimeNode("try",sum(tryExecutionTime), tryExecutionTime);
		} else {
			final double tryTimeSum = sum(tryExecutionTime);
			final AvgTimeNode bodyN = new BlockTimeNode("body", tryTimeSum, tryExecutionTime);
			final double finallyTimeSum = sum(finnalyExecutionTime);
			final AvgTimeNode finallyN = new BlockTimeNode("finally", finallyTimeSum, finnalyExecutionTime);		
			final AvgTimeNode tryN = new BlockTimeNode("try", tryTimeSum+finallyTimeSum, bodyN, finallyN);
			 //Todo: markig/tagging

			
			return tryN;
		}
	}	  
	
	
}
