package eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast;

import org.eclipse.jdt.core.dom.IMethodBinding;

import eu.cloudwave.wp5.feedback.eclipse.performance.core.tag.MethodLocator;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.AstContext;

public class MethodDeclaration extends AMethodRelated<org.eclipse.jdt.core.dom.MethodDeclaration> implements MethodOccurence {
	
	 MethodDeclaration(org.eclipse.jdt.core.dom.MethodDeclaration methodDeclaration, AstContext ctx) {
		super(methodDeclaration, AMethodRelated.CallType.DECLARATION,ctx);
	 }
		
	 @Override
	 public MethodDeclaration getCurrentMethode() {
			return this;
	 }
	  
	  
	  /**
	   * {@inheritDoc}
	   */
	  @Override
	  public int getStartPosition() {
	    return inner.getName().getStartPosition();
	  }

	  /**
	   * {@inheritDoc}
	   */
	  @Override
	  public int getEndPosition() {
	    return getStartPosition() + inner.getName().getLength();
	  }
	  
	
	  public MethodLocator createCorrespondingMethodLocation(){
		  IMethodBinding bind = inner.resolveBinding().getMethodDeclaration();
		  return new MethodLocator(bind.getDeclaringClass().getQualifiedName(), bind.getName(), AMethodRelated.getTargetArguments(bind));
	  }
	  
	  public void attachPublicTag(String name, Object value) {
		  MethodLocator loc = createCorrespondingMethodLocation();
		  ctx.getTagCreator().addMethodTag(loc, name, value);
	  }

	  @Override
	  public String getMethodKind() {
		  IMethodBinding bind = inner.resolveBinding().getMethodDeclaration();
		  return bind.isConstructor()?"Constructor":"Method";
	  }

}
