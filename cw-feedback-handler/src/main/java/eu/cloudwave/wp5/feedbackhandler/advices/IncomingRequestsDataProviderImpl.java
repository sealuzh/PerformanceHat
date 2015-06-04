/*******************************************************************************
 * Copyright 2015 Software Evolution and Architecture Lab, University of Zurich
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 ******************************************************************************/
package eu.cloudwave.wp5.feedbackhandler.advices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import eu.cloudwave.wp5.common.constants.AggregationInterval;
import eu.cloudwave.wp5.common.dto.costs.AggregatedIncomingRequestsDto;
import eu.cloudwave.wp5.feedbackhandler.aggregations.AggregatedIncomingRequestCollector;
import eu.cloudwave.wp5.feedbackhandler.aggregations.IncomingRequestCollector;
import eu.cloudwave.wp5.feedbackhandler.aggregations.strategies.RequestAggregationStrategy;
import eu.cloudwave.wp5.feedbackhandler.aggregations.strategies.RequestAggregationStrategyImpl;
import eu.cloudwave.wp5.feedbackhandler.controller.dto.AggregatedMicroserviceRequestDtoFactory;
import eu.cloudwave.wp5.feedbackhandler.model.db.DbApplication;
import eu.cloudwave.wp5.feedbackhandler.repositories.ProcedureExecutionRepository;

/**
 * Implementation of {@link IncomingRequestsDataProvider}.
 */
@Service
public class IncomingRequestsDataProviderImpl implements IncomingRequestsDataProvider {

  @Autowired
  private AggregatedMicroserviceRequestDtoFactory aggregatedRequestsDtoFactory;

  @Autowired
  private ProcedureExecutionRepository procedureExecutionRepository;

  /**
   * {@inheritDoc}
   */
  @Override
  public List<AggregatedIncomingRequestsDto> getAllIncomingRequests(final AggregationInterval aggregationInterval, final Long timeRangeFrom, final Long timeRangeTo) {
    final List<IncomingRequestCollector> requests = procedureExecutionRepository.getAllIncomingRequests(timeRangeFrom, timeRangeTo);
    return aggregateIncomingRequestsByInterval(requests, aggregationInterval, timeRangeFrom, timeRangeTo);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<AggregatedIncomingRequestsDto> getIncomingRequestsByIdentifier(DbApplication application, AggregationInterval aggregationInterval, Long timeRangeFrom, Long timeRangeTo) {
    final List<IncomingRequestCollector> requests = procedureExecutionRepository.getIncomingByIdentifier(application.getApplicationId(), timeRangeFrom, timeRangeTo, true);
    return aggregateIncomingRequestsByInterval(requests, aggregationInterval, timeRangeFrom, timeRangeTo);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AggregatedIncomingRequestsDto getOverallIncomingRequestsByIdentifier(DbApplication application, AggregationInterval aggregationInterval, Long timeRangeFrom, Long timeRangeTo) {
    final List<IncomingRequestCollector> requests = procedureExecutionRepository.getIncomingByIdentifier(application.getApplicationId(), timeRangeFrom, timeRangeTo, false);
    final List<AggregatedIncomingRequestsDto> aggregatedRequests = aggregateIncomingRequestsByInterval(requests, aggregationInterval, timeRangeFrom, timeRangeTo);
    if (aggregatedRequests.isEmpty()) {
      return null;
    }
    else {
      return aggregatedRequests.get(0);
    }
  }

  /**
   * Helper function that aggregates all incoming requests by given aggregation interval and puts it in a DTO
   * 
   * @param requests
   * @param aggregationInterval
   * @return a list of aggregated microservice requests with statistics (min, max, avg, etc.) as data transfer objects
   */
  private List<AggregatedIncomingRequestsDto> aggregateIncomingRequestsByInterval(
      List<IncomingRequestCollector> requests,
      final AggregationInterval aggregationInterval,
      final Long timeRangeFrom,
      final Long timeRangeTo) {
    final RequestAggregationStrategy strategy = new RequestAggregationStrategyImpl(aggregationInterval, timeRangeFrom, timeRangeTo);

    List<AggregatedIncomingRequestsDto> dtoList = Lists.newArrayList();
    for (IncomingRequestCollector request : requests) {
      AggregatedIncomingRequestCollector aggregatedRequest = new AggregatedIncomingRequestCollector(request, strategy);
      AggregatedIncomingRequestsDto aggregatedRequestDTO = aggregatedRequestsDtoFactory.create(aggregatedRequest);
      dtoList.add(aggregatedRequestDTO);
    }
    return dtoList;
  }
}
