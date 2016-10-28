package eu.cloudwave.wp5.feedback.eclipse.performance.extension.visitor;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.*;

public abstract class ProgrammMarkerVisitor {

	public boolean shouldVisitChilds(){return true;};
	protected ProgrammMarkerVisitor CONTINUE = this;
	protected static ProgrammMarkerVisitor SKIP_CHILDS = new ProgrammMarkerVisitor() {
		@Override
		public boolean shouldVisitChilds() {
			return true;
		}
	};
	
	
	public ProgrammMarkerVisitor visit(LoopStatement loop){return null;}
	public ProgrammMarkerVisitor visit(ForEachStatement forEach){return null;}
	public ProgrammMarkerVisitor visit(ForStatement forLoop){return null;}

	public ProgrammMarkerVisitor visit(Invocation invocation){return null;}
	public ProgrammMarkerVisitor visit(Expression decl) { return null;}
	public ProgrammMarkerVisitor visit(MethodInvocation methodeCall){return null;}
	public ProgrammMarkerVisitor visit(ClassInstanceCreation newInstance){return null;}

	public ProgrammMarkerVisitor visit(MethodOccurence methode){return null;}
	public ProgrammMarkerVisitor visit(MethodDeclaration declaration){return null;}
	public ProgrammMarkerVisitor visit(ParameterDeclaration decl){return null;}

	
	public void finish(){}
	

}
