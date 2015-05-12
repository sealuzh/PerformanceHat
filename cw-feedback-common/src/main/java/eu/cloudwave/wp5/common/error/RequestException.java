package eu.cloudwave.wp5.common.error;

import eu.cloudwave.wp5.common.error.ErrorType;

/**
 * An error that can be thrown in a controller if the request from the client was not valid.
 */
public class RequestException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private ErrorType type;

  public RequestException(final ErrorType type, final String message) {
    super(message);
    this.type = type;
  }

  public RequestException(final String message) {
    super(message);
    this.type = ErrorType.GENERAL;
  }

  public ErrorType getType() {
    return type;
  }

}
