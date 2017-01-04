package eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.predictor;

import java.util.List;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.PredictionNode;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.Branching;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.Invocation;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.Loop;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.Try;
/**
 * A Callback interface to implement the actual prediction logic used for the different block nodes
 * @author Markus Knecht
 *
 */
public interface BlockTimePredictorCallback {

	/**
	 * Called when a time prediction for a invocation is needed
	 * @param invocation the prediction is needed for
	 * @return a Prediction node representing the Prediction
	 */
	public PredictionNode invocationEncountered(Invocation invocation);
	
	/**
	 * Called when a time prediction for a loop is needed
	 * @param iterationPredictionTimes are the predicted times for the elements in the loops body
	 * @param headerPredictionTimes are the predicted times for the elements in the loops header
	 * @param loop the prediction is needed for
	 * @return a Prediction node representing the Prediction
	 */
	public PredictionNode loopMeasured(List<PredictionNode> iterationPredictionTimes, List<PredictionNode> headerPredictionTimes, Loop loop);
	
	
	/**
	 * Called when a time prediction for a branching is needed
	 * @param conditionPredictionTimes are the predicted times for the elements in the condition branched on
	 * @param branchPredicitonTimes are the predicted times for the elements in each branch
	 * @param branching the prediction is needed for
	 * @return a Prediction node representing the Prediction
	 */
	public PredictionNode branchingMeasured(List<PredictionNode> conditionPredictionTimes, List<List<PredictionNode>> branchPredicitonTimes, Branching branch);
	
	/**
	 * Called when a time prediction for a try is needed
	 * @param tryPredictionTimes are the predicted times for the elements in the try block
	 * @param finallyPredictionTimes are the predicted times for the elements in the finally block
	 * @param catchPredictionTimes are the predicted times for the elements in each catch block
	 * @param tryStm the prediction is needed for
	 * @return a Prediction node representing the Prediction
	 */
	public PredictionNode tryMeasured(List<PredictionNode> tryPredictionTimes, List<PredictionNode> finallyPredictionTimes, List<List<PredictionNode>> catchPredictionTimes, Try tryStm);

}