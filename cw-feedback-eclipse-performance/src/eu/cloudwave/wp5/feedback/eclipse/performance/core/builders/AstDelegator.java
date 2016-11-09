package eu.cloudwave.wp5.feedback.eclipse.performance.core.builders;

import java.util.Stack;

import org.eclipse.jdt.core.dom.*;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ProgrammMarkerContext;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.IAstNode;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.Invocation;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.LoopStatement;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.MethodOccurence;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.visitor.ProgrammMarkerVisitor;

public class AstDelegator extends ASTVisitor  {
	
	 private static class StackEntry{
		 public final int depth;
		 public final ProgrammMarkerVisitor usedMarker;
		 public StackEntry(int depth, ProgrammMarkerVisitor usedMarker) {
			this.depth = depth;
			this.usedMarker = usedMarker;
		 }
	 }
	
	 private final Stack<StackEntry> visitors = new Stack<>();
	 private ProgrammMarkerContext context;
	 
	 private int depth = 0;
	 
	@Override
	public void preVisit(ASTNode node) {
		depth++;
	}

	@Override
	public void postVisit(ASTNode node) {
		while (!visitors.isEmpty()  && visitors.peek().depth >= depth) visitors.pop().usedMarker.finish();
		depth--;
	}
	
	public void pushChildVisitor(ProgrammMarkerVisitor visitor){
		if(visitor == null || (!visitors.isEmpty() && visitors.peek().usedMarker == visitor))return;
		visitors.push(new StackEntry(depth, visitor));
	}

	public ProgrammMarkerVisitor getCurrent(){
		 return visitors.peek().usedMarker;
	 }
	 
	 public AstDelegator(final ProgrammMarkerVisitor visitor, ProgrammMarkerContext context) {
		 this.context = context;
		 pushChildVisitor(visitor);
	 }
	 
	 private boolean handleVisitStart(IAstNode node){
//			ProgrammMarkerVisitor subst = getCurrent().getOptionalSubstitution(node);
//			pushChildVisitor(subst);
			return getCurrent().shouldVisitNode(node);
	 }

	 private boolean handleVisitReturn(ProgrammMarkerVisitor pmv){
		 if(pmv != null)  pushChildVisitor(pmv);
		 return getCurrent().shouldVisitChilds();
	 }
	
	 //Todo: lambdafy visiors
	 
	 @Override
     public boolean visit(final MethodDeclaration methodDeclaration) {
		 eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.MethodDeclaration decl = new eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.MethodDeclaration(methodDeclaration,context);
		 context = new ProgrammMarkerMethodContext(context, decl);
		 if(!handleVisitStart(decl)) return false;
		 ProgrammMarkerVisitor m = getCurrent().visit(decl);
		 if(m == null) m = getCurrent().visit((MethodOccurence)decl);
		 return handleVisitReturn(m);
     }
	 
	 public void endVisit(final MethodDeclaration methodDeclaration) {
		 context = ((ProgrammMarkerMethodContext)context).base;
	 }
	
     @Override
	public boolean visit(SingleVariableDeclaration node) {
    	 int count = 0;
    	 
    	 for(Object param :context.getCurrentMethode().getEclipseAstNode().parameters()){
    		if(param.equals(node)) {
    	    	 eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.ParameterDeclaration decl = new eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.ParameterDeclaration(count,context.getCurrentMethode(),node,context);
    			 if(!handleVisitStart(decl)) return false;
    	    	 ProgrammMarkerVisitor m = getCurrent().visit(decl);
    			 return handleVisitReturn(m);
    		}
    		count++;
    	 }
    	 return handleVisitReturn((ProgrammMarkerVisitor)null);

	}

	@Override
     public boolean visit(final MethodInvocation methodInvocation) {
    	 eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.MethodInvocation decl = new eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.MethodInvocation(methodInvocation,context);
		 if(!handleVisitStart(decl)) return false;
    	 ProgrammMarkerVisitor m = getCurrent().visit(decl);
   	  	 if(m == null) m = getCurrent().visit((Invocation)decl);
   	  	 if(m == null) m = getCurrent().visit((MethodOccurence)decl);
		 return handleVisitReturn(m);
     }

   
     @Override
     public boolean visit(final SuperMethodInvocation methodInvocation) {
    	 eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.MethodInvocation decl = new eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.MethodInvocation(methodInvocation,context);
		 if(!handleVisitStart(decl)) return false;
    	 ProgrammMarkerVisitor m = getCurrent().visit(decl);
   	  	 if(m == null) m = getCurrent().visit((Invocation)decl);
   	  	 if(m == null) m = getCurrent().visit((MethodOccurence)decl);
		 return handleVisitReturn(m);
     }
     
     @Override
     public boolean visit(final ClassInstanceCreation newInstance) {
    	 eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.ConstructorInvocation decl = new eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.ConstructorInvocation(newInstance,context);
		 if(!handleVisitStart(decl)) return false;
    	 ProgrammMarkerVisitor m = getCurrent().visit(decl);
    	 if(m == null) m = getCurrent().visit((Invocation)decl);
   	  	 if(m == null) m = getCurrent().visit((MethodOccurence)decl);
		 return handleVisitReturn(m);
     }
     
	 @Override
     public boolean visit(final ConstructorInvocation alt) {
    	 eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.ConstructorInvocation decl = new eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.ConstructorInvocation(alt,context);
		 if(!handleVisitStart(decl)) return false;
    	 ProgrammMarkerVisitor m = getCurrent().visit(decl);
    	 if(m == null) m = getCurrent().visit((Invocation)decl);
   	  	 if(m == null) m = getCurrent().visit((MethodOccurence)decl);
		 return handleVisitReturn(m);
     }

	 @Override
     public boolean visit(final SuperConstructorInvocation alt) {
    	 eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.ConstructorInvocation decl = new eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.ConstructorInvocation(alt,context);
		 if(!handleVisitStart(decl)) return false;
    	 ProgrammMarkerVisitor m = getCurrent().visit(decl);
    	 if(m == null) m = getCurrent().visit((Invocation)decl);
   	  	 if(m == null) m = getCurrent().visit((MethodOccurence)decl);
		 return handleVisitReturn(m);
     }
     
     @Override
     public boolean visit(final EnhancedForStatement foreachStatement) {
    	 eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.ForEachStatement decl = new eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.ForEachStatement(foreachStatement,context);
		 if(!handleVisitStart(decl)) return false;
    	 ProgrammMarkerVisitor m = getCurrent().visit(decl);
    	 if(m == null) m = getCurrent().visit((LoopStatement)decl);
		 return handleVisitReturn(m);
     }

     @Override
     public boolean visit(final ForStatement forStatement) {
    	 eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.ForStatement decl = new eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.ForStatement(forStatement,context);
		 if(!handleVisitStart(decl)) return false;
    	 ProgrammMarkerVisitor m = getCurrent().visit(decl);
    	 if(m == null) m = getCurrent().visit((LoopStatement)decl);
		 return handleVisitReturn(m);
     }    
     
 	@Override
 	public boolean visit(CatchClause catchClause) {
 		 eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.CatchClause decl = new eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.CatchClause(catchClause,context);
		 if(!handleVisitStart(decl)) return false;
 		 ProgrammMarkerVisitor m = getCurrent().visit(decl);
		 return handleVisitReturn(m);
 	} 
	
	@Override
	public boolean visit(Block block) {
		 eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.Block decl = new eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.Block(block,context);
		 if(!handleVisitStart(decl)) return false;
		 ProgrammMarkerVisitor m = getCurrent().visit(decl);
		 return handleVisitReturn(m);
	}

	/* Todo: Merge with Branch
	@Override
	public void endVisit(ConditionalExpression node) {
		// TODO Auto-generated method stub
		super.endVisit(node);
	}
	*/

	

	//Note: to remember whats still open
	@Override
	public void endVisit(DoStatement node) {
		// TODO Auto-generated method stub
		super.endVisit(node);
	}

	@Override
	public void endVisit(IfStatement node) {
		// TODO Auto-generated method stub
		super.endVisit(node);
	}

	
	@Override
	public void endVisit(SwitchCase node) {
		// TODO Auto-generated method stub
		super.endVisit(node);
	}

	@Override
	public void endVisit(SwitchStatement node) {
		// TODO Auto-generated method stub
		super.endVisit(node);
	}

	@Override
	public void endVisit(TryStatement node) {
		// TODO Auto-generated method stub
		super.endVisit(node);
	}

	@Override
	public void endVisit(WhileStatement node) {
		// TODO Auto-generated method stub
		super.endVisit(node);
	}

 
     
}


