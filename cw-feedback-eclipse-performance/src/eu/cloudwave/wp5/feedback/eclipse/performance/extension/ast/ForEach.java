package eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast;

import java.util.Collections;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import com.google.common.base.Optional;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ProgrammMarkerContext;

public class ForEach extends AAstNode<EnhancedForStatement> implements Loop{
		
	public ForEach(EnhancedForStatement foreachStatement,ProgrammMarkerContext ctx) {
		super(foreachStatement,ctx);
	}

	@Override
	protected int getStartPosition() {
		return inner.getParameter().getStartPosition();
	}

	@Override
	protected int getEndPosition() {
		  final org.eclipse.jdt.core.dom.Expression expression = inner.getExpression();
          return expression.getStartPosition() + expression.getLength();
	}

	@Override
	public Optional<Integer> getIterations() {
		return Optional.absent();
	}

	/**
	   * Determines the source of the collection that is iterated in the given foreach-loop. The source is either a
	   * parameter or a return value of a procedure (method or constructor). This information is required to fetch
	   * information about the average size of the collection from the feedback handler.
	   * @return
	   */
	  //todo: do rewire CollectionSource to Something that can have tags, like a param or a variable/Vlaue and then use tag mechanism
	  public Optional<IAstNode> getSourceNode() {
	    final org.eclipse.jdt.core.dom.Expression expression = inner.getExpression();

	    // Case 1: the for loop contains a variable; 'for(Object item : items)'
	    if (expression instanceof SimpleName) {
	      final String variableName = expression.toString();
	      // Case 1a: the variable of the for loop is a method parameter of the containing method
	      final Optional<ParameterDeclaration> param = getParameter(variableName);
	      if (param.isPresent()) return param.transform(t -> t); //needed because no variance
	      
	      // Case 1b: the variable of the for-loop is locally defined inside the method
	      //Needs attachment for working
	      //todo: just get methode invocation here
	      final Optional<MethodInvocation> procedure = getLocalVariableInitializer(variableName);
	      if (procedure.isPresent()) return param.transform(t -> t); //needed because no variance

	      // Case 1c: the variable is a field of the containing class
	      // TODO enhancement: Support cases where the collection is a class field (if possible).
	      // This case is tricky with SCA, because you don't know which code of the class already has been executed.
	      // Therefore one can not simply search for assignments of the class field.
	      // A solution could be to look at all the call traces that are executed when the loop is executed. This might help
	      // to find out which code is executed and then in combination with SCA to find the place where the collection is
	      // set. But this would require many calls to the Feedback handler which could make the build very slow.
	    }
	    // Case 2: the for loop contains a method invocation; example: 'for(Object item : getItems())'
	    else if (expression instanceof org.eclipse.jdt.core.dom.MethodInvocation) {
	    	return Optional.of(new eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.MethodInvocation((org.eclipse.jdt.core.dom.MethodInvocation)expression,ctx));

	    }
	    return Optional.absent();
	  }
	  


	  /**
	   * Checks if the given {@link SimpleName} is a parameter of the given {@link MethodDeclaration}. If this is the case,
	   * the parameter is returned.
	   * 
	   * @param simpleName
	   *          the {@link SimpleName}
	   * @param methodDeclaration
	   *          the {@link MethodDeclaration}
	   * @return the parameter if the {@link SimpleName} is a parameter of the
	   *         given {@link MethodDeclaration}, {@link Optional#absent()} otherwise
	   */
	  private Optional<ParameterDeclaration> getParameter(final String variableName) {
		int counter = 0;
	    for (final Object parameter : getCurrentMethode().inner.parameters()) {
	      if (parameter instanceof SingleVariableDeclaration) { // should always be the case
	        if (((SingleVariableDeclaration) parameter).getName().toString().equals(variableName)) {
		      return Optional.of(new eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.ParameterDeclaration(counter,getCurrentMethode(),(SingleVariableDeclaration) parameter,ctx));
	        }
	      }
	      counter++;
	    }
	    return Optional.absent();
	  }

	  private Optional<MethodInvocation> getLocalVariableInitializer(final String variableName) {
		Box<MethodInvocation> inv = new Box<>();	    
		//todo: adapt to use newAst and not old one, if it somwhen support all these
	    getCurrentMethode().inner.getBody().accept(new ASTVisitor() {

	      @Override
	      public boolean visit(final VariableDeclarationFragment node) {
	        if (node.getStartPosition() < inner.getStartPosition() && node.getName().toString().equals(variableName)) {
	          setSourceProcedure(node.getInitializer());
	        }
	        return false;
	      }

	      @Override
	      public boolean visit(final Assignment node) {
	        if (node.getStartPosition() < inner.getStartPosition() && node.getLeftHandSide().toString().equals(variableName)) {
	          setSourceProcedure(node.getRightHandSide());
	        }
	        return false;
	      }

	      private void setSourceProcedure(final org.eclipse.jdt.core.dom.Expression expression) {
	        if (expression != null && expression instanceof org.eclipse.jdt.core.dom.MethodInvocation) {
	        	inv.set(new MethodInvocation((org.eclipse.jdt.core.dom.MethodInvocation) expression,ctx));
	        }
	      }
	    });

	    return inv.get();
	  }

	 
	  private static class Box<T> {
	    private T value = null;

	    public void set(final T value) {
	      this.value = value;
	    }

	    public Optional<T> get() {
	      return Optional.fromNullable(value);
	    }
	  }


	@Override
	public List<IAstNode> getInitNodes() {
		return Collections.singletonList(IAstNode.fromEclipseAstNode(inner.getExpression(),ctx));
	}

	@Override
	public IAstNode getBody() {
		return IAstNode.fromEclipseAstNode(inner.getBody(), ctx);
	}

	  
}
