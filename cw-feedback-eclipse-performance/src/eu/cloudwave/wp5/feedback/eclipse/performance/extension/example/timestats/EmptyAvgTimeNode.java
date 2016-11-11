package eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.timestats;

import java.util.Collection;
import java.util.Collections;

import org.apache.commons.lang3.NotImplementedException;

public class EmptyAvgTimeNode implements AvgTimeNode{
	private final Collection<AvgTimeNode> childs;
	
	public EmptyAvgTimeNode(Collection<AvgTimeNode> childs) {
		this.childs = childs;
	}

	public boolean isDataNode(){
		return true;
	}
	
	public String getText(){
		throw new NotImplementedException("getText() not avaiable on a node where isDataNode() == false");
	}
	
	public double getAvgTime(){
		throw new NotImplementedException("getAvgTime() not avaiable on a node where isDataNode() == false");
	}
	
	public Collection<AvgTimeNode> getChildren(){
		return childs;
	}
	
}
