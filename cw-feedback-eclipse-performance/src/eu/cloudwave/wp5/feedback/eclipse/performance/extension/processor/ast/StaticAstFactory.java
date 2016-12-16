package eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast;

import org.eclipse.jdt.core.dom.SingleVariableDeclaration;

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
	//expensive method, but often jit inlining should take care of it
	public static IAstNode fromEclipseAstNode(org.eclipse.jdt.core.dom.ASTNode node, AstContext ctx){
		//without Meta programming that allows to add methods to ASTNode's (which java has not), their is no better option
		if(node instanceof org.eclipse.jdt.core.dom.MethodInvocation){
			return createMethodInvocation((org.eclipse.jdt.core.dom.MethodInvocation)node, ctx);
		} else if(node instanceof org.eclipse.jdt.core.dom.SuperMethodInvocation){
			return createMethodInvocation((org.eclipse.jdt.core.dom.SuperMethodInvocation)node, ctx);
		} else if(node instanceof org.eclipse.jdt.core.dom.TryStatement){
			return createTry((org.eclipse.jdt.core.dom.TryStatement)node, ctx);
		} else if(node instanceof org.eclipse.jdt.core.dom.MethodDeclaration){
			return createMethodDeclaration((org.eclipse.jdt.core.dom.MethodDeclaration)node, ctx);
		} else if(node instanceof org.eclipse.jdt.core.dom.EnhancedForStatement){
			return createForEach((org.eclipse.jdt.core.dom.EnhancedForStatement)node, ctx);
		} else if(node instanceof org.eclipse.jdt.core.dom.ForStatement){
			return createFor((org.eclipse.jdt.core.dom.ForStatement)node, ctx);
		} else if(node instanceof org.eclipse.jdt.core.dom.ClassInstanceCreation){
			return createConstructorInvocation((org.eclipse.jdt.core.dom.ClassInstanceCreation)node, ctx);
		} else if(node instanceof org.eclipse.jdt.core.dom.ConstructorInvocation){
			return createConstructorInvocation((org.eclipse.jdt.core.dom.ConstructorInvocation)node, ctx);
		} else if(node instanceof org.eclipse.jdt.core.dom.SuperConstructorInvocation){
			return createConstructorInvocation((org.eclipse.jdt.core.dom.SuperConstructorInvocation)node, ctx);
		} else if(node instanceof org.eclipse.jdt.core.dom.CatchClause){
			return createCatchClause((org.eclipse.jdt.core.dom.CatchClause)node, ctx);
		} else if(node instanceof org.eclipse.jdt.core.dom.IfStatement){
			return createBranching((org.eclipse.jdt.core.dom.IfStatement)node, ctx);
		} else if(node instanceof org.eclipse.jdt.core.dom.ConditionalExpression){
			return createBranching((org.eclipse.jdt.core.dom.ConditionalExpression)node, ctx);
		} else if(node instanceof org.eclipse.jdt.core.dom.SwitchStatement){
			return createBranching((org.eclipse.jdt.core.dom.SwitchStatement)node, ctx);
		} else if(node instanceof org.eclipse.jdt.core.dom.Block){
			return createBlock((org.eclipse.jdt.core.dom.Block)node, ctx);
		}
		
		return new NodeWrapper(node, ctx);
	}
	
	//Following one method per node type (or multiple if their are multiple option for backing Eclipse Nodes)
	//TODO: make Java Doc
	
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
