package eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.timestats;

import java.util.Collection;
import java.util.Collections;

import org.apache.commons.lang3.NotImplementedException;

import eu.cloudwave.wp5.feedback.eclipse.performance.core.tag.MethodLocator;

public class MethodCallTimeNode implements AvgTimeNode{
	private final MethodLocator loc;
	private final double avgTime;

	public MethodCallTimeNode(MethodLocator loc, double avgTime) {
		super();
		this.loc = loc;
		this.avgTime = avgTime;
	}

	public boolean isDataNode(){
		return true;
	}
	
	public String getText(){
		return loc.methodName;
	}
	
	public double getAvgTime(){
		return avgTime;
	}
	
	public Collection<AvgTimeNode> getChildren(){
		return Collections.emptyList();
	}
}
