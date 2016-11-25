package eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast;

import org.eclipse.jdt.core.dom.IMethodBinding;

import eu.cloudwave.wp5.feedback.eclipse.performance.core.tag.MethodLocator;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ProgrammMarkerContext;

public class MethodInvocation extends AMethodRelated<org.eclipse.jdt.core.dom.Expression> implements Invocation {

	
	private org.eclipse.jdt.core.dom.MethodInvocation directCall = null;
	private org.eclipse.jdt.core.dom.SuperMethodInvocation superCall = null;
	
	MethodInvocation(org.eclipse.jdt.core.dom.MethodInvocation methodInvocation, ProgrammMarkerContext ctx) {
		super(methodInvocation,AMethodRelated.CallType.METHOD,ctx);
		directCall = methodInvocation;
	}
	
	MethodInvocation(org.eclipse.jdt.core.dom.SuperMethodInvocation methodInvocation, ProgrammMarkerContext ctx) {
		super(methodInvocation,AMethodRelated.CallType.METHOD_SUPER,ctx);
		superCall = methodInvocation;
	}

	  /**
	   * {@inheritDoc}
	   */
	  //TODO: Test if still ok
	  //@Override
	  //public int getStartPosition() {
	  //  final Expression expression = inner.getExpression();
	  //  return expression != null ? expression.getStartPosition() + expression.getLength() + 1 : inner.getName().getStartPosition();
	  //}

	  /**
	   * {@inheritDoc}
	   */
	  //@Override
	  //public int getEndPosition() {
	  //  final ASTNode parentExpression = inner.getName().getParent();
	  //  return parentExpression.getStartPosition() + parentExpression.getLength();
	  //}

	
	//sadly we hae to repeat because defualts cant implement interface other then own
	 public MethodLocator createCorrespondingMethodLocation(){
		 IMethodBinding bind = null;
			if(directCall != null){
				bind = directCall.resolveMethodBinding().getMethodDeclaration();
			} else if(superCall != null) {
				bind = superCall.resolveMethodBinding().getMethodDeclaration();
			}
		return new MethodLocator(bind.getDeclaringClass().getQualifiedName(), bind.getName(), AMethodRelated.getTargetArguments(bind));
	  }

	@Override
	public String getMethodKind() {
		return "Method";
	}
	
	
	 
}
