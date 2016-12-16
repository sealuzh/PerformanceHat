package eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast;

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
}
