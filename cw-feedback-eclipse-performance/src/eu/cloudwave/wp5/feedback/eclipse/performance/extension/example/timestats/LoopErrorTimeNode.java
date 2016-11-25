package eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.timestats;

import java.util.Collection;
import java.util.Collections;

import org.apache.commons.lang3.NotImplementedException;

import eu.cloudwave.wp5.feedback.eclipse.performance.core.tag.MethodLocator;

public class LoopErrorTimeNode implements AvgTimeNode{

	public boolean isDataNode(){
		return true;
	}
	
	public String getText(){
		return "<Iteration prediction failed>";
	}
	
	public double getAvgTime(){
		return 0;
	}
	
	public Collection<AvgTimeNode> getChildren(){
		return Collections.emptyList();
	}
}
