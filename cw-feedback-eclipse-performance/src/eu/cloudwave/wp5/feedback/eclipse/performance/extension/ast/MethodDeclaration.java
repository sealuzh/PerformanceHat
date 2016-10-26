package eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast;

import java.util.Collections;
import java.util.List;

import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;

import com.google.common.collect.Lists;

import eu.cloudwave.wp5.common.model.Procedure;
import eu.cloudwave.wp5.common.model.ProcedureKind;
import eu.cloudwave.wp5.common.model.impl.ProcedureImpl;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ProgrammMarkerContext;

public class MethodDeclaration extends AMethodRelated<org.eclipse.jdt.core.dom.MethodDeclaration> implements MethodOccurence {
	
	public MethodDeclaration(org.eclipse.jdt.core.dom.MethodDeclaration methodDeclaration, ProgrammMarkerContext ctx) {
		super(methodDeclaration,ctx);
	}
	
	 @Override
	public MethodDeclaration getCurrentMethode() {
		return this;
	}
	 
	private List<Double> metrics = null;

	@Override
	public List<Double> getDoubleTags(String name) {
		if(name.equals("CollectionSize")){
			if (metrics == null) {
				 Procedure procedure = createCorrelatingProcedure();
				 String[] arguments = procedure.getArguments().toArray(new String[procedure.getArguments().size()]);
			     Double averageSize = ctx.getFeedBackClient().collectionSize(ctx.getProject(), procedure.getClassName(), procedure.getName(), arguments, "");
			     metrics = Collections.singletonList(averageSize);
	        }
	        return metrics;
		} else {
			return super.getDoubleTags(name);
		}
	}

	/**
	   * Returns the corresponding {@link IMethodBinding} of the wrapped object.
	   * 
	   * @return the corresponding {@link IMethodBinding} of the wrapped object.
	   */
	  public IMethodBinding getMethodBinding(){
		 return inner.resolveBinding().getMethodDeclaration();
	  }

	  
	  /**
	   * Returns the name of the method.
	   * 
	   * @return the name of the method
	   */
	  public String getMethodName() {
	    return getMethodBinding().getName();
	  }
	  
	  /**
	   * Returns the qualified name of the declaring class of the method.
	   * 
	   * @return the qualified name of the declaring class of the method
	   */
	  public String getQualifiedClassName() {
	    return getMethodBinding().getDeclaringClass().getQualifiedName();
	  }
	  
	  /**
	   * Returns an array containing the qualified names of the types of the given arguments.
	   * 
	   * @param methodBinding
	   *          the {@link IMethodBinding}
	   * @return an array containing the qualified names of the types of the given arguments
	   */
	  public String[] getArguments() {
	    final ITypeBinding[] argumentTypes = getMethodBinding().getParameterTypes();
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
	  
	
	  public Procedure createCorrelatingProcedure() {
	    final List<String> arguments = Lists.newArrayList(getArguments());
	    //todo: pass in correct, but now this also is always UNKNOWN so at least ok
	    return new ProcedureImpl(getQualifiedClassName(), getMethodName(), ProcedureKind.UNKNOWN, arguments, Lists.newArrayList());
	  }

}
