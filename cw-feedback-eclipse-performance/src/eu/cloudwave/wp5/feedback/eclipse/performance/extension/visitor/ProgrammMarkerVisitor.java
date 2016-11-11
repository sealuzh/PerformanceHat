package eu.cloudwave.wp5.feedback.eclipse.performance.extension.visitor;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.Block;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.CatchClause;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.ConstructorInvocation;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.ForEachStatement;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.ForStatement;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.IAstNode;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.Invocation;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.LoopStatement;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.MethodDeclaration;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.MethodInvocation;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.MethodOccurence;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.ParameterDeclaration;

public abstract class ProgrammMarkerVisitor {

	public boolean shouldVisitChilds(){return true;};
	public boolean shouldVisitNode(IAstNode node){return true;};
	//todo: needs better name
	public ProgrammMarkerVisitor concreteBranchVisitor(IAstNode node){return null;};
	
	protected ProgrammMarkerVisitor CONTINUE = this;
	protected static ProgrammMarkerVisitor SKIP_CHILDS = new ProgrammMarkerVisitor() {
		@Override
		public boolean shouldVisitChilds() {
			return false;
		}
	};
	
	
	public ProgrammMarkerVisitor visit(LoopStatement loop){return null;}
	public ProgrammMarkerVisitor visit(ForEachStatement forEach){return null;}
	public ProgrammMarkerVisitor visit(ForStatement forLoop){return null;}

	public ProgrammMarkerVisitor visit(Invocation invocation){return null;}
	public ProgrammMarkerVisitor visit(MethodInvocation methodeCall){return null;}
	public ProgrammMarkerVisitor visit(ConstructorInvocation newInstance){return null;}

	public ProgrammMarkerVisitor visit(MethodOccurence methode){return null;}
	public ProgrammMarkerVisitor visit(MethodDeclaration declaration){return null;}
	public ProgrammMarkerVisitor visit(ParameterDeclaration decl){return null;}

	public ProgrammMarkerVisitor visit(CatchClause decl) {return null;}
	public ProgrammMarkerVisitor visit(Block decl) {return null;}

	
	public void finish(){}

}
