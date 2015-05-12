package eu.cloudwave.wp5.feedbackhandler.controller.dto;

import java.util.IntSummaryStatistics;

import org.springframework.stereotype.Service;

import eu.cloudwave.wp5.common.dto.AggregatedMicroserviceRequestsDto;
import eu.cloudwave.wp5.feedbackhandler.repositories.aggregations.AggregatedMicroserviceClientRequest;

/**
 * Implementation of {@link AggregatedMicroserviceRequestDtoFactory}, @service needed for Spring autowiring
 */
@Service
public class AggregatedMicroserviceRequestDtoFactoryImpl implements AggregatedMicroserviceRequestDtoFactory {

  @Override
  public AggregatedMicroserviceRequestsDto create(AggregatedMicroserviceClientRequest request) {
    final IntSummaryStatistics stats = request.getStatistics();
    return new AggregatedMicroserviceRequestsDto(request.getCaller(), request.getCallee(), stats.getMin(), stats.getMax(), stats.getAverage(), stats.getSum());
  }
}
