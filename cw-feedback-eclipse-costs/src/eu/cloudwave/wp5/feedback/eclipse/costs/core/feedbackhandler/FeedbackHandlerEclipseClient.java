package eu.cloudwave.wp5.feedback.eclipse.costs.core.feedbackhandler;

import eu.cloudwave.wp5.common.dto.AggregatedMicroserviceRequestsDto;
import eu.cloudwave.wp5.common.dto.AggregatedProcedureMetricsDto;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.FeedbackProject;

/**
 * An eclipse-specific client to talk to the feedback handler. Delegates the actual communication with the feedback
 * handler to a {@link FeedbackHandlerClient} and provides eclipse-specific functionality around it.
 */
public interface FeedbackHandlerEclipseClient {

  /**
   * Get all microservice requests
   * 
   * @param project
   *          the project
   * @return An array of {@link AggregatedProcedureMetricsDto}
   */
  public AggregatedMicroserviceRequestsDto[] all(FeedbackProject project);

  /**
   * Get all microservice requests to the given callee (application id)
   * 
   * @param project
   *          the project
   * @return An array of {@link AggregatedMicroserviceRequestsDto}
   */
  public AggregatedMicroserviceRequestsDto[] requestsByCallee(FeedbackProject project);

  /**
   * Get all microservice requests from the given caller (application id)
   * 
   * @param project
   *          the project
   * @return An array of {@link AggregatedMicroserviceRequestsDto}
   */
  public AggregatedMicroserviceRequestsDto[] requestsByCaller(FeedbackProject project);
}
