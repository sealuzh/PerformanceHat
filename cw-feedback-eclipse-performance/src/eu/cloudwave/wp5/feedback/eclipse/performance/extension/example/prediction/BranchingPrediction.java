package eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.prediction;

import java.util.Collection;
import java.util.List;

import com.google.common.collect.Lists;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.PredictionNode;

public class BranchingPrediction implements PredictionNode{

	private final double predTime;
	private final PredictionNode condition;
	private final Collection<PredictionNode> branches;

	public BranchingPrediction(double predTime, PredictionNode condition, Collection<PredictionNode> branches) {
		this.predTime = predTime;
		this.condition = condition;
		this.branches = branches;
	}

	public boolean isDataNode(){
		return true;
	}
	
	public String getText(){
		return "branching";
	}
	
	public double getPredictedTime(){
		return predTime;
	}
	
	public Collection<PredictionNode> getChildren(){
		List<PredictionNode> res = Lists.newArrayList(condition);
		res.addAll(branches);
		return res;
	}
}
