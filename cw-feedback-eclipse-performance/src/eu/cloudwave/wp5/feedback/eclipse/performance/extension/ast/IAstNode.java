package eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.FeedbackMarkerType;

public interface IAstNode {
	
	public MethodDeclaration getCurrentMethode();
	
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

}
