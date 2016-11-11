package eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.timestats;

import java.util.Collection;

public interface AvgTimeNode {
	public boolean isDataNode();
	public String getText();
	public double getAvgTime();
	public Collection<AvgTimeNode> getChildren();
}
