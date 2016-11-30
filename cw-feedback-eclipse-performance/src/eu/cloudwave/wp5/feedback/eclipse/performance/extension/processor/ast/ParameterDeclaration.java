package eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast;

import java.util.List;

import com.google.common.collect.Lists;

import eu.cloudwave.wp5.feedback.eclipse.performance.core.tag.MethodLocator;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.AstContext;

public class ParameterDeclaration extends AAstNode<org.eclipse.jdt.core.dom.SingleVariableDeclaration>{

	private final MethodDeclaration method;
	private final int paramPos;
	
	ParameterDeclaration(int paramPos, MethodDeclaration method, org.eclipse.jdt.core.dom.SingleVariableDeclaration inner, AstContext ctx) {
		super(inner, ctx);
		this.method = method;
		this.paramPos = paramPos;
	}
	
	public int getPosition(){
		return paramPos;
	}
}
