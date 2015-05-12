package eu.cloudwave.wp5.common.error;

public enum ErrorType {

  // unauthorized
  INVALID_APPLICATION_ID("Invalid Application-ID"),
  WRONG_ACCESS_TOKEN("Wrong Access-Token"),

  // metric sources
  METRIC_SOURCE_NOT_AVAILABLE("Metric Source not available"),
  UNKNOWN_METRIC("Unknown Metric"),
  INVALID_PARAMETER("Invalid Parameter"),

  // new relic
  NEW_RELIC__INVALID_API_KEY("Bad API Key"),
  NEW_RELIC__INVALID_APPLICATION_ID("Invalid Application ID"),
  NEW_RELIC__GENERAL("General New Relic Error"),

  // feedback handler
  FEEDBACK_HANDLER_NOT_AVAILABLE("Feedback Handler not available"),

  // general
  GENERAL("General Error"),

  // default
  UNKNOWN("<unknown error>");

  private String title;

  private ErrorType(final String title) {
    this.title = title;
  }

  public String getTitle() {
    return title;
  }

}
