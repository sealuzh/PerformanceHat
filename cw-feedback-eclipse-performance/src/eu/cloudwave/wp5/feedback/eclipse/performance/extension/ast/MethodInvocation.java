package eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.IMethodBinding;

import eu.cloudwave.wp5.feedback.eclipse.performance.core.tag.MethodLocator;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ProgrammMarkerContext;

public class MethodInvocation extends AMethodRelated<org.eclipse.jdt.core.dom.MethodInvocation> implements Invocation, MethodOccurence {

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

	
	//sadly we hae to repeat because defualts cant implement interface other then own
	 public MethodLocator createCorrespondingMethodLocation(){
		IMethodBinding bind = inner.resolveMethodBinding().getMethodDeclaration();
		return new MethodLocator(bind.getDeclaringClass().getQualifiedName(), bind.getName(), AMethodRelated.getTargetArguments(bind));
	  }
	
	 @Override
		public String getMethodKind() {
			return "Method";
		}
	 
}
