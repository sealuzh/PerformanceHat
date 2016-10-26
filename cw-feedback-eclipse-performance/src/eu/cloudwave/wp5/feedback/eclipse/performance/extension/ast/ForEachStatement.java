package eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.VariableDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import com.google.common.base.Optional;

import eu.cloudwave.wp5.common.model.Procedure;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.ast.extensions.MethodInvocationExtension;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ProgrammMarkerContext;

public class ForEachStatement extends AAstNode<EnhancedForStatement> implements LoopStatement{
		
	public ForEachStatement(EnhancedForStatement foreachStatement,ProgrammMarkerContext ctx) {
		super(foreachStatement,ctx);
	}

	//Temporary until real Tag Collector is implemented
	@Override
	protected boolean corrolatesWith(Procedure procedure) {
		return false;
	}

	@Override
	protected int getStartPosition() {
		return inner.getParameter().getStartPosition();
	}

	@Override
	protected int getEndPosition() {
		return  inner.getParameter().getStartPosition();
	}

	/**
	   * Determines the source of the collection that is iterated in the given foreach-loop. The source is either a
	   * parameter or a return value of a procedure (method or constructor). This information is required to fetch
	   * information about the average size of the collection from the feedback handler.
	   * @return
	   */
	  //todo: do rewire CollectionSource to Something that can have tags, like a param or a variable/Vlaue and then use tag mechanism
	  public Optional<CollectionSource> getSourceCollection() {
	    final Expression expression = inner.getExpression();

	    // Case 1: the for loop contains a variable; 'for(Object item : items)'
	    if (expression instanceof SimpleName) {
	      final String variableName = expression.toString();
	      // Case 1a: the variable of the for loop is a method parameter of the containing method
	      final Optional<Integer> position = getParameterPosition(variableName);
	      if (position.isPresent()) {
	    	//Needs attachment for working
	    	//todo: return Optional.of(new Wrapper( (VariableDeclaration) (getCurrentMethode().inner.parameters()).get(position.get())))
	        return Optional.of(new CollectionSource(getCurrentMethode().createCorrelatingProcedure(), position.get()));
	      }
	      // Case 1b: the variable of the for-loop is locally defined inside the method
	      //Needs attachment for working
	      //todo: just get methode invocation here
	      final Optional<Procedure> procedure = getLocalVariableProcedureAssignment(variableName);
	      if (procedure.isPresent()) {
	        return Optional.of(new CollectionSource(procedure.get()));
	      }
	      // Case 1c: the variable is a field of the containing class
	      // TODO enhancement: Support cases where the collection is a class field (if possible).
	      // This case is tricky with SCA, because you don't know which code of the class already has been executed.
	      // Therefore one can not simply search for assignments of the class field.
	      // A solution could be to look at all the call traces that are executed when the loop is executed. This might help
	      // to find out which code is executed and then in combination with SCA to find the place where the collection is
	      // set. But this would require many calls to the Feedback handler which could make the build very slow.
	    }
	    // Case 2: the for loop contains a method invocation; example: 'for(Object item : getItems())'
	    else if (expression instanceof MethodInvocation) {
	      //Needs attachment for working
	      //todo: just get the methode invocation here	
	      final MethodInvocationExtension methodInvocationExtension = new MethodInvocationExtension((MethodInvocation) expression);
	      return Optional.of(new CollectionSource(methodInvocationExtension.createCorrelatingProcedure()));
	    }
	    return Optional.absent();
	  }

	  /**
	   * Checks if the given {@link SimpleName} is a parameter of the given {@link MethodDeclaration}. If this is the case,
	   * the position of the parameter (in the method signature) is returned.
	   * 
	   * @param simpleName
	   *          the {@link SimpleName}
	   * @param methodDeclaration
	   *          the {@link MethodDeclaration}
	   * @return the position of the parameter (in the method signature) if the {@link SimpleName} is a parameter of the
	   *         given {@link MethodDeclaration}, {@link Optional#absent()} otherwise
	   */
	  private Optional<Integer> getParameterPosition(final String variableName) {
	    int counter = 0;
	    for (final Object parameter : getCurrentMethode().inner.parameters()) {
	      if (parameter instanceof VariableDeclaration) { // should always be the case
	        if (((VariableDeclaration) parameter).getName().toString().equals(variableName)) {
	          return Optional.of(counter);
	        }
	      }
	      counter++;
	    }
	    return Optional.absent();
	  }

	  private Optional<Procedure> getLocalVariableProcedureAssignment(final String variableName) {
	    final ProcedureWrapper procedureBucket = new ProcedureWrapper();

		//todo: adapt to use newAst and not old one
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

	      private void setSourceProcedure(final Expression expression) {
	        if (expression != null && expression instanceof MethodInvocation) {
	          final MethodInvocation methodInvocation = ((MethodInvocation) expression);
	          procedureBucket.set(new MethodInvocationExtension(methodInvocation).createCorrelatingProcedure());
	        }
	      }
	    });

	    return procedureBucket.get();
	  }

	 

	  /**
	   * A wrapper to store a {@link Procedure}. It is used to set the procedure from within an inner class. The reason is
	   * that inner classes can only access final-fields. Therefor a field of type {@link Procedure} cannot directly be set.
	   */
	  private static class ProcedureWrapper {
	    private Procedure procedure;

	    public ProcedureWrapper() {
	      this.procedure = null;
	    }

	    public void set(final Procedure procedure) {
	      this.procedure = procedure;
	    }

	    public Optional<Procedure> get() {
	      if (procedure != null) {
	        return Optional.of(procedure);
	      }
	      return Optional.absent();
	    }
	  }

}
