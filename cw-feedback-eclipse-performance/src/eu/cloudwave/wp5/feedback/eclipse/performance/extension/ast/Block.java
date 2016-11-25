package eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ProgrammMarkerContext;

//todo: make statement super interface
public class Block extends AAstNode<org.eclipse.jdt.core.dom.Block> {
	
	Block(org.eclipse.jdt.core.dom.Block block, ProgrammMarkerContext ctx) {
		super(block,ctx);
	}


}
