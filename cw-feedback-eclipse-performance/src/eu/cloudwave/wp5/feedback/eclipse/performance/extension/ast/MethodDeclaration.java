package eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast;

import org.eclipse.jdt.core.dom.IMethodBinding;

import eu.cloudwave.wp5.feedback.eclipse.performance.core.tag.MethodLocator;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ProgrammMarkerContext;

public class MethodDeclaration extends AMethodRelated<org.eclipse.jdt.core.dom.MethodDeclaration> implements MethodOccurence {
	
	 MethodDeclaration(org.eclipse.jdt.core.dom.MethodDeclaration methodDeclaration, ProgrammMarkerContext ctx) {
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

	@Override
	public String getMethodKind() {
		IMethodBinding bind = inner.resolveBinding().getMethodDeclaration();
		return bind.isConstructor()?"Constructor":"Method";
		//return "Method";
	}

	@Override
	public void attachTag(String tagName, Object tagValue) {
		ctx.getTagCreator().addMethodTag(createCorrespondingMethodLocation(), tagName, tagValue);
		super.attachTag(tagName, tagValue);
	}
	
	

}
