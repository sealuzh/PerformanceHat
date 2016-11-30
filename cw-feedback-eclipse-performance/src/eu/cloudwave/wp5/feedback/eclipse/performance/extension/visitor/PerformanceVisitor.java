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

public abstract class PerformanceVisitor {

	public boolean shouldVisitChilds(){return true;};
	public boolean shouldVisitNode(IAstNode node){return true;};
	//todo: needs better name
	public PerformanceVisitor concreteNodeVisitor(IAstNode node){return null;};
	
	protected PerformanceVisitor CONTINUE = this;
	protected static PerformanceVisitor SKIP_CHILDS = new PerformanceVisitor() {
		@Override
		public boolean shouldVisitChilds() {
			return false;
		}
	};
	
	
	public PerformanceVisitor visit(Loop loop){return null;}
	public PerformanceVisitor visit(ForEach forEach){return null;}
	public PerformanceVisitor visit(For forLoop){return null;}

	public PerformanceVisitor visit(Invocation invocation){return null;}
	public PerformanceVisitor visit(MethodInvocation methodeCall){return null;}
	public PerformanceVisitor visit(ConstructorInvocation newInstance){return null;}

	public PerformanceVisitor visit(MethodOccurence methode){return null;}
	public PerformanceVisitor visit(MethodDeclaration declaration){return null;}
	public PerformanceVisitor visit(ParameterDeclaration decl){return null;}

	public PerformanceVisitor visit(CatchClause decl) {return null;}
	public PerformanceVisitor visit(Block decl) {return null;}
	public PerformanceVisitor visit(Branching decl){return null;}
	public PerformanceVisitor visit(Try decl){return null;}
	
	public void finish(){}
	


}
