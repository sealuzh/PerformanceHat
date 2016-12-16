package eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast;

import java.util.Collection;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;

import com.google.common.collect.Lists;

import eu.cloudwave.wp5.feedback.eclipse.performance.core.tag.MethodLocator;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.AstContext;

/**
 * An abstract superclass that encapsulates common logic for all nodes that involve a method
 * @author Markus Knecht
 * @param <T> is the concrete backing node
 */
public abstract class AMethodRelated<T extends ASTNode> extends AAstNode<T> {
	/**
	 * Fetches the information about the related method
	 * @return the corresponding MethodLocator
	 */
	public abstract MethodLocator createCorrespondingMethodLocation();

	/**
	 * The avaiable method relation types
	 * @author Markus Knecht
	 */
	public enum Type{
	  CTR_NEW, METHOD, CTR_DELEGATE, CTR_SUPER, METHOD_SUPER, DECLARATION
	}

	/**
	 * The kind of Method related node this represents
	 */
	public final Type callType; 
	
	AMethodRelated(T inner,Type callType, AstContext ctx) {
		super(inner, ctx);
		this.callType = callType;
	}
		
	/**
	   * Returns an array containing the qualified names of the types of the given arguments.
	   * 
	   * @param methodBinding
	   *          the {@link IMethodBinding}
	   * @return an array containing the qualified names of the types of the given arguments
	   */
	  static public String[] getTargetArguments(IMethodBinding binding) {
	    final ITypeBinding[] argumentTypes = binding.getParameterTypes();
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
	  public Collection<Object> getTags(String name) {
		  MethodLocator loc = createCorrespondingMethodLocation();
		  List<Object> res = Lists.newArrayList();
		  //beside the node attached tags find the public method attached tags
		  res.addAll(ctx.getTagProvider().getTagsForMethod(loc, name));
		  res.addAll(super.getTags(name));
		  return res;
	  }
	  
	  
	  
}
