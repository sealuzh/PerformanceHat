package eu.cloudwave.wp5.feedback.eclipse.performance.extension.visitor;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.Block;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.Branching;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.CatchClause;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.ConstructorInvocation;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.For;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.ForEach;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.IAstNode;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.Invocation;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.Loop;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.MethodDeclaration;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.MethodInvocation;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.MethodOccurence;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.ParameterDeclaration;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.Try;

/**
 * The Visitor for the Performance Ast
 * @author Markus Knecht
 *
 */
public abstract class PerformanceVisitor {

	/**
	 * Called before the children of a node are visited, allows to skip all children by returning false
	 * @param node which children are under consideration for visiting
	 * @return false if children should be skipped true otherwise
	 */
	public boolean shouldVisitChilds(IAstNode node){return true;};
	
	/**
	 * Called before a node is visited, allows to skip the node (including its children)
	 * @param node which is under consideration for visitation
	 * @return false if should be skipped true otherwise
	 */
	public boolean shouldVisitNode(IAstNode node){return true;};
	
	
	/**
	 * Allows to exchange the visitor for a single node (and its child)
	 * @param node for which the visitor is requested
	 * @return the new visitor for the node or null if the current should be used
	 */
	public PerformanceVisitor concreteNodeVisitor(IAstNode node){return null;};
	
	//A Visitor, that can be usesd as visit return if the visitation should continue with the same Visitor
	protected PerformanceVisitor CONTINUE = this;

	//A Visitor, that can be usesd as visit return if the visitation should skip the childs of the active noder
	protected static PerformanceVisitor SKIP_CHILDS = new PerformanceVisitor() {
		@Override
		public boolean shouldVisitChilds(IAstNode node) {
			return false;
		}
	};
	
	protected static PerformanceVisitor USE_NEXT_VISIT = null;
	
	//All the visit methods
	
	public PerformanceVisitor visit(Loop loop){return USE_NEXT_VISIT;}
	public PerformanceVisitor visit(ForEach forEach){return USE_NEXT_VISIT;}
	public PerformanceVisitor visit(For forLoop){return USE_NEXT_VISIT;}

	public PerformanceVisitor visit(Invocation invocation){return USE_NEXT_VISIT;}
	public PerformanceVisitor visit(MethodInvocation methodeCall){return USE_NEXT_VISIT;}
	public PerformanceVisitor visit(ConstructorInvocation newInstance){return USE_NEXT_VISIT;}

	public PerformanceVisitor visit(MethodOccurence methode){return USE_NEXT_VISIT;}
	public PerformanceVisitor visit(MethodDeclaration declaration){return USE_NEXT_VISIT;}
	public PerformanceVisitor visit(ParameterDeclaration decl){return USE_NEXT_VISIT;}

	public PerformanceVisitor visit(CatchClause decl) {return USE_NEXT_VISIT;}
	public PerformanceVisitor visit(Block decl) {return USE_NEXT_VISIT;}
	public PerformanceVisitor visit(Branching decl){return USE_NEXT_VISIT;}
	public PerformanceVisitor visit(Try decl){return USE_NEXT_VISIT;}
	
	/**
	 * Is called when this visitor has visited its last node and will be discarded
	 */
	public void finish(){}
	


}
