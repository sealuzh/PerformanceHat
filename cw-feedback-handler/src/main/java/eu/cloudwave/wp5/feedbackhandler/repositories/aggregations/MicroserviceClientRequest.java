package eu.cloudwave.wp5.feedbackhandler.repositories.aggregations;

import java.util.List;

/**
 * Mongodb result item of a microservice client request
 */
public class MicroserviceClientRequest {

  private String caller;
  private String callee;
  private String methodName;
  private List<Long> reqTimestamps;

  public MicroserviceClientRequest(String caller, String callee, String methodName, List<Long> reqTimestamps) {
    /*
     * We only need the text in between the quotes
     */
    this.caller = caller.split("\"")[1];
    this.callee = callee.split("\"")[1];
    this.methodName = methodName;
    this.reqTimestamps = reqTimestamps;
  }

  public String getCaller() {
    return caller;
  }

  public String getCallee() {
    return callee;
  }

  public String getMethodName() {
    return methodName;
  }

  /*
   * Request timestamps in milliseconds
   */
  public List<Long> getReqTimestamps() {
    return reqTimestamps;
  }
}
