package eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;

import com.google.common.collect.Lists;

import eu.cloudwave.wp5.feedback.eclipse.performance.core.tag.MethodLocator;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ProgrammMarkerContext;

public abstract class AMethodRelated<T extends ASTNode> extends AAstNode<T> {
	public abstract MethodLocator createCorrespondingMethodLocation();

	public AMethodRelated(T inner, ProgrammMarkerContext ctx) {
		super(inner, ctx);
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
	
	@Override
	public List<Object> getTags(String name) {
		MethodLocator loc = createCorrespondingMethodLocation();
		List<Object> res = Lists.newArrayList();
		res.addAll(ctx.getTagProvider().getTagsForMethod(new MethodLocator(loc.className, loc.methodName, loc.argumentTypes),name));
		res.addAll(super.getTags(name));
		return res;
	}
}
