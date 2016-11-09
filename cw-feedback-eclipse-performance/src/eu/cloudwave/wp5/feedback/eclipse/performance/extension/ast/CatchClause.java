package eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ProgrammMarkerContext;

public class CatchClause extends AAstNode<org.eclipse.jdt.core.dom.CatchClause> {
	
	public CatchClause(org.eclipse.jdt.core.dom.CatchClause catchClause, ProgrammMarkerContext ctx) {
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
