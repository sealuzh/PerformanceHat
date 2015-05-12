package eu.cloudwave.wp5.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import eu.cloudwave.wp5.common.error.ErrorType;
import eu.cloudwave.wp5.common.util.HashCodes;

/**
 * A DTO to transmit error messages.
 */
public class RestRequestErrorDto {

  private static final String UNKNOWN = "<unknown error>";

  private String message;
  private ErrorType type;

  public RestRequestErrorDto(final ErrorType type, final String message) {
    this.type = type;
    this.message = message;
  }

  public RestRequestErrorDto() {
    // jackson requires default constructor for deserialization
    this(ErrorType.UNKNOWN, UNKNOWN);
  }

  public ErrorType getType() {
    return type;
  }

  @JsonIgnore
  public String getTitle() {
    return type.getTitle();
  }

  public String getMessage() {
    return message;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(final Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj instanceof RestRequestErrorDto) {
      final RestRequestErrorDto rre = (RestRequestErrorDto) obj;
      if (rre.getMessage().equals(this.getMessage()) && rre.getType().equals(this.getType())) {
        return true;
      }
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    return HashCodes.combinedHashCode(message.hashCode(), type.hashCode());
  }

}
