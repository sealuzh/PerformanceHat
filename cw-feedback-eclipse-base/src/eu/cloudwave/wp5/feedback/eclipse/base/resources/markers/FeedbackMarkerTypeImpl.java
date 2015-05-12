package eu.cloudwave.wp5.feedback.eclipse.base.resources.markers;

public class FeedbackMarkerTypeImpl implements FeedbackMarkerType {

  private final String name;

  public FeedbackMarkerTypeImpl(String name) {
    this.name = name;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public String toString() {
    return getName();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (this.getClass().isInstance(obj))
      return this.getName().equals(((FeedbackMarkerType) obj).getName());
    return false;
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }
}
