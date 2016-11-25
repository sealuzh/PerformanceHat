package eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.timestats;

import java.util.Collection;
import java.util.Collections;

import org.apache.commons.lang3.NotImplementedException;

import com.google.common.collect.Lists;

public class LoopTimeNode implements AvgTimeNode{

	private final double predTime;
	public final double avgIters;
	public final AvgTimeNode body;
	public final AvgTimeNode header;

	public LoopTimeNode(double predTime, double avgIters, AvgTimeNode body, AvgTimeNode header) {
		this.predTime = predTime;
		this.avgIters = avgIters;
		this.header = header;
		this.body = body;
	}

	public boolean isDataNode(){
		return true;
	}
	
	public String getText(){
		return "loop("+avgIters+"x)";
	}
	
	public double getAvgTime(){
		return predTime;
	}
	
	public Collection<AvgTimeNode> getChildren(){
		return Lists.newArrayList(header,body);
	}
}
