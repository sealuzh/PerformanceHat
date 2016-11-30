package eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.AstContext;

public class CatchClause extends AAstNode<org.eclipse.jdt.core.dom.CatchClause> {
	
	CatchClause(org.eclipse.jdt.core.dom.CatchClause catchClause, AstContext ctx) {
		super(catchClause,ctx);
	}

	public String getExceptionQualifiedName(){
		return inner.getException().getType().toString();
	}
	
	//Todo: Exception variable
	
	public Block getBody(){
		return new Block(inner.getBody(),ctx);
	}

}
