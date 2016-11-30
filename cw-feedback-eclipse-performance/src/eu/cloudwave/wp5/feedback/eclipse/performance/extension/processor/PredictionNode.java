package eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor;

import java.util.Collection;

public interface PredictionNode {
	//Defines if the node needs to be printed or just its childs
	public boolean isDataNode();
	//Get the description text of the node (will be indented)
	public String getText();
	//Get the predicted time
	public double getPredictedTime();
	//get the childs this prediction depends on
	public Collection<PredictionNode> getChildren();
}
