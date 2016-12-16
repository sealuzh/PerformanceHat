package eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.AstContext;

/**
 * A Block Node represents a java code block  starting with a "{" end ending with a "}"
 *  Each block defines its own Scope 
 * @author Markus Knecht
 */
public class Block extends AAstNode<org.eclipse.jdt.core.dom.Block> {
	Block(org.eclipse.jdt.core.dom.Block block, AstContext ctx) {
		super(block,ctx);
	}
}
