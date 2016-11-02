package eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast;

import eu.cloudwave.wp5.feedback.eclipse.performance.core.tag.MethodLocator;

//Todo: make methode occurence
public interface Invocation extends Expression {
	  
	  //public ProcedureKind getProcedureKind();
	  
	  public  MethodLocator createCorrespondingMethodLocation();
}
