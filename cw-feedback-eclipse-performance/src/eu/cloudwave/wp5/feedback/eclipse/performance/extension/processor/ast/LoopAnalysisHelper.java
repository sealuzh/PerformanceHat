package eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import com.google.common.base.Optional;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.AstContext;

class LoopAnalysisHelper {
	
		public static Optional<IAstNode> resolveName(IAstNode node, SimpleName name, AstContext ctx){			
			  final String variableName = name.getIdentifier();
		      // Case 1a: the variable of the for loop is a method parameter of the containing method
		      final Optional<ParameterDeclaration> param = LoopAnalysisHelper.getParameter(variableName, ctx);
		      if (param.isPresent()) return param.transform(t -> t); //needed because no variance
		      
		      // Case 1b: the variable of the for-loop is locally defined inside the method
		      //Needs attachment for working
		      //todo: just get methode invocation here
		      final Optional<IAstNode> procedure = LoopAnalysisHelper.getLocalVariableInitializer(node,variableName,ctx);
		      if (procedure.isPresent()) return procedure.transform(t -> t); //needed because no variance

		      // Case 1c: the variable is a field of the containing class
		      // TODO enhancement: Support cases where the collection is a class field (if possible).
		      // This case is tricky with SCA, because you don't know which code of the class already has been executed.
		      // Therefore one can not simply search for assignments of the class field.
		      // A solution could be to look at all the call traces that are executed when the loop is executed. This might help
		      // to find out which code is executed and then in combination with SCA to find the place where the collection is
		      // set. But this would require many calls to the Feedback handler which could make the build very slow.
		      return Optional.absent();
		}

	 	private static Optional<ParameterDeclaration> getParameter(final String variableName, AstContext ctx) {
			int counter = 0;
		    for (final Object parameter : ctx.getCurrentMethod().inner.parameters()) {
		      if (parameter instanceof SingleVariableDeclaration) { // should always be the case
		        if (((SingleVariableDeclaration) parameter).getName().toString().equals(variableName)) {
			      return Optional.of(StaticAstFactory.createParameterDeclaration(counter,(SingleVariableDeclaration) parameter,ctx));
		        }
		      }
		      counter++;
		    }
		    return Optional.absent();
		  }

		  private static Optional<IAstNode> getLocalVariableInitializer(final IAstNode cur, final String variableName, AstContext ctx) {
			Box<IAstNode> inv = new Box<>();	

			//todo: adapt to use newAst and not old one, if it somwhen support all these
			ctx.getCurrentMethod().getEclipseAstNode().getBody().accept(new ASTVisitor() {

		      @Override
		      public boolean visit(final VariableDeclarationFragment node) {
		        if (node.getStartPosition() < cur.getEclipseAstNode().getStartPosition() && node.getName().getIdentifier().equals(variableName)) {
		        	setSourceProcedure(node.getInitializer());
		        }
		        return false;
		      }

		      @Override
		      public boolean visit(final Assignment node) {
		    	if(node.getLeftHandSide() instanceof SimpleName){
		    		 if (node.getStartPosition() < cur.getEclipseAstNode().getStartPosition() && ((SimpleName)node.getLeftHandSide()).getIdentifier().equals(variableName)) {
				          setSourceProcedure(node.getRightHandSide());
				     }
		    	}
		        return false; 
		      }

		      private void setSourceProcedure(final org.eclipse.jdt.core.dom.Expression expression) {
		        if (expression != null) {
		        	inv.set(StaticAstFactory.fromEclipseAstNode(expression,ctx));
		        }
		      }
		    });
		    return inv.get();
		  }
		  
		  
		  //To circumvent javas inability to capture non-final variables in lambdas/anonymous classes
		  // other languages do this auto boxing automatically
		  private static class Box<T> {
		    private T value = null;

		    public void set(final T value) {
		      this.value = value;
		    }

		    public Optional<T> get() {
		      return Optional.fromNullable(value);
		    }
		  }

}
