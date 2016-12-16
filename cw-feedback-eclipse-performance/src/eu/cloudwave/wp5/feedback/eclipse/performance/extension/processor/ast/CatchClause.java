package eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.AstContext;

/**
 * A IAstNode representing a catch clause of a try catch finally expression
 * @author Markus Knecht
 *
 */
public class CatchClause extends AAstNode<org.eclipse.jdt.core.dom.CatchClause> {
	
	CatchClause(org.eclipse.jdt.core.dom.CatchClause catchClause, AstContext ctx) {
		super(catchClause,ctx);
	}

	//TODO: Class Loacator would be better as soon as they exist
	/**
	 * Fetches the name of the exception catched by this clause
	 * @return the fully qualified exception name
	 */
	public String getExceptionQualifiedName(){
		return inner.getException().getType().toString();
	}
	
	//TODO: Exception variable
	
	/**
	 * Gets the Block node representing the Computation excecuted if the specified exeption occurs
	 * @return the catch Block
	 */
	public Block getBody(){
		return StaticAstFactory.createBlock(inner.getBody(),ctx);
	}

}
