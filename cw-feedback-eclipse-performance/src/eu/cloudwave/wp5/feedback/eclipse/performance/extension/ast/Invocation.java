package eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast;

import java.util.List;

import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;

import com.google.common.collect.Lists;

import eu.cloudwave.wp5.common.model.Procedure;
import eu.cloudwave.wp5.common.model.ProcedureKind;
import eu.cloudwave.wp5.common.model.impl.ProcedureImpl;


public interface Invocation extends IAstNode {
	
	  /**
	   * Returns the corresponding {@link IMethodBinding} of the wrapped object.
	   * 
	   * @return the corresponding {@link IMethodBinding} of the wrapped object.
	   */
	  public IMethodBinding getTargetMethodBinding();

	  
	  /**
	   * Returns the name of the method.
	   * 
	   * @return the name of the method
	   */
	  default public String getTargetMethodName() {
	    return getTargetMethodBinding().getName();
	  }
	  
	  /**
	   * Returns the qualified name of the declaring class of the method.
	   * 
	   * @return the qualified name of the declaring class of the method
	   */
	  default public String getTargetQualifiedClassName() {
	    return getTargetMethodBinding().getDeclaringClass().getQualifiedName();
	  }
	  
	  /**
	   * Returns an array containing the qualified names of the types of the given arguments.
	   * 
	   * @param methodBinding
	   *          the {@link IMethodBinding}
	   * @return an array containing the qualified names of the types of the given arguments
	   */
	  default public String[] getTargetArguments() {
	    final ITypeBinding[] argumentTypes = getTargetMethodBinding().getParameterTypes();
	    final String[] argumentNames = new String[argumentTypes.length];
	    for (int i = 0; i < argumentTypes.length; i++) {
	      final String argument = argumentTypes[i].getQualifiedName();
		    final int genericBeginIndex = argument.indexOf("<");
		    if (genericBeginIndex != -1) {
		    	argumentNames[i] = argument.substring(0, genericBeginIndex);
		    } else {
		    	argumentNames[i] = argument;
		    }
	    }
	    return argumentNames;
	  }
	  
	  public ProcedureKind getProcedureKind();
	  
	  default public Procedure createCorrelatingProcedure(){
		  final List<String> arguments = Lists.newArrayList(getTargetArguments());
		  return new ProcedureImpl(getTargetQualifiedClassName(), getTargetMethodName(), getProcedureKind(), arguments, Lists.newArrayList());
	  }
}
