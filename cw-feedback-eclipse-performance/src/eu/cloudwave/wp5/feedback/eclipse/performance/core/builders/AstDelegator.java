package eu.cloudwave.wp5.feedback.eclipse.performance.core.builders;

import java.util.Stack;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ProgrammMarkerContext;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.Expression;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.Invocation;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.LoopStatement;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.MethodOccurence;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.visitor.ProgrammMarkerVisitor;

public class AstDelegator extends ASTVisitor  {
	 private final Stack<ProgrammMarkerVisitor> visitors = new Stack<>();
	 private ProgrammMarkerContext context;
	 
	 public ProgrammMarkerVisitor getCurrent(){
		 return visitors.peek();
	 }
	 
	 public AstDelegator(final ProgrammMarkerVisitor visitor, ProgrammMarkerContext context) {
		 this.context = context;
		 this.visitors.add(visitor);
	 }

	//make vararg
	 private boolean handleVisitReturn(ProgrammMarkerVisitor pmv){
		 ProgrammMarkerVisitor valid = null;
		 if(pmv != null && pmv !=  getCurrent()) valid = pmv;
		
		 if(valid == null) {
			 visitors.push(visitors.peek());
		     return true;
		 }
		 
		 visitors.push(valid);
		 return valid.shouldVisitChilds();
		 
	 }
	 
	 private void handleEndVisit(){
		 ProgrammMarkerVisitor old =  visitors.pop();
		 if(visitors.isEmpty() || visitors.peek() != old ){
			old.finish();
		 }
	 }
	
	 @Override
     public boolean visit(final MethodDeclaration methodDeclaration) {
		 eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.MethodDeclaration decl = new eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.MethodDeclaration(methodDeclaration,context);
		 context = new ProgrammMarkerMethodContext(context, decl);
		 ProgrammMarkerVisitor m = getCurrent().visit(decl);
		 if(m == null) m = getCurrent().visit((MethodOccurence)decl);
		 return handleVisitReturn(m);
     }
	 
	 public void endVisit(final MethodDeclaration methodDeclaration) {
		 context = ((ProgrammMarkerMethodContext)context).base;
		 handleEndVisit();
	 }
	 
	 
	
     @Override
	public boolean visit(SingleVariableDeclaration node) {
    	 int count = 0;
    	 
    	 for(Object param :context.getCurrentMethode().getEclipseAstNode().parameters()){
    		if(param.equals(node)) {
    	    	 eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.ParameterDeclaration decl = new eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.ParameterDeclaration(count,context.getCurrentMethode(),node,context);
    	    	 ProgrammMarkerVisitor m = getCurrent().visit(decl);
    			 return handleVisitReturn(m);
    		}
    		count++;
    	 }
    	 
    	 return handleVisitReturn((ProgrammMarkerVisitor)null);

	}

	@Override
	public void endVisit(SingleVariableDeclaration node) {
		 handleEndVisit();
	}

	@Override
     public boolean visit(final MethodInvocation methodInvocation) {
    	 eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.MethodInvocation decl = new eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.MethodInvocation(methodInvocation,context);
    	 ProgrammMarkerVisitor m = getCurrent().visit(decl);
   	  	 if(m == null) m = getCurrent().visit((Invocation)decl);
   	  	 if(m == null) m = getCurrent().visit((Expression)decl);
   	  	 if(m == null) m = getCurrent().visit((MethodOccurence)decl);
		 return handleVisitReturn(m);
     }

     public void endVisit(final MethodInvocation methodInvocation){
		 handleEndVisit();
	 }

     @Override
     public boolean visit(final ClassInstanceCreation newInstance) {
    	 eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.ClassInstanceCreation decl = new eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.ClassInstanceCreation(newInstance,context);
    	 ProgrammMarkerVisitor m = getCurrent().visit(decl);
    	 if(m == null) m = getCurrent().visit((Invocation)decl);
   	  	 if(m == null) m = getCurrent().visit((Expression)decl);
		 return handleVisitReturn(m);
     }
     
     public void endVisit(final ClassInstanceCreation newInstance) {
		 handleEndVisit();
     }
     
     @Override
     public boolean visit(final EnhancedForStatement foreachStatement) {
    	 eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.ForEachStatement decl = new eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.ForEachStatement(foreachStatement,context);
    	 ProgrammMarkerVisitor m = getCurrent().visit(decl);
    	 if(m == null) m = getCurrent().visit((LoopStatement)decl);
		 return handleVisitReturn(m);
     }
     
     public void endVisit(final EnhancedForStatement foreachStatement) {
		 handleEndVisit();
     }

     @Override
     public boolean visit(final ForStatement forStatement) {
    	 eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.ForStatement decl = new eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.ForStatement(forStatement,context);
    	 ProgrammMarkerVisitor m = getCurrent().visit(decl);
    	 if(m == null) m = getCurrent().visit((LoopStatement)decl);
		 return handleVisitReturn(m);
     }
     
     public void endVisit(final ForStatement forStatement) {
		 handleEndVisit();
     }

     
}


