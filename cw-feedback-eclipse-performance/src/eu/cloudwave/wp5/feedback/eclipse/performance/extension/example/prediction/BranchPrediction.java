package eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.prediction;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.PredictionNode;

/**
 * A PredictionNode implementation for if, else, case and conditional block
 * @see PredictionNode
 * @author Markus Knecht
 *
 */
public class BranchPrediction implements PredictionNode{

	private final int index;
	private final double fraction;
	private final double avgTime;
	private final Collection<PredictionNode> childs;
	
	public BranchPrediction(int index, double fraction, double avgTime, Collection<PredictionNode> childs) {
		this.index = index;
		this.fraction = fraction;
		this.avgTime = avgTime;
		this.childs = childs;
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
		 double part = new BigDecimal((fraction*100)).setScale(2, RoundingMode.HALF_UP).doubleValue();
		return "branch "+index+"("+part+"%)";
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
		return childs;
	}
}
