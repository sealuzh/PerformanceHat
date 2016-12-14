package eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.prediction;

import java.util.Collection;
import java.util.List;

import com.google.common.collect.Lists;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.PredictionNode;

/**
 * A PredictionNode implementation for if/switch and conditional blocks
 * @see PredictionNode
 * @author Markus Knecht
 *
 */
public class BranchingPrediction implements PredictionNode{

	private final double predTime;
	private final PredictionNode condition;
	private final Collection<PredictionNode> branches;

	public BranchingPrediction(double predTime, PredictionNode condition, Collection<PredictionNode> branches) {
		this.predTime = predTime;
		this.condition = condition;
		this.branches = branches;
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
		return "branching";
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
		List<PredictionNode> res = Lists.newArrayList(condition);
		res.addAll(branches);
		return res;
	}
}
