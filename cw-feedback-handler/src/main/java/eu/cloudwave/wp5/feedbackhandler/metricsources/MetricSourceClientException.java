package eu.cloudwave.wp5.feedbackhandler.metricsources;

import eu.cloudwave.wp5.common.error.ErrorType;

/**
 * This exception is thrown if an error occurred while fetching metrics data from an external metric source.
 */
public class MetricSourceClientException extends Exception {

  private static final long serialVersionUID = 1L;

  private ErrorType type;

  public MetricSourceClientException(final ErrorType type, final String message) {
    super(message);
    this.type = type;
  }

  public MetricSourceClientException(final ErrorType type, final Throwable cause) {
    super(cause);
    this.type = type;
  }

  public ErrorType getType() {
    return type;
  }

}
