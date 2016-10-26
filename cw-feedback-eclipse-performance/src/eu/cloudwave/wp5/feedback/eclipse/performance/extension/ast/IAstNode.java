package eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast;

import java.util.List;
import java.util.Map;

import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.FeedbackMarkerType;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.MeasurementTag;

public interface IAstNode {
	
	public MethodDeclaration getCurrentMethode();
	
	public <E extends MeasurementTag> List<E> getMeasurementTags(Class<E> tagType);
	
	public void markWarning(String id, FeedbackMarkerType type, String message, Map<String, Object> additionalAttributes);

}
