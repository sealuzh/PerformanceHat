package eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.prediction;

import java.util.Collection;
import java.util.Collections;

import eu.cloudwave.wp5.feedback.eclipse.performance.core.tag.MethodLocator;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.PredictionNode;

public class MethodCallPrediction implements PredictionNode{
	private final MethodLocator loc;
	private final double avgTime;

	public MethodCallPrediction(MethodLocator loc, double avgTime) {
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
	
	public double getPredictedTime(){
		return avgTime;
	}
	
	public Collection<PredictionNode> getChildren(){
		return Collections.emptyList();
	}
}
