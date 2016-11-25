package eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.timestats;

import java.util.Collection;
import java.util.Collections;

import org.apache.commons.lang3.NotImplementedException;

import com.google.common.collect.Lists;

public class BlockTimeNode implements AvgTimeNode{

	private final String text;
	private final double avgTime;
	private final Collection<AvgTimeNode> childs;
	
	public BlockTimeNode(String text, double avgTime, Collection<AvgTimeNode> childs) {
		this.text = text;
		this.avgTime = avgTime;
		this.childs = childs;
	}
	
	public BlockTimeNode(String text, double avgTime, AvgTimeNode... childs) {
		this(text,avgTime,Lists.newArrayList(childs));
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
		return childs;
	}
}
