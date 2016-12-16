package eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast;

import eu.cloudwave.wp5.feedback.eclipse.performance.core.tag.MethodLocator;

/**
 * A Interface combining all the IAstNode that either declare a Method or Constructor, or Call a Method or Constructor
 * @author Markus Knecht
 *
 */
public interface MethodOccurence extends IAstNode{
	/**
	 * Fetches the information about the related method
	 * @return the corresponding MethodLocator
	 */
	 public MethodLocator createCorrespondingMethodLocation(); 
	 
	 /**
	  * generates "Constructor" or "Method" depending on what this is
	  * @return a String with the Kind of Method
	  */
	 public String getMethodKind();
}
