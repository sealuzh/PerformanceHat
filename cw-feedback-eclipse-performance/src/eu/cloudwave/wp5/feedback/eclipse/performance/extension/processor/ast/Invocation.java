package eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast;

import java.util.List;

import eu.cloudwave.wp5.feedback.eclipse.performance.core.tag.MethodLocator;

/**
 * A IAstNode representing all invocations
 * This includes: Method Calls and Constructor Calls
 * @author Markus Knecht
 *
 */
public interface Invocation extends MethodOccurence {
	 /**
	  * {@inheritDoc}
	  */
	  public  MethodLocator createCorrespondingMethodLocation();
	  
	  /**
	   * Gets all the nodes that represent the arguments.
	   * The receiver is in position 0 if a receiver is present
	   * @return
	   */
	  public List<IAstNode> getArguments();
	  
}
