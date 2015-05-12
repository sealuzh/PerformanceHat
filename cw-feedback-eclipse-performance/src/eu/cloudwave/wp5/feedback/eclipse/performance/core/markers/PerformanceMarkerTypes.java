package eu.cloudwave.wp5.feedback.eclipse.performance.core.markers;

import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.FeedbackMarkerType;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.FeedbackMarkerTypeImpl;

/**
 * The different types of markers. The feedback marker defined in plugin.xml has an attribute 'type' that specifies its
 * type. This property stores the string representation of {@link PerformanceMarkerTypes}.
 */
public class PerformanceMarkerTypes {

  public static FeedbackMarkerType HOTSPOT = new FeedbackMarkerTypeImpl("hotspot");
  public static FeedbackMarkerType COLLECTION_SIZE = new FeedbackMarkerTypeImpl("collection-size");

  private PerformanceMarkerTypes() {}
}
