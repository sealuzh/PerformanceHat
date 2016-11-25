package eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast;

import java.util.List;

import com.google.common.collect.Lists;

import eu.cloudwave.wp5.feedback.eclipse.performance.core.tag.MethodLocator;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ProgrammMarkerContext;

public class ParameterDeclaration extends AAstNode<org.eclipse.jdt.core.dom.SingleVariableDeclaration>{

	private final MethodDeclaration method;
	private final int paramPos;
	
	ParameterDeclaration(int paramPos, MethodDeclaration method, org.eclipse.jdt.core.dom.SingleVariableDeclaration inner, ProgrammMarkerContext ctx) {
		super(inner, ctx);
		this.method = method;
		this.paramPos = paramPos;
	}
	
	@Override
	public List<Object> getTags(String name) {
		MethodLocator loc = method.createCorrespondingMethodLocation();
		List<Object> res = Lists.newArrayList();
		res.addAll(ctx.getTagProvider().getTagsForParam(loc,paramPos,name));
		res.addAll(super.getTags(name));
		return res;
	}	
}
