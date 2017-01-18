package eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.base.Optional;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.AstContext;

/**
 * A IAstNode representing a try catch finally statement
 * @author Markus Knecht
 *
 */
public class Try extends AAstNode<org.eclipse.jdt.core.dom.TryStatement> {
	
	//Lazy calced SubNodes
	private Block body= null;
	private List<CatchClause> catches = null;
	private Optional<Block> fin  = null;

	
	Try(org.eclipse.jdt.core.dom.TryStatement tryStat, AstContext ctx) {
		super(tryStat,ctx);
	}
	
	/**
	 * Gets the try Block
	 * @return the try block node
	 */
	public Block getBody(){
		if(body == null){
			body = StaticAstFactory.createBlock(inner.getBody(),ctx);
		}
		return body;
	}

	/**
	 * Gets the catch clauses
	 * @return the catch clause nodes as list (list may be empty)
	 */
	@SuppressWarnings("unchecked")
	public List<CatchClause> getCactchClauses(){
		if(catches == null){
			catches = ((List<org.eclipse.jdt.core.dom.CatchClause>)inner.catchClauses()).stream().map(b ->  StaticAstFactory.createCatchClause(b,ctx)).collect(Collectors.toList());
		}
		return catches;
	}

	/**
	 * Gets the finally Block
	 * @return the finally block node or null if their is none
	 */
	public Optional<Block> getFinally(){
		if(fin == null){
			if(inner.getFinally() == null){
				fin = Optional.absent();
			} else {
				fin = Optional.of(StaticAstFactory.createBlock(inner.getFinally(),ctx));
			}
		}
		
		return fin;
	}
}
