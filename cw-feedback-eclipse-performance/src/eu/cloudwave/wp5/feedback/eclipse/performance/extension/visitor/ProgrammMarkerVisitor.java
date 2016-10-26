package eu.cloudwave.wp5.feedback.eclipse.performance.extension.visitor;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.*;

public abstract class ProgrammMarkerVisitor {

	public boolean shouldVisitChilds(){return true;};
	protected static ProgrammMarkerVisitor CONTINUE = null;
	protected static ProgrammMarkerVisitor SKIP_CHILDS = new ProgrammMarkerVisitor() {
		@Override
		public boolean shouldVisitChilds() {
			return true;
		}
	};
	
	
	public ProgrammMarkerVisitor visit(LoopStatement loop){return CONTINUE;};
	public ProgrammMarkerVisitor visit(ForEachStatement forEach){return CONTINUE;};
	public ProgrammMarkerVisitor visit(ForStatement forLoop){return CONTINUE;};

	public ProgrammMarkerVisitor visit(Invocation invocation){return CONTINUE;};
	public ProgrammMarkerVisitor visit(MethodInvocation methodeCall){return CONTINUE;};
	public ProgrammMarkerVisitor visit(ClassInstanceCreation newInstance){return CONTINUE;};

	public ProgrammMarkerVisitor visit(MethodOccurence methode){return CONTINUE;};
	public ProgrammMarkerVisitor visit(MethodDeclaration declaration){return CONTINUE;};
	public ProgrammMarkerVisitor visit(ParameterDeclaration decl){return CONTINUE;};
	
	public void finish(){}
	

}
