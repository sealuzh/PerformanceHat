package eu.cloudwave.wp5.feedback.eclipse.performance.extension.example;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import eu.cloudwave.wp5.feedback.eclipse.performance.core.tag.MethodLocator;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.AstContext;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.PerformancePlugin;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.prediction.BlockPrediction;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.prediction.BranchPrediction;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.prediction.BranchingPrediction;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.prediction.LoopPrediction;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.prediction.MethodCallPrediction;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.PredictionNode;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.Branching;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.Invocation;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.Loop;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.MethodDeclaration;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.Try;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.predictor.BlockTimePredictor;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.predictor.BlockTimePredictorCallback;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.visitor.PerformanceVisitor;

/**
 * A Predictor using the BlockTimePrediction Framework to predict method executioin times
 * @author Markus Knecht
 *
 */
public class BlockPredictionPlugin implements PerformancePlugin, BlockTimePredictorCallback{

	  private static final String ID = "eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.BlockPredictionPlugin";
	  private static final String COLLECTION_SIZE_TAG = "CollectionSize";
	  private static final String AVG_EXEC_TIME_TAG = "AvgExcecutionTime";
	  public static final String AVG_PRED_TIME_TAG = "AvgPredictionTime";
	  
	  /**
	   * {@inheritDoc}
	   */
	  @Override
	  public String getId() {
			return ID;
	  }	  
	
	  /**
	   * {@inheritDoc}
	   */
	  @Override
	  public List<String> getRequiredTags() {
		  	return Lists.asList(COLLECTION_SIZE_TAG,AVG_EXEC_TIME_TAG, new String[]{});
	  }
	  
	  /**
	   * {@inheritDoc}
	   */
	  public List<String> getProvidedTags(){
		  return Collections.singletonList(AVG_PRED_TIME_TAG);
	  }
	  
	  /**
	   * {@inheritDoc}
	   */
	  @Override
	  public PerformanceVisitor createPerformanceVisitor(final AstContext rootContext) {
		  //Visitor that visits all method declaration and predict excecution times
		  return new PerformanceVisitor() {
				@Override
				public PerformanceVisitor visit(MethodDeclaration method) {
					 //Use BlockTimeCollector to measure the method
					  return new BlockTimePredictor(BlockPredictionPlugin.this){
						@Override
						public PredictionNode generateResults() {
							//simply sum up contributions
							final double methodTimeSum = sum(excecutionTimeStats);
							//generate a generic Block Prediction for method body
							final PredictionNode methodN = new BlockPrediction("method declaration", methodTimeSum, excecutionTimeStats);	
							//publish the results as a Tag (Consumed by Hotspot at least)
							method.attachPublicTag(AVG_PRED_TIME_TAG, methodN);
							//method.attachTag(AVG_PRED_TIME_TAG, methodN);
							return methodN;
						} 
					  };
			     }
		  };
	} 
	  
	//TODO: Move to a toolbox
	private static double sum(List<PredictionNode> nodes){
		return nodes.stream().mapToDouble(n -> n.getPredictedTime()).sum();
	}
	  
	/**
	 * Predicts based on the AVG_EXEC_TIME_TAG and AVG_PRED_TIME_TAG
	 * {@inheritDoc}
	 */
	@Override
	public PredictionNode invocationEncountered(Invocation invocation) {
		Collection<Double> measurements = invocation.getDoubleTags(BlockPredictionPlugin.AVG_EXEC_TIME_TAG);
		//Consume own Tags, only works over multiple compiles or else we would need complext tag management
		Collection<Object> predTags = invocation.getTags(BlockPredictionPlugin.AVG_PRED_TIME_TAG);
		//We prefer predictions currently
		if(!predTags.isEmpty()) {
			measurements = predTags.stream().map(p -> ((PredictionNode)p).getPredictedTime()).collect(Collectors.toList());
		}
		if(measurements.isEmpty()) return null;
		double avgExecTime = 0.0;
		for(double avgT : measurements) {
			avgExecTime+=avgT;
		}
		avgExecTime /= measurements.size();
		MethodLocator loc = invocation.createCorrespondingMethodLocation();
		return new MethodCallPrediction(loc, avgExecTime);
	}

	/**
	 * Predicts based on loop Header and body and the number of iterations
	 * {@inheritDoc}
	 */
	@Override
	public PredictionNode loopMeasured(List<PredictionNode> iterationExecutionTime, List<PredictionNode> headerExecutionTime, Loop loop){

		//do we know the iterations
		Double avgItersLookup = LoopUtils.findNumOfIterations(loop);
		//at least do one iteration
		double avgIters = (avgItersLookup == null)?1:avgItersLookup;
		final double iterExcecTime = sum(iterationExecutionTime);
		final double headerExectime =  sum(headerExecutionTime);

		//Predict n*body + 1*header
		final PredictionNode headerN = new BlockPrediction("loop header", headerExectime, headerExecutionTime);
		PredictionNode bodyN = new BlockPrediction("loop body", iterExcecTime, iterationExecutionTime);		
		final PredictionNode loopN = new LoopPrediction((avgIters*bodyN.getPredictedTime())+headerN.getPredictedTime(),avgIters, bodyN, headerN);	
		
		//Publish prediction per tag
		loop.attachTag(AVG_PRED_TIME_TAG, loopN);
		return loopN;
	}

	/**
	 * Predicts based on all branches (assumes equal likelihood)
	 * {@inheritDoc}
	 */
	@Override
	public PredictionNode branchingMeasured(List<PredictionNode> conditionExecutionTime, List<List<PredictionNode>> branchExecutionTimes, Branching branch){
		 final List<PredictionNode> branchNodes = Lists.newArrayList();
		 //for the condition simply sum up
		 final PredictionNode conditionN = new BlockPrediction("condition", sum(conditionExecutionTime), conditionExecutionTime);
		 //Create the Branch Prediction by summing up and deviding through number of branches
		 double tot = 0;
		 int branchCount = branchExecutionTimes.size();
		 if(branch.isSkippable())branchCount++; //shows if their is a chance for zero branch in that case we giv it also equal likelyhood
		 int i = 0;
		 //factor
		 double part = 1.0/branchCount;
		 //calc each branch
		 for(List<PredictionNode> bet:branchExecutionTimes) {
			 final double betSum = sum(bet);
			 branchNodes.add(new BranchPrediction((++i),part, betSum/branchCount, bet));
			 tot+=betSum;
		 }
		 //average
		 tot /= branchCount;
		 //main prediction node
		 final PredictionNode branchN = new BranchingPrediction(tot, conditionN, branchNodes);

		 //Publish prediction per tag
		 branch.attachTag(AVG_PRED_TIME_TAG, branchN);
		 
		 return branchN;
	}

	/**
	 * Predicts based on try,finally but ignores catches
	 * {@inheritDoc}
	 */
	@Override
	public PredictionNode tryMeasured(List<PredictionNode> tryExecutionTime, List<PredictionNode> finnalyExecutionTime, List<List<PredictionNode>> catchExccutionTimes, Try tryStm) {
		//Is a finally present
		if(finnalyExecutionTime == null){
			//if not its simply the sum of the try
			return new BlockPrediction("try",sum(tryExecutionTime), tryExecutionTime);
		} else {
			//else record both try and finally
			//the try
			final double tryTimeSum = sum(tryExecutionTime);
			final PredictionNode bodyN = new BlockPrediction("body", tryTimeSum, tryExecutionTime);
			//the finally
			final double finallyTimeSum = sum(finnalyExecutionTime);
			final PredictionNode finallyN = new BlockPrediction("finally", finallyTimeSum, finnalyExecutionTime);		
			//thr full try/finally
			final PredictionNode tryN = new BlockPrediction("try", tryTimeSum+finallyTimeSum, bodyN, finallyN);
			
			 //Publish prediction per tag
			tryStm.attachTag(AVG_PRED_TIME_TAG, tryN); 
			
			return tryN;
		}
	}
	
}
