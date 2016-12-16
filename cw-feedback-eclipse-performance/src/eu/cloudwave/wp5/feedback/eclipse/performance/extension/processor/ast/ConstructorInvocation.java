package eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast;

import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;

import eu.cloudwave.wp5.feedback.eclipse.performance.core.tag.MethodLocator;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.AstContext;

public class ConstructorInvocation extends AMethodRelated<org.eclipse.jdt.core.dom.ASTNode> implements Invocation {
	
	  private static final String EMPTY = "";
	  private static final String INIT = "<init>";
	  
	  private org.eclipse.jdt.core.dom.ClassInstanceCreation newInstance = null;
	  private org.eclipse.jdt.core.dom.ConstructorInvocation thisCall = null;
	  private org.eclipse.jdt.core.dom.SuperConstructorInvocation superCall = null;


	  ConstructorInvocation(org.eclipse.jdt.core.dom.ClassInstanceCreation newInstance, AstContext ctx) {
		 super(newInstance,AMethodRelated.Type.CTR_NEW,ctx);
		 this.newInstance = newInstance;
      }
 	  
 	  ConstructorInvocation(org.eclipse.jdt.core.dom.ConstructorInvocation thisCall, AstContext ctx) {
		 super(thisCall,AMethodRelated.Type.CTR_DELEGATE,ctx);
		 this.thisCall = thisCall;
      }
 	  
 	  ConstructorInvocation(org.eclipse.jdt.core.dom.SuperConstructorInvocation superCall, AstContext ctx) {
		 super(superCall,AMethodRelated.Type.CTR_SUPER,ctx);
		 this.superCall = superCall;
      }
 	  
 	  
 	  
	  /**
	   * {@inheritDoc}
	   */
 	  //TODO: Test if still ok
	  //@Override
	  //protected int getStartPosition() {
	  //  final Expression expression = inner.getExpression();
	  //  return expression != null ? expression.getStartPosition() + expression.getLength() + 1 : inner.getType().getStartPosition();
	  //}
	
	  /**
	   * {@inheritDoc}
	   */
	  //@Override
	  //protected int getEndPosition() {
	  //  final ASTNode parentExpression = inner.getType().getParent();
	  //  return parentExpression.getStartPosition() + parentExpression.getLength();
	  //}
	
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
		IMethodBinding bind = null;
		if(newInstance != null){
			bind = newInstance.resolveConstructorBinding().getMethodDeclaration();
		} else if(thisCall != null) {
			bind = thisCall.resolveConstructorBinding().getMethodDeclaration();
		} else if(superCall != null) {
			bind = superCall.resolveConstructorBinding().getMethodDeclaration();
		}
		
		return new MethodLocator(getTargetQualifiedClassName(bind), getTargetMethodName(bind), AMethodRelated.getTargetArguments(bind));
	 }

	@Override
	public String getMethodKind() {
		return "Constructor";
	}

}
