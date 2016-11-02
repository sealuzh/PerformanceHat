package eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast;

import eu.cloudwave.wp5.feedback.eclipse.performance.core.tag.MethodLocator;

public interface MethodOccurence extends IAstNode{
	 public MethodLocator createCorrespondingMethodLocation(); 
	 public String getMethodKind();
}
