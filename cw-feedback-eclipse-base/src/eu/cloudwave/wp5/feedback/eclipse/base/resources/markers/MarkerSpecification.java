package eu.cloudwave.wp5.feedback.eclipse.base.resources.markers;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * Specifies the properties of a marker.
 */
public class MarkerSpecification {

  private String id;
  private FeedbackMarkerType type;
  private MarkerPosition position;
  private int severity;
  private String message;
  private Map<String, Object> additionalAttributes;

  public MarkerSpecification(final String id, final MarkerPosition position, final int severity, final FeedbackMarkerType type, final String message) {
    super();
    this.id = id;
    this.position = position;
    this.severity = severity;
    this.type = type;
    this.message = message;
    this.additionalAttributes = Maps.newHashMap();
  }

  public String getId() {
    return id;
  }

  public MarkerPosition getPosition() {
    return position;
  }

  public int getSeverity() {
    return severity;
  }

  public FeedbackMarkerType getType() {
    return type;
  }

  public String getMessage() {
    return message;
  }

  public Map<String, Object> getAdditionalAttributes() {
    return additionalAttributes;
  }

  /**
   * Adds the given key/value-pair to the list of additional attributes
   * 
   * @param key
   *          the key of the attribute
   * @param value
   *          the value of the attribute
   * @return the current {@link MarkerSpecification}
   */
  public MarkerSpecification and(final String key, final Object value) {
    additionalAttributes.put(key, value);
    return this;
  }

  /**
   * Creates a {@link MarkerSpecification} of the given attributes.
   * 
   * @param id
   *          the id of the marker
   * @param position
   *          position of the marker within the file
   * @param severity
   *          severity of the marker
   * @param type
   *          the type of the marker
   * @param message
   *          message of the marker
   * @return the created {@link MarkerSpecification}
   */
  public static MarkerSpecification of(final String id, final MarkerPosition position, final int severity, final FeedbackMarkerType type, final String message) {
    return new MarkerSpecification(id, position, severity, type, message);
  }

}
