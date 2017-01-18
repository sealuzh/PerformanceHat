package eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;

import com.google.common.collect.Lists;

import eu.cloudwave.wp5.feedback.eclipse.performance.core.tag.MethodLocator;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.AstContext;

/**
 * A IAstNode representing the invocation of a constructor inluding the generation of a new instance.
 * Including: this(...) calls, super(...) calls and new ...(...) callse
 * @author Markus Knecht
 *
 */
public class ConstructorInvocation extends AMethodRelated<org.eclipse.jdt.core.dom.ASTNode> implements Invocation {
	
	  private static final String EMPTY = "";
	  private static final String INIT = "<init>";
	  
	  //all the possible backing eclipse ASTNode's
	  private org.eclipse.jdt.core.dom.ClassInstanceCreation newInstance = null;
	  private org.eclipse.jdt.core.dom.ConstructorInvocation thisCall = null;
	  private org.eclipse.jdt.core.dom.SuperConstructorInvocation superCall = null;

	  //Lazy calced SubNodes
	  private List<IAstNode> arguments = null;
	  
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
	
 	  //Helper to find the class name for the Method Location
 	  private static String getTargetQualifiedClassName(IMethodBinding bind) {
 		  final String regularName = bind.getDeclaringClass().getQualifiedName();
 		  // if the name is empty, this is the constructor of anonymous inner class
 		  // Therefore, no name exists and the name of the implementing interface or the extending class has to be considered.
 		  if (regularName.equals(EMPTY)) {
 			  final ITypeBinding[] interfaces = bind.getDeclaringClass().getInterfaces();
 			  if (interfaces.length > 0) {
 				  return interfaces[0].getQualifiedName();
 			  }
 			  return bind.getDeclaringClass().getSuperclass().getQualifiedName();
 		  }
 		  return regularName;
 	  }
 	  
 	  //Helper to find the Method Name
 	  private static String getTargetMethodName(IMethodBinding bind) {
 		  final String regularName = bind.getName();
 		  if (regularName.equals(EMPTY)) {
 			  return INIT;
 		  }
 		  return regularName;
 	  }

 	 /**
 		 * {@inheritDoc}
 		 */
 		public List<IAstNode> getArguments(){
 			if(arguments == null){
 				arguments = Lists.newArrayList();
 				List<ASTNode> args = Lists.newArrayList(); 
 				if(newInstance != null){
 					args = newInstance.arguments();
 				} else if(superCall != null) {
 					args = superCall.arguments();
 				}else if(thisCall != null) {
 					args = thisCall.arguments();
 				}
 				
 				for(ASTNode node:args){
 					arguments.add(StaticAstFactory.fromEclipseAstNodeOrDefault(node, ctx));
 				}
 			}
 			return arguments;
 		}
 	  
 	  /**
 	   * {@inheritDoc}
 	   */ 
 	  @Override
 	  protected IMethodBinding getBinding() {
		  if(newInstance != null){
			  return newInstance.resolveConstructorBinding().getMethodDeclaration();
		  } else if(thisCall != null) {
			  return thisCall.resolveConstructorBinding().getMethodDeclaration();
		  } else if(superCall != null) {
			  return superCall.resolveConstructorBinding().getMethodDeclaration();
		  }
		  return null;
 	  }


 	  @Override
 	  public  MethodLocator createCorrespondingMethodLocation(){
 		 IMethodBinding bind = getBinding();
 		 return new MethodLocator(getTargetQualifiedClassName(bind), getTargetMethodName(bind), AMethodRelated.getTargetArguments(bind));	
 	  }

 	  /**
 	   * {@inheritDoc}
 	   */
 	  @Override
 	  public String getMethodKind() {
 		  return "Constructor";
 	  }

}
