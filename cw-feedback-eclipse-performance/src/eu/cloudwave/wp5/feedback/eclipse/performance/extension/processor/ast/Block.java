package eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.AstContext;

//todo: make statement super interface
public class Block extends AAstNode<org.eclipse.jdt.core.dom.Block> {
	
	Block(org.eclipse.jdt.core.dom.Block block, AstContext ctx) {
		super(block,ctx);
	}


}
