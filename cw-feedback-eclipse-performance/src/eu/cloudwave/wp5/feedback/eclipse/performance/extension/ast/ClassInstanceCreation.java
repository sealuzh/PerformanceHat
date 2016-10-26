package eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast;

import java.util.Arrays;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;

import eu.cloudwave.wp5.common.model.Procedure;
import eu.cloudwave.wp5.common.model.ProcedureKind;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ProgrammMarkerContext;

public class ClassInstanceCreation extends AAstNode<org.eclipse.jdt.core.dom.ClassInstanceCreation> implements Invocation {
	
	  private static final String EMPTY = "";
	  private static final String INIT = "<init>";

 	  public ClassInstanceCreation(org.eclipse.jdt.core.dom.ClassInstanceCreation newInstance, ProgrammMarkerContext ctx) {
		super(newInstance,ctx);
      }

	  @Override
	  public String getTargetQualifiedClassName() {
	    final String regularName = getTargetMethodBinding().getDeclaringClass().getQualifiedName();
	    // if the name is empty, this is the constructor of anonymous inner class
	    // Therefore, no name exists and the name of the implementing interface or the extending class ha to be considered.
	    if (regularName.equals(EMPTY)) {
	      final ITypeBinding[] interfaces = getTargetMethodBinding().getDeclaringClass().getInterfaces();
	      if (interfaces.length > 0) {
	        return interfaces[0].getQualifiedName();
	      }
	      return getTargetMethodBinding().getDeclaringClass().getSuperclass().getQualifiedName();
	    }
	    return regularName;
	  }

	  @Override
	  public String getTargetMethodName() {
	    final String regularName = getTargetMethodBinding().getName();
	    if (regularName.equals(EMPTY)) {
	      return INIT;
	    }
	    return regularName;
	  }

	  /**
	   * {@inheritDoc}
	   */
	  @Override
	  public IMethodBinding getTargetMethodBinding() {
	    return inner.resolveConstructorBinding().getMethodDeclaration();
	  }

	  /**
	   * {@inheritDoc}
	   */
	  @Override
	  protected int getStartPosition() {
	    final Expression expression = inner.getExpression();
	    return expression != null ? expression.getStartPosition() + expression.getLength() + 1 : inner.getType().getStartPosition();
	  }
	
	  /**
	   * {@inheritDoc}
	   */
	  @Override
	  protected int getEndPosition() {
	    final ASTNode parentExpression = inner.getType().getParent();
	    return parentExpression.getStartPosition() + parentExpression.getLength();
	  }

	@Override
	protected boolean corrolatesWith(Procedure procedure) {
		  boolean doesClassCorroalte = getTargetQualifiedClassName().equals(procedure.getClassName());
		  boolean doesMethodCorroalte = getTargetMethodName().equals(procedure.getName());
		  boolean doArgsCorroalte = Arrays.equals(getTargetArguments(), procedure.getArguments().toArray());
		  return doesClassCorroalte & doesMethodCorroalte & doArgsCorroalte;
	}
	
	@Override
	public ProcedureKind getProcedureKind() {
		return ProcedureKind.CONSTRUCTOR;
	}
	

}
