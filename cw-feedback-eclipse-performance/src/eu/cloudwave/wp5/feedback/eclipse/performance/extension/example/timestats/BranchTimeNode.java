package eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.timestats;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Collections;

import org.apache.commons.lang3.NotImplementedException;

import com.google.common.collect.Lists;

public class BranchTimeNode implements AvgTimeNode{

	private final int index;
	private final double fraction;
	private final double avgTime;
	private final Collection<AvgTimeNode> childs;
	
	public BranchTimeNode(int index, double fraction, double avgTime, Collection<AvgTimeNode> childs) {
		this.index = index;
		this.fraction = fraction;
		this.avgTime = avgTime;
		this.childs = childs;
	}

	public boolean isDataNode(){
		return true;
	}
	
	public String getText(){
		 double part = new BigDecimal((fraction*100)).setScale(2, RoundingMode.HALF_UP).doubleValue();
		return "branch "+index+"("+part+"%)";
	}
	
	public double getAvgTime(){
		return avgTime;
	}
	
	public Collection<AvgTimeNode> getChildren(){
		return childs;
	}
}
