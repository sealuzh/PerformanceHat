package eu.cloudwave.wp5.feedback.eclipse.performance.core.builders;

import java.util.Stack;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ProgrammMarkerContext;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.Invocation;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.LoopStatement;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.MethodOccurence;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.visitor.ProgrammMarkerVisitor;

public class AstDelegator extends ASTVisitor  {
	 private final Stack<ProgrammMarkerVisitor> visitors = new Stack<>();
	 private ProgrammMarkerContext context;

	 public AstDelegator(ProgrammMarkerVisitor visitor, ProgrammMarkerContext context) {
		 this.context = context;
		 this.visitors.add(visitor);
	 }

	//make vararg
	 private boolean handleVisitReturn(ProgrammMarkerVisitor... ms){
		 ProgrammMarkerVisitor valid = null;
		 for(ProgrammMarkerVisitor m : ms){
			 if(m != null){
				 if(valid == null) valid = m;
				 else throw new IllegalStateException("Only One Methode per ASTNode can return a ChildVisitor");
			 }
		 }
		 
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
		 context = new ProgrammMarkerMathodContext(context, decl);
		 ProgrammMarkerVisitor m1 = visitors.peek().visit(decl);
		 ProgrammMarkerVisitor m2 = visitors.peek().visit((MethodOccurence)decl);
		 return handleVisitReturn(m1, m2);
     }
	 
	 public void endVisit(final MethodDeclaration methodDeclaration) {
		 context = ((ProgrammMarkerMathodContext)context).base;
		 handleEndVisit();
	 }
	 
	
     @Override
     public boolean visit(final MethodInvocation methodInvocation) {
    	 eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.MethodInvocation decl = new eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.MethodInvocation(methodInvocation,context);
		 ProgrammMarkerVisitor m1 = visitors.peek().visit(decl);
		 ProgrammMarkerVisitor m2 = visitors.peek().visit((MethodOccurence)decl);
		 ProgrammMarkerVisitor m3 = visitors.peek().visit((Invocation)decl);
		 return handleVisitReturn(m1, m2, m3);
     }

     public void endVisit(final MethodInvocation methodInvocation){
		 handleEndVisit();
	 }

     @Override
     public boolean visit(final ClassInstanceCreation newInstance) {
    	 eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.ClassInstanceCreation decl = new eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.ClassInstanceCreation(newInstance,context);
		 ProgrammMarkerVisitor m1 = visitors.peek().visit(decl);
		 ProgrammMarkerVisitor m2 = visitors.peek().visit((Invocation)decl);
		 return handleVisitReturn(m1, m2);
     }
     
     public void endVisit(final ClassInstanceCreation newInstance) {
		 handleEndVisit();
     }
     
     @Override
     public boolean visit(final EnhancedForStatement foreachStatement) {
    	 eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.ForEachStatement decl = new eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.ForEachStatement(foreachStatement,context);
		 ProgrammMarkerVisitor m1 = visitors.peek().visit(decl);
		 ProgrammMarkerVisitor m2 = visitors.peek().visit((LoopStatement)decl);
		 return handleVisitReturn(m1, m2);
     }
     
     public void endVisit(final EnhancedForStatement foreachStatement) {
		 handleEndVisit();
     }

     @Override
     public boolean visit(final ForStatement forStatement) {
    	 eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.ForStatement decl = new eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.ForStatement(forStatement,context);
		 ProgrammMarkerVisitor m1 = visitors.peek().visit(decl);
		 ProgrammMarkerVisitor m2 = visitors.peek().visit((LoopStatement)decl);
		 return handleVisitReturn(m1, m2);
     }
     
     public void endVisit(final ForStatement forStatement) {
		 handleEndVisit();
     }

     
}


