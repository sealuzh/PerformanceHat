package eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast;

import java.util.Arrays;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.IMethodBinding;

import eu.cloudwave.wp5.common.model.Procedure;
import eu.cloudwave.wp5.common.model.ProcedureKind;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ProgrammMarkerContext;

public class MethodInvocation extends AAstNode<org.eclipse.jdt.core.dom.MethodInvocation> implements Invocation, MethodOccurence {

	public MethodInvocation(org.eclipse.jdt.core.dom.MethodInvocation methodInvocation, ProgrammMarkerContext ctx) {
		super(methodInvocation,ctx);
	}

	  /**
	   * {@inheritDoc}
	   */
	  @Override
	  public int getStartPosition() {
	    final Expression expression = inner.getExpression();
	    return expression != null ? expression.getStartPosition() + expression.getLength() + 1 : inner.getName().getStartPosition();
	  }

	  /**
	   * {@inheritDoc}
	   */
	  @Override
	  public int getEndPosition() {
	    final ASTNode parentExpression = inner.getName().getParent();
	    return parentExpression.getStartPosition() + parentExpression.getLength();
	  }

	  /**
	   * {@inheritDoc}
	   */
	  @Override
	  public IMethodBinding getTargetMethodBinding() {
	    return inner.resolveMethodBinding().getMethodDeclaration();
	  }
	  
	  @Override
		public boolean corrolatesWith(Procedure procedure) {
			  boolean doesClassCorroalte = getTargetQualifiedClassName().equals(procedure.getClassName());
			  boolean doesMethodCorroalte = getTargetMethodName().equals(procedure.getName());
			  boolean doArgsCorroalte = Arrays.equals(getTargetArguments(), procedure.getArguments().toArray());
			  return doesClassCorroalte & doesMethodCorroalte & doArgsCorroalte;
		}

	@Override
	public ProcedureKind getProcedureKind() {
		return ProcedureKind.METHOD;
	}
	
	

}
