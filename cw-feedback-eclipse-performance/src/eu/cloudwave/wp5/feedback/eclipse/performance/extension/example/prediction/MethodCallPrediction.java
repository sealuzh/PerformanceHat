package eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.prediction;

import java.util.Collection;
import java.util.Collections;

import eu.cloudwave.wp5.feedback.eclipse.performance.core.tag.MethodLocator;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.PredictionNode;

/**
 * A PredictionNode implementation for method call leafs
 * @see PredictionNode
 * @author Markus Knecht
 *
 */
public class MethodCallPrediction implements PredictionNode{
	private final MethodLocator loc;
	private final double avgTime;

	public MethodCallPrediction(MethodLocator loc, double avgTime) {
		super();
		this.loc = loc;
		this.avgTime = avgTime;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isDataNode(){
		return true;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getText(){
		return loc.methodName;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getPredictedTime(){
		return avgTime;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<PredictionNode> getChildren(){
		return Collections.emptyList();
	}
}
