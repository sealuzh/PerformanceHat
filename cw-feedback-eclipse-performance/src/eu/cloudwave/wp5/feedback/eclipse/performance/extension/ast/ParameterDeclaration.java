package eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import eu.cloudwave.wp5.common.model.Procedure;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ProgrammMarkerContext;

public class ParameterDeclaration extends AAstNode<org.eclipse.jdt.core.dom.SingleVariableDeclaration>{

	private final MethodDeclaration method;
	private final int paramPos;
	
	public ParameterDeclaration(int paramPos, MethodDeclaration method, org.eclipse.jdt.core.dom.SingleVariableDeclaration inner, ProgrammMarkerContext ctx) {
		super(inner, ctx);
		this.method = method;
		this.paramPos = paramPos;
	}

	
	private List<Double> metrics = null;
	
	@Override
	public List<Double> getDoubleTags(String name) {
		if(name.equals("CollectionSize")){
			if (metrics == null) {
				 Procedure procedure = method.createCorrelatingProcedure();
				 String[] arguments = procedure.getArguments().toArray(new String[procedure.getArguments().size()]);
			     Double averageSize = ctx.getFeedBackClient().collectionSize(ctx.getProject(), procedure.getClassName(), procedure.getName(), arguments, paramPos+"");
			     metrics = Collections.singletonList(averageSize);
	        }
	        return metrics;
		}
		return Collections.emptyList();
	}

	@Override
	public List<Object> getTags(String name) {
		return getDoubleTags(name).stream().map(t -> (Object)t).collect(Collectors.toList());
	}

	//Todo: Fill these two methods
	@Override
	protected int getStartPosition() {
		return 0;
	}

	@Override
	protected int getEndPosition() {
		return 0;
	}

}
