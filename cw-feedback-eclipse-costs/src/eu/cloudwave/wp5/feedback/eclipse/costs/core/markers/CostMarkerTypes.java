package eu.cloudwave.wp5.feedback.eclipse.costs.core.markers;

import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.FeedbackMarkerType;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.FeedbackMarkerTypeImpl;

/**
 * The different types of markers. The feedback marker defined in the base project plugin.xml has an attribute 'type'
 * that specifies its type. This property stores the string representation of {@link CostMarkerTypes}.
 */
public class CostMarkerTypes {

  public static FeedbackMarkerType METHOD_DECLARATION = new FeedbackMarkerTypeImpl("microserviceMethodDeclaration");
  public static FeedbackMarkerType CLIENT_INVOCATION = new FeedbackMarkerTypeImpl("microserviceClientInvocation");

  private CostMarkerTypes() {}
}
