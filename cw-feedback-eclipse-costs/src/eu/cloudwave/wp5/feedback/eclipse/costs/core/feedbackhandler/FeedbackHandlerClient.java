package eu.cloudwave.wp5.feedback.eclipse.costs.core.feedbackhandler;

import eu.cloudwave.wp5.common.dto.AggregatedMicroserviceRequestsDto;

/**
 * Provides methods to talk to the Feedback Handler Server.
 */
public interface FeedbackHandlerClient {

  /**
   * Get all microservice requests
   * 
   * @param accessToken
   *          the access token
   * @param applicationId
   *          the application ID
   * @return An array of {@link AggregatedMicroserviceRequestsDto} containing all requests
   */
  public AggregatedMicroserviceRequestsDto[] allRequests(final String accessToken, final String applicationId);

  /**
   * Get all microservice requests to the given callee (application id)
   * 
   * @param accessToken
   *          the access token
   * @param applicationId
   *          the application ID which will be used to identify the callee
   * @return An array of {@link AggregatedMicroserviceRequestsDto}
   */
  public AggregatedMicroserviceRequestsDto[] requestsByCallee(final String accessToken, final String applicationId);

  /**
   * Get all microservice requests from the given caller (application id)
   * 
   * @param accessToken
   *          the access token
   * @param applicationId
   *          the application ID which will be used to identify the caller
   * @return An array of {@link AggregatedMicroserviceRequestsDto}
   */
  public AggregatedMicroserviceRequestsDto[] requestsByCaller(final String accessToken, final String applicationId);
}