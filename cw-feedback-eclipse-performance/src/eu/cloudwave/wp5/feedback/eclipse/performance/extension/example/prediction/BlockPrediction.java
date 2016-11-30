package eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.prediction;

import java.util.Collection;

import com.google.common.collect.Lists;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.PredictionNode;

public class BlockPrediction implements PredictionNode{

	private final String text;
	private final double avgTime;
	private final Collection<PredictionNode> childs;
	
	public BlockPrediction(String text, double avgTime, Collection<PredictionNode> childs) {
		this.text = text;
		this.avgTime = avgTime;
		this.childs = childs;
	}
	
	public BlockPrediction(String text, double avgTime, PredictionNode... childs) {
		this(text,avgTime,Lists.newArrayList(childs));
	}

	public boolean isDataNode(){
		return true;
	}
	
	public String getText(){
		return text;
	}
	
	public double getPredictedTime(){
		return avgTime;
	}
	
	public Collection<PredictionNode> getChildren(){
		return childs;
	}
}
