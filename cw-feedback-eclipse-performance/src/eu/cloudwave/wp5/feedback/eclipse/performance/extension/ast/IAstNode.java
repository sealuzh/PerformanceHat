package eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.jdt.core.dom.ASTNode;

import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.FeedbackMarkerType;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ProgrammMarkerContext;

//Note: equality is based on the node it represents and not how te node is represented
public interface IAstNode {
	
	public MethodDeclaration getCurrentMethode();
	public ASTNode getEclipseAstNode();
	
	public void attachTag(String name, Object value);
	
	default public List<Object> getTags(String name){
		return Collections.emptyList();
	}
	
	default public List<Double> getDoubleTags(String name){
		return getTags(name).stream().map(t -> {
			if(t instanceof Double) return (Double) t;
			else return Double.parseDouble(t.toString());
		}).collect(Collectors.toList());
	}
	
	default public List<Integer> getIntTags(String name){
		return getTags(name).stream().map(t -> {
			if(t instanceof Integer) return (Integer) t;
			else return Integer.parseInt(t.toString());
		}).collect(Collectors.toList());
	}
	
	public void markWarning(String id, FeedbackMarkerType type, String message, Map<String, Object> additionalAttributes);

	static class NodeWrapper extends AAstNode<org.eclipse.jdt.core.dom.ASTNode>{
		public NodeWrapper(org.eclipse.jdt.core.dom.ASTNode inner, ProgrammMarkerContext ctx) {
			super(inner, ctx);
		}
	}
	
	public static IAstNode fromEclipseAstNode(org.eclipse.jdt.core.dom.ASTNode expr, ProgrammMarkerContext ctx){
		return new NodeWrapper(expr, ctx);
	}

}
