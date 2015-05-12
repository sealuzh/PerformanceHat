package eu.cloudwave.wp5.feedbackhandler.advices;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.cloudwave.wp5.common.dto.AggregatedMicroserviceRequestsDto;
import eu.cloudwave.wp5.feedbackhandler.controller.dto.AggregatedMicroserviceRequestDtoFactory;
import eu.cloudwave.wp5.feedbackhandler.model.db.DbApplication;
import eu.cloudwave.wp5.feedbackhandler.repositories.ProcedureExecutionRepository;
import eu.cloudwave.wp5.feedbackhandler.repositories.aggregations.AggregatedMicroserviceClientRequest;
import eu.cloudwave.wp5.feedbackhandler.repositories.aggregations.MicroserviceClientRequest;

/**
 * Implementation of {@link RuntimeDataProvider}.
 */
@Service
public class CostDataProviderImpl implements CostDataProvider {

  @Autowired
  private AggregatedMicroserviceRequestDtoFactory aggregatedRequestsDtoFactory;

  @Autowired
  private ProcedureExecutionRepository procedureExecutionRepository;

  /**
   * Helper function that aggregates all requests by given aggregation interval
   * 
   * @param requests
   * @param aggregationInterval
   * @return a list of aggregated microservice requests with statistics (min, max, avg, etc.) as data transfer objects
   */
  private List<AggregatedMicroserviceRequestsDto> aggregatedRequests(List<MicroserviceClientRequest> requests, final int aggregationInterval) {
    return requests.stream().map(request -> aggregatedRequestsDtoFactory.create(new AggregatedMicroserviceClientRequest(request, aggregationInterval))).collect(Collectors.toList());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<AggregatedMicroserviceRequestsDto> getAllAggregatedRequests(final int aggregationInterval) {
    List<MicroserviceClientRequest> requests = procedureExecutionRepository.getAllRequests();
    return this.aggregatedRequests(requests, aggregationInterval);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<AggregatedMicroserviceRequestsDto> getAggregatedRequestsByCallee(final DbApplication application, final int aggregationInterval) {
    List<MicroserviceClientRequest> requests = procedureExecutionRepository.getRequestsByCallee(application.getApplicationId());
    return this.aggregatedRequests(requests, aggregationInterval);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<AggregatedMicroserviceRequestsDto> getAggregatedRequestsByCaller(final DbApplication application, final int aggregationInterval) {
    List<MicroserviceClientRequest> requests = procedureExecutionRepository.getRequestsByCaller(application.getApplicationId());
    return this.aggregatedRequests(requests, aggregationInterval);
  }
}
