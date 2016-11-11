package eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.timestats;

import java.util.Collection;
import java.util.Collections;

import org.apache.commons.lang3.NotImplementedException;

public class AvgTimeLeafe implements AvgTimeNode{
	private final String text;
	private final double avgTime;

	public AvgTimeLeafe(String text, double avgTime) {
		super();
		this.text = text;
		this.avgTime = avgTime;
	}

	public boolean isDataNode(){
		return true;
	}
	
	public String getText(){
		return text;
	}
	
	public double getAvgTime(){
		return avgTime;
	}
	
	public Collection<AvgTimeNode> getChildren(){
		return Collections.emptyList();
	}
}
