package eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.timestats;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.NotImplementedException;

import com.google.common.collect.Lists;

public class BranchingTimeNode implements AvgTimeNode{

	private final double predTime;
	private final AvgTimeNode condition;
	private final Collection<AvgTimeNode> branches;

	public BranchingTimeNode(double predTime, AvgTimeNode condition, Collection<AvgTimeNode> branches) {
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
	
	public double getAvgTime(){
		return predTime;
	}
	
	public Collection<AvgTimeNode> getChildren(){
		List<AvgTimeNode> res = Lists.newArrayList(condition);
		res.addAll(branches);
		return res;
	}
}
