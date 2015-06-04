package eu.cloudwave.wp5.feedbackhandler.aggregations;

import java.util.List;

import eu.cloudwave.wp5.common.util.Splitters;

/**
 * Request with anonymous caller used to count the number of incoming requests to this microservice
 */
public class IncomingRequestCollector implements RequestCollector {

  /**
   * The identifier of the receiving service
   */
  private String identifier;

  /**
   * The method identifier which receives the request
   */
  private String method;

  /**
   * List of timestamps of requests in milliseconds
   */
  private List<Long> reqTimestamps;

  public IncomingRequestCollector(String identifier, String method, List<Long> reqTimestamps) {
    this.identifier = Splitters.inBetweenBrackets(identifier);
    this.method = Splitters.inBetweenBrackets(method);
    this.reqTimestamps = reqTimestamps;
  }

  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = Splitters.inBetweenBrackets(identifier);
  }

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = Splitters.inBetweenBrackets(method);
  }

  /**
   * {@inheritDoc}
   */
  public List<Long> getReqTimestamps() {
    return reqTimestamps;
  }

  public void setReqTimestamps(List<Long> reqTimestamps) {
    this.reqTimestamps = reqTimestamps;
  }
}
