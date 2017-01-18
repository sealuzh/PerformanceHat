package eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;

import com.google.common.collect.Lists;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.AstContext;

/**
 * A Block Node represents a java code block  starting with a "{" end ending with a "}"
 *  Each block defines its own Scope 
 * @author Markus Knecht
 */
public class Block extends AAstNode<org.eclipse.jdt.core.dom.Block> {
	
	//Lazy calced SubNodes
	private List<IAstNode> statements = null;
	
	Block(org.eclipse.jdt.core.dom.Block block, AstContext ctx) {
		super(block,ctx);
	}
	
	public List<IAstNode> getStatements(){
		if(statements == null){
			statements = Lists.newArrayList();
			List<ASTNode> stms = inner.statements();
			for(ASTNode node:stms){
				statements.add(StaticAstFactory.fromEclipseAstNodeOrDefault(node, ctx));
			}
		}
		return statements;
	}
}
