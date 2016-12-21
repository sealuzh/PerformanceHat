package eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast;

import java.util.Collection;

import org.eclipse.jdt.core.dom.IMethodBinding;

import eu.cloudwave.wp5.feedback.eclipse.performance.core.tag.MethodLocator;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.AstContext;

/**
 * A IAstNode representing a Method Declaration
 * @author Markus Knecht
 */
public class MethodDeclaration extends AMethodRelated<org.eclipse.jdt.core.dom.MethodDeclaration> implements MethodOccurence {
	
	 MethodDeclaration(org.eclipse.jdt.core.dom.MethodDeclaration methodDeclaration, AstContext ctx) {
		super(methodDeclaration, AMethodRelated.Type.DECLARATION,ctx);
	 }

	  /**
	   * {@inheritDoc}
	   */
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
	  
	  
 	  /**
 	   * {@inheritDoc}
 	   */ 
 	  @Override
 	  protected IMethodBinding getBinding() {
		  return inner.resolveBinding().getMethodDeclaration();
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
	   * In addition to tags bound by its node, MethodeDeclarations allow to attach a Tag to the actual method and not the node
	   *  This tag is avaiable from other CompilationUnits and can be accessed as usual over get Tag
	   *  It is even present on Method calls to the method
	   * @param name of the tag
	   * @param value of the tag
	   */
	  public void attachPublicTag(String name, Object value) {
		  MethodLocator loc = createCorrespondingMethodLocation();
		  ctx.getTagCreator().addMethodTag(loc, name, value);
	  }
	  
	  
	  /**
	   * {@inheritDoc}
	   */
	  @Override
	  public String getMethodKind() {
		  IMethodBinding bind = inner.resolveBinding().getMethodDeclaration();
		  return bind.isConstructor()?"Constructor":"Method";
	  }
	  
	

	/**
	   * Returns the Body node of the Method or null if their is none
	   * @return the body as IAstNode
	   */
	  public Block getBody(){
		  if(inner.getBody() ==  null) return null;
		  return StaticAstFactory.createBlock(inner.getBody(), ctx);
	  }

}
