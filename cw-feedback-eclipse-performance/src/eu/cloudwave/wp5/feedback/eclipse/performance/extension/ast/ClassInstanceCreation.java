package eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;

import eu.cloudwave.wp5.feedback.eclipse.performance.core.tag.MethodLocator;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ProgrammMarkerContext;

public class ClassInstanceCreation extends AMethodRelated<org.eclipse.jdt.core.dom.ClassInstanceCreation> implements Invocation {
	
	  private static final String EMPTY = "";
	  private static final String INIT = "<init>";

 	  public ClassInstanceCreation(org.eclipse.jdt.core.dom.ClassInstanceCreation newInstance, ProgrammMarkerContext ctx) {
		super(newInstance,ctx);
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
	
	 private static String getTargetQualifiedClassName(IMethodBinding bind) {
		    final String regularName = bind.getDeclaringClass().getQualifiedName();
		    // if the name is empty, this is the constructor of anonymous inner class
		    // Therefore, no name exists and the name of the implementing interface or the extending class ha to be considered.
		    if (regularName.equals(EMPTY)) {
		      final ITypeBinding[] interfaces = bind.getDeclaringClass().getInterfaces();
		      if (interfaces.length > 0) {
		        return interfaces[0].getQualifiedName();
		      }
		      return bind.getDeclaringClass().getSuperclass().getQualifiedName();
		    }
		    return regularName;
		  }

		  private static String getTargetMethodName(IMethodBinding bind) {
		    final String regularName = bind.getName();
		    if (regularName.equals(EMPTY)) {
		      return INIT;
		    }
		    return regularName;
		  }

	public  MethodLocator createCorrespondingMethodLocation(){
		IMethodBinding bind = inner.resolveConstructorBinding().getMethodDeclaration();
		return new MethodLocator(getTargetQualifiedClassName(bind), getTargetMethodName(bind), AMethodRelated.getTargetArguments(bind));
	 }

}
