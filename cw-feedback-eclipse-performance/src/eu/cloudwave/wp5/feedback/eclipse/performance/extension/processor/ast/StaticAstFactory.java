package eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;

import com.google.common.collect.Lists;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.AstContext;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.IAstNode.NodeWrapper;

/**
 * Class for generating instances of IAstNode's
 * All instances should be created over this.
 * @author Markus Knecht
 *
 */
public class StaticAstFactory {
	
	/**
	 * Allows to create an IAstNode from an Eclipse ASTNode even if the concrete type is unknown
	 * @param node is the eclipse ASTNode
	 * @param ctx is the Performance AstContext
	 * @return the corresponding IAstNode
	 */
	public static IAstNode fromEclipseAstNodeOrDefault(ASTNode node, AstContext ctx){
		IAstNode resolved = fromEclipseAstNode(node, ctx);
		if(resolved == null) return new NodeWrapper(node, ctx);
		return resolved;
	}
	
	/* May become relevant for visit impl of Node Wrapper again
	public static List<IAstNode> extractAllFromEclipseAstNode(ASTNode node, AstContext ctx){
		final List<IAstNode> res = Lists.newArrayList();
		IAstNode resolved = fromEclipseAstNode(node, ctx);
		if(resolved != null){
			res.add(resolved);
		} else {
			node.accept(new ASTVisitor() {
				@Override
				public boolean preVisit2(ASTNode node) {
					IAstNode resolved = fromEclipseAstNode(node, ctx);
					if(resolved != null){
						res.add(resolved);
						return false;
					}
					return super.preVisit2(node);
				}

				
				
			});
		}
		
		return res;
	}*/
	
	//expensive method, but often jit inlining should take care of it
	private static IAstNode fromEclipseAstNode(ASTNode node, AstContext ctx){
		//without Meta programming that allows to add methods to ASTNode's (which java has not), their is no better option
		switch (node.getNodeType()) {
			case ASTNode.COMPILATION_UNIT: 
				return createAstRoot((org.eclipse.jdt.core.dom.CompilationUnit)node, ctx);
			case ASTNode.METHOD_INVOCATION: 
				return createMethodInvocation((org.eclipse.jdt.core.dom.MethodInvocation)node, ctx);
			case ASTNode.SUPER_METHOD_INVOCATION: 
				return createMethodInvocation((org.eclipse.jdt.core.dom.SuperMethodInvocation)node, ctx);
			case ASTNode.TRY_STATEMENT: 
				return createTry((org.eclipse.jdt.core.dom.TryStatement)node, ctx);			
			case ASTNode.METHOD_DECLARATION: 
				return createMethodDeclaration((org.eclipse.jdt.core.dom.MethodDeclaration)node, ctx);
			case ASTNode.ENHANCED_FOR_STATEMENT: 
				return createForEach((org.eclipse.jdt.core.dom.EnhancedForStatement)node, ctx);
			case ASTNode.FOR_STATEMENT: 
				return createFor((org.eclipse.jdt.core.dom.ForStatement)node, ctx);
			case ASTNode.CLASS_INSTANCE_CREATION: 
				return createConstructorInvocation((org.eclipse.jdt.core.dom.ClassInstanceCreation)node, ctx);
			case ASTNode.CONSTRUCTOR_INVOCATION: 
				return createConstructorInvocation((org.eclipse.jdt.core.dom.ConstructorInvocation)node, ctx);
			case ASTNode.SUPER_CONSTRUCTOR_INVOCATION: 
				return createConstructorInvocation((org.eclipse.jdt.core.dom.SuperConstructorInvocation)node, ctx);
			case ASTNode.CATCH_CLAUSE: 
				return createCatchClause((org.eclipse.jdt.core.dom.CatchClause)node, ctx);
			case ASTNode.IF_STATEMENT: 
				return createBranching((org.eclipse.jdt.core.dom.IfStatement)node, ctx);
			case ASTNode.CONDITIONAL_EXPRESSION: 
				return createBranching((org.eclipse.jdt.core.dom.ConditionalExpression)node, ctx);
			case ASTNode.SWITCH_STATEMENT: 
				return createBranching((org.eclipse.jdt.core.dom.SwitchStatement)node, ctx);
			case ASTNode.BLOCK: 
				return createBlock((org.eclipse.jdt.core.dom.Block)node, ctx);
			default:
				return null;
		}
	}
	
	//Following one method per node type (or multiple if their are multiple option for backing Eclipse Nodes)
	//TODO: make Java Doc
	
	public static AstRoot createAstRoot(org.eclipse.jdt.core.dom.CompilationUnit node, AstContext ctx){
		return new AstRoot(node, ctx);
	}
	
	public static MethodInvocation createMethodInvocation(org.eclipse.jdt.core.dom.MethodInvocation node, AstContext ctx){
		return new MethodInvocation(node, ctx);
	}
	
	public static MethodInvocation createMethodInvocation(org.eclipse.jdt.core.dom.SuperMethodInvocation node, AstContext ctx){
		return new MethodInvocation(node, ctx);
	}
	
	public static Try createTry(org.eclipse.jdt.core.dom.TryStatement node, AstContext ctx){
		return new Try(node, ctx);
	}
	
	public static MethodDeclaration createMethodDeclaration(org.eclipse.jdt.core.dom.MethodDeclaration node, AstContext ctx){
		return new MethodDeclaration(node, ctx);
	}
	
	public static ForEach createForEach(org.eclipse.jdt.core.dom.EnhancedForStatement node, AstContext ctx){
		return new ForEach(node, ctx);
	}
	
	public static For createFor(org.eclipse.jdt.core.dom.ForStatement node, AstContext ctx){
		return new For(node, ctx);
	}
	
	public static ConstructorInvocation createConstructorInvocation(org.eclipse.jdt.core.dom.ClassInstanceCreation node, AstContext ctx){
		return new ConstructorInvocation(node, ctx);
	}
	
	public static ConstructorInvocation createConstructorInvocation(org.eclipse.jdt.core.dom.ConstructorInvocation node, AstContext ctx){
		return new ConstructorInvocation(node, ctx);
	}
	
	public static ConstructorInvocation createConstructorInvocation(org.eclipse.jdt.core.dom.SuperConstructorInvocation node, AstContext ctx){
		return new ConstructorInvocation(node, ctx);
	}
	
	public static CatchClause createCatchClause(org.eclipse.jdt.core.dom.CatchClause node, AstContext ctx){
		return new CatchClause(node, ctx);
	}
	
	public static Branching createBranching(org.eclipse.jdt.core.dom.IfStatement node, AstContext ctx){
		return new Branching(node, ctx);
	}
	 
	public static Branching createBranching(org.eclipse.jdt.core.dom.ConditionalExpression node, AstContext ctx){
		return new Branching(node, ctx);
	}
	 
	public static Branching createBranching(org.eclipse.jdt.core.dom.SwitchStatement node, AstContext ctx){
		return new Branching(node, ctx);
	}
	
	public static Block createBlock(org.eclipse.jdt.core.dom.Block node, AstContext ctx){
		return new Block(node, ctx);
	}

	public static ParameterDeclaration createParameterDeclaration(int count, SingleVariableDeclaration node, AstContext context) {
		return new ParameterDeclaration(count,node,context);
	}

}
