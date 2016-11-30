package eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.prediction;

import java.util.Collection;

import com.google.common.collect.Lists;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.PredictionNode;

public class LoopPrediction implements PredictionNode{

	private final double predTime;
	public final double avgIters;
	public final PredictionNode body;
	public final PredictionNode header;

	public LoopPrediction(double predTime, double avgIters, PredictionNode body, PredictionNode header) {
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
	
	public double getPredictedTime(){
		return predTime;
	}
	
	public Collection<PredictionNode> getChildren(){
		return Lists.newArrayList(header,body);
	}
}
