package eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast;

import eu.cloudwave.wp5.feedback.eclipse.performance.core.tag.MethodLocator;

//Todo: make methode occurence
public interface Invocation extends MethodOccurence {
	  
	  //public ProcedureKind getProcedureKind();
	  
	  public  MethodLocator createCorrespondingMethodLocation();
}
