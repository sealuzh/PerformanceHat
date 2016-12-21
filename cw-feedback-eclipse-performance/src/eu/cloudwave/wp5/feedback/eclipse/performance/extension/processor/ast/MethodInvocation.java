package eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast;

import org.eclipse.jdt.core.dom.IMethodBinding;

import eu.cloudwave.wp5.feedback.eclipse.performance.core.tag.MethodLocator;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.AstContext;

/**
 * A IAstNode representing a Method call including super calls
 * @author Markus
 *
 */
public class MethodInvocation extends AMethodRelated<org.eclipse.jdt.core.dom.Expression> implements Invocation {
	
	//the different backing nodes
	private org.eclipse.jdt.core.dom.MethodInvocation directCall = null;
	private org.eclipse.jdt.core.dom.SuperMethodInvocation superCall = null;
	
	MethodInvocation(org.eclipse.jdt.core.dom.MethodInvocation methodInvocation, AstContext ctx) {
		super(methodInvocation,AMethodRelated.Type.METHOD,ctx);
		directCall = methodInvocation;
	}
	
	MethodInvocation(org.eclipse.jdt.core.dom.SuperMethodInvocation methodInvocation, AstContext ctx) {
		super(methodInvocation,AMethodRelated.Type.METHOD_SUPER,ctx);
		superCall = methodInvocation;
	}
	
	  
	/**
	 * {@inheritDoc}
	 */ 
	@Override
	protected IMethodBinding getBinding() {
		if(directCall != null){
			return directCall.resolveMethodBinding().getMethodDeclaration();
		} else if(superCall != null) {
			return superCall.resolveMethodBinding().getMethodDeclaration();
		}
		return null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public MethodLocator createCorrespondingMethodLocation(){
		IMethodBinding bind = getBinding();
		return new MethodLocator(bind.getDeclaringClass().getQualifiedName(), bind.getName(), AMethodRelated.getTargetArguments(bind));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getMethodKind() {
		return "Method";
	}
	
}