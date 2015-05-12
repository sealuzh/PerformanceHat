package eu.cloudwave.wp5.feedback.eclipse.base.resources.markers;

public class FeedbackMarkerTypeSerializer {

  public String serialize(FeedbackMarkerType type) {
    return type.toString();
  }

  public FeedbackMarkerType deserialize(String serializedType) {
    return new FeedbackMarkerTypeImpl(serializedType);
  }
}
