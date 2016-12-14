package eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.AstContext;

public class ParameterDeclaration extends AAstNode<org.eclipse.jdt.core.dom.SingleVariableDeclaration>{

	private final int paramPos;
	
	ParameterDeclaration(int paramPos, org.eclipse.jdt.core.dom.SingleVariableDeclaration inner, AstContext ctx) {
		super(inner, ctx);
		this.paramPos = paramPos;
	}
	
	public int getPosition(){
		return paramPos;
	}
}
