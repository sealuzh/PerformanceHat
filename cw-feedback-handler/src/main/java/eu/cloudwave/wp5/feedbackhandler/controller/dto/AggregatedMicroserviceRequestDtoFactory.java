package eu.cloudwave.wp5.feedbackhandler.controller.dto;

import eu.cloudwave.wp5.common.dto.AggregatedMicroserviceRequestsDto;
import eu.cloudwave.wp5.feedbackhandler.repositories.aggregations.AggregatedMicroserviceClientRequest;

/**
 * A factory for microservice request related data transfer objects.
 */
public interface AggregatedMicroserviceRequestDtoFactory {

  /**
   * Creates a {@link AggregatedMicroserviceRequestsDto} out of a {@link AggregatedMicroserviceClientRequest}.
   * 
   * @param request
   *          input request of type {@link AggregatedMicroserviceClientRequest}.
   * @return output request of type {@link AggregatedMicroserviceRequestsDto}
   */
  public AggregatedMicroserviceRequestsDto create(final AggregatedMicroserviceClientRequest request);

}
