package eu.cloudwave.wp5.feedbackhandler.metricsources;

import eu.cloudwave.wp5.common.dto.newrelic.MethodInfoSummarized;

/**
 * This is a general interface to access an API of an external metric source.
 */
public interface MetricSourceClient {

  /**
   * Get summarized information about the given method.
   * 
   * @param applicationId
   *          the application ID
   * @param className
   *          the name of the class that contains the method
   * @param methodName
   *          the name of the method
   * @return summarized information about the given method
   * @throws MetricSourceClientException
   *           if the request was not successful
   */
  public MethodInfoSummarized summarized(String applicationId, String className, String methodName) throws MetricSourceClientException;

}
