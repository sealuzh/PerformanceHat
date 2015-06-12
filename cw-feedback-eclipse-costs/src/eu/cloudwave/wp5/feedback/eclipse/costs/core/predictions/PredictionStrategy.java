package eu.cloudwave.wp5.feedback.eclipse.costs.core.predictions;

import java.util.List;

import eu.cloudwave.wp5.common.dto.ApplicationDto;
import eu.cloudwave.wp5.common.dto.costs.AggregatedRequestsDto;
import eu.cloudwave.wp5.common.model.Prediction;

/**
 * A {@link PredictionStrategy} takes some input and returns predictions as output. The underlying algorithm is
 * delegated to the actual implementation.
 */
public interface PredictionStrategy {

  /**
   * Executes the strategy to generate cost predictions
   * 
   * @param invokedApplication
   *          {@link ApplicationDto} of the microservice that has been invoked
   * @param existingRequestsToInvokedApplication
   *          {@link AggregatedRequestsDto} with existing overall requests to the invoked microservice
   * @param expectedAdditionalRequests
   *          {@link AggregatedRequestsDto} with expected additional requests
   * 
   * @return {@link List<Prediction>} a list of predictions
   */
  public List<Prediction> predict(ApplicationDto invokedApplication, AggregatedRequestsDto existingRequestsToInvokedApplication, AggregatedRequestsDto expectedAdditionalRequests);
}
