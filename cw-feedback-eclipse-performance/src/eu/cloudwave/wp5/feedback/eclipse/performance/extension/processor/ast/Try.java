package eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast;

import java.util.List;
import java.util.stream.Collectors;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.AstContext;

/**
 * A IAstNode representing a try catch finally statement
 * @author Markus Knecht
 *
 */
public class Try extends AAstNode<org.eclipse.jdt.core.dom.TryStatement> {
	
	Try(org.eclipse.jdt.core.dom.TryStatement tryStat, AstContext ctx) {
		super(tryStat,ctx);
	}
	
	/**
	 * Gets the try Block
	 * @return the try block node
	 */
	public Block getBody(){
		return StaticAstFactory.createBlock(inner.getBody(),ctx);
	}

	/**
	 * Gets the catch clauses
	 * @return the catch clause nodes as list (list may be empty)
	 */
	@SuppressWarnings("unchecked")
	public List<CatchClause> getCactchClauses(){
		return ((List<org.eclipse.jdt.core.dom.CatchClause>)inner.catchClauses()).stream().map(b ->  StaticAstFactory.createCatchClause(b,ctx)).collect(Collectors.toList());
	}

	/**
	 * Gets the finally Block
	 * @return the finally block node or null if their is none
	 */
	public Block getFinally(){
		if(inner.getFinally() == null) return null;
		return StaticAstFactory.createBlock(inner.getFinally(),ctx);
	}
}
