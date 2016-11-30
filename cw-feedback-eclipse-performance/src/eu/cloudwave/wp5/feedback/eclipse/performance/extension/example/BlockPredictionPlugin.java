package eu.cloudwave.wp5.feedback.eclipse.performance.extension.example;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import eu.cloudwave.wp5.feedback.eclipse.performance.core.tag.MethodLocator;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.AstContext;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.PerformancePlugin;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.prediction.BlockPrediction;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.prediction.BranchPrediction;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.prediction.BranchingPrediction;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.prediction.LoopPrediction;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.prediction.LoopPredictionError;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.prediction.MethodCallPrediction;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.PredictionNode;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.Branching;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.Invocation;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.Loop;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.MethodDeclaration;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.Try;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.predictor.BlockTimeCollector;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.predictor.BlockTimeCollectorCallback;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.predictor.LoopUtils;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.visitor.PerformanceVisitor;

public class BlockPredictionPlugin implements PerformancePlugin, BlockTimeCollectorCallback{


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
	  public PerformanceVisitor createPerformanceVisitor(final AstContext rootContext) {
		  return new PerformanceVisitor() {
				@Override
				public PerformanceVisitor visit(MethodDeclaration method) {
					  return new BlockTimeCollector(BlockPredictionPlugin.this,rootContext){
						@Override
						public PredictionNode generateResults() {
							final double methodTimeSum = sum(excecutionTimeStats);
							final PredictionNode methodN = new BlockPrediction("method declaration", methodTimeSum, excecutionTimeStats);	
							//final double threshold = context.getProject().getFeedbackProperties().getDouble(PerformanceFeedbackProperties.TRESHOLD__LOOPS, PerformanceConfigs.DEFAULT_THRESHOLD_LOOPS);
							//if (methodTimeSum >= threshold) createCriticalMetodMarker(method,context.getTemplateHandler(),methodN);
							method.attachTag(AVG_PRED_TIME_TAG, methodN);
							return methodN;
						} 
					  };
			     }
		  };
	} 
	  
	private static double sum(List<PredictionNode> nodes){
		return nodes.stream().mapToDouble(n -> n.getPredictedTime()).sum();
	}
	  
	@Override
	public PredictionNode invocationEncountered(Invocation invocation, AstContext context) {
		List<Double> tags = invocation.getDoubleTags(BlockPredictionPlugin.AVG_EXEC_TIME_TAG);
		if(tags.isEmpty()) return null;
		double avgExecTime = 0.0;
		for(double avgT : tags) {
			avgExecTime+=avgT;
		}
		avgExecTime /= tags.size();
		MethodLocator loc = invocation.createCorrespondingMethodLocation();
		return new MethodCallPrediction(loc, avgExecTime);
	}

	@Override
	public PredictionNode loopMeasured(List<PredictionNode> iterationExecutionTime, List<PredictionNode> headerExecutionTime, Loop loop, AstContext context){

		
		Double avgItersLookup = LoopUtils.findNumOfIterations(loop, context);
		double avgIters = (avgItersLookup == null)?0:avgItersLookup;
		final double iterExcecTime = sum(iterationExecutionTime);
		final double headerExectime =  sum(headerExecutionTime);

		final PredictionNode headerN = new BlockPrediction("loop header", headerExectime, headerExecutionTime);
		PredictionNode bodyN;
		if(avgItersLookup == null) bodyN = new LoopPredictionError();
		else bodyN = new BlockPrediction("loop body", iterExcecTime, iterationExecutionTime);		
		final PredictionNode loopN = new LoopPrediction((avgIters*bodyN.getPredictedTime())+headerN.getPredictedTime(),avgIters, bodyN, headerN);	
		
		//final double threshold = context.getProject().getFeedbackProperties().getDouble(PerformanceFeedbackProperties.TRESHOLD__LOOPS, PerformanceConfigs.DEFAULT_THRESHOLD_LOOPS);
		loop.attachTag(AVG_PRED_TIME_TAG, loopN);
		//if (totalT >= threshold) createCriticalLoopMarker(loop,context.getTemplateHandler(),avgIters,iterExcecTime, loopN);
		return loopN;
	}

	@Override
	public PredictionNode branchMeasured(List<PredictionNode> conditionExecutionTime, List<List<PredictionNode>> branchExecutionTimes, Branching branch, AstContext context){
		 final List<PredictionNode> branchNodes = Lists.newArrayList();
		 final PredictionNode conditionN = new BlockPrediction("condition", sum(conditionExecutionTime), conditionExecutionTime);
		 double tot = 0;
		 int branchCount = branchExecutionTimes.size();
		 if(branch.isSkippable())branchCount++;
		 int i = 0;
		 double part = 1.0/branchCount;
		 for(List<PredictionNode> bet:branchExecutionTimes) {
			 final double betSum = sum(bet);
			 branchNodes.add(new BranchPrediction((++i),part, betSum/branchCount, bet));
			 tot+=betSum;
		 }
		 tot /= branchCount;
		 final PredictionNode branchN = new BranchingPrediction(tot, conditionN, branchNodes);

		 //Todo: markig/tagging
		 
		 return branchN;
	}

	@Override
	public PredictionNode tryMeasured(List<PredictionNode> tryExecutionTime, List<PredictionNode> finnalyExecutionTime, List<List<PredictionNode>> catchExccutionTimes, Try tryStm, AstContext context) {
		//todo: make try node
		if(finnalyExecutionTime == null){
			return new BlockPrediction("try",sum(tryExecutionTime), tryExecutionTime);
		} else {
			final double tryTimeSum = sum(tryExecutionTime);
			final PredictionNode bodyN = new BlockPrediction("body", tryTimeSum, tryExecutionTime);
			final double finallyTimeSum = sum(finnalyExecutionTime);
			final PredictionNode finallyN = new BlockPrediction("finally", finallyTimeSum, finnalyExecutionTime);		
			final PredictionNode tryN = new BlockPrediction("try", tryTimeSum+finallyTimeSum, bodyN, finallyN);
			 //Todo: markig/tagging

			
			return tryN;
		}
	}	  
	
	
}
