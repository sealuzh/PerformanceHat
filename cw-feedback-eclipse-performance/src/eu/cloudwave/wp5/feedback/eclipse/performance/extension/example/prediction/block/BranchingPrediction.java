package eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.prediction.block;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.PredictionNode;

/**
 * A PredictionNode implementation for if/switch and conditional blocks
 * @see PredictionNode
 * @author Markus Knecht
 *
 */
public class BranchingPrediction extends APrediction{
	private final PredictionNode condition;
	private final Collection<PredictionNode> branches;

	public BranchingPrediction(double avgTimePred, double avgTimeMes, PredictionNode condition, Collection<PredictionNode> branches) {
		super(avgTimePred,avgTimeMes);
		this.condition = condition;
		this.branches = branches;
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
	public Collection<PredictionNode> getChildren(){
		List<PredictionNode> res = Lists.newArrayList(condition);
		res.addAll(branches);
		return res;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<String> getPredictedText() {
		return getPredictedTime().stream().map(p -> round(p)+"ms").collect(Collectors.toList());

	}
}
