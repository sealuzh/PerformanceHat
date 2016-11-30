package eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.prediction;

import java.util.Collection;
import java.util.Collections;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.PredictionNode;

public class LoopPredictionError implements PredictionNode{

	public boolean isDataNode(){
		return true;
	}
	
	public String getText(){
		return "<Iteration prediction failed>";
	}
	
	public double getPredictedTime(){
		return 0;
	}
	
	public Collection<PredictionNode> getChildren(){
		return Collections.emptyList();
	}
}
