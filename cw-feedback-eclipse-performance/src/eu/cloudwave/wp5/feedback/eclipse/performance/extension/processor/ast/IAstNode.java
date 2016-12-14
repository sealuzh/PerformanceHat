package eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.jdt.core.dom.ASTNode;

import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.FeedbackMarkerType;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.AstContext;

//Note: equality is based on the node it represents and not how te node is represented
public interface IAstNode {
	
	public MethodDeclaration getCurrentMethode();
	public ASTNode getEclipseAstNode();
	
	public void attachTag(String name, Object value);
	
    public Collection<Object> getTags(String name);
	
	default public Collection<Double> getDoubleTags(String name){
		return getTags(name).stream().map(t -> {
			if(t instanceof Double) return (Double) t;
			else return Double.parseDouble(t.toString());
		}).collect(Collectors.toList());
	}
	
	default public Collection<Integer> getIntTags(String name){
		
		return getTags(name).stream().map(t -> {
			if(t instanceof Integer) return (Integer) t;
			else return Integer.parseInt(t.toString());
		}).collect(Collectors.toList());
	}
	
	public void markWarning(String id, FeedbackMarkerType type, String message, Map<String, Object> additionalAttributes);

	static class NodeWrapper extends AAstNode<org.eclipse.jdt.core.dom.ASTNode>{
		public NodeWrapper(org.eclipse.jdt.core.dom.ASTNode inner, AstContext ctx) {
			super(inner, ctx);
		}
	}

}
