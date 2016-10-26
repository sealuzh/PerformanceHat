package eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast;

import eu.cloudwave.wp5.common.model.Procedure;

public interface MethodOccurence extends IAstNode{
	 public Procedure createCorrelatingProcedure();
}
