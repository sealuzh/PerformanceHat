package eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.prediction;

import java.util.Collection;

import com.google.common.collect.Lists;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.PredictionNode;

/**
 * A PredictionNode implementation for for and foreach blocks
 * @see PredictionNode
 * @author Markus Knecht
 *
 */
public class LoopPrediction implements PredictionNode{

	private final double predTime;
	public final double avgIters;
	public final PredictionNode body;
	public final PredictionNode header;

	public LoopPrediction(double predTime, double avgIters, PredictionNode body, PredictionNode header) {
		this.predTime = predTime;
		this.avgIters = avgIters;
		this.header = header;
		this.body = body;
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
		return "loop("+avgIters+"x)";
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getPredictedTime(){
		return predTime;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<PredictionNode> getChildren(){
		return Lists.newArrayList(header,body);
	}
}
