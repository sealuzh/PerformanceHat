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
import eu.cloudwave.wp5.common.dto.costs.AggregatedMicroserviceRequestsDto;
import eu.cloudwave.wp5.feedbackhandler.aggregations.AggregatedClientRequestCollector;
import eu.cloudwave.wp5.feedbackhandler.aggregations.ClientRequestCollector;
import eu.cloudwave.wp5.feedbackhandler.aggregations.strategies.RequestAggregationStrategy;
import eu.cloudwave.wp5.feedbackhandler.aggregations.strategies.RequestAggregationStrategyImpl;
import eu.cloudwave.wp5.feedbackhandler.controller.dto.AggregatedMicroserviceRequestDtoFactory;
import eu.cloudwave.wp5.feedbackhandler.model.db.DbApplication;
import eu.cloudwave.wp5.feedbackhandler.repositories.ProcedureExecutionRepository;

/**
 * Implementation of {@link CostDataProvider}.
 */
@Service
public class CostDataProviderImpl implements CostDataProvider {

  @Autowired
  private AggregatedMicroserviceRequestDtoFactory aggregatedRequestsDtoFactory;

  @Autowired
  private ProcedureExecutionRepository procedureExecutionRepository;

  /**
   * {@inheritDoc}
   */
  @Override
  public List<AggregatedMicroserviceRequestsDto> getAllAggregatedRequests(final AggregationInterval aggregationInterval, final Long timeRangeFrom, final Long timeRangeTo) {
    final List<ClientRequestCollector> requests = procedureExecutionRepository.getAllRequests(timeRangeFrom, timeRangeTo);
    return aggregateRequestsByInterval(requests, aggregationInterval, timeRangeFrom, timeRangeTo);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<AggregatedMicroserviceRequestsDto> getAggregatedRequestsByCallee(
      final DbApplication application,
      final AggregationInterval aggregationInterval,
      final Long timeRangeFrom,
      final Long timeRangeTo) {
    final List<ClientRequestCollector> requests = procedureExecutionRepository.getRequestsByCallee(application.getApplicationId(), timeRangeFrom, timeRangeTo);
    return this.aggregateRequestsByInterval(requests, aggregationInterval, timeRangeFrom, timeRangeTo);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AggregatedMicroserviceRequestsDto getOverallAggregatedRequestsByCallee(
      final DbApplication application,
      final AggregationInterval aggregationInterval,
      final Long timeRangeFrom,
      final Long timeRangeTo) {
    final RequestAggregationStrategy strategy = new RequestAggregationStrategyImpl(aggregationInterval, timeRangeFrom, timeRangeTo);
    final List<ClientRequestCollector> requests = procedureExecutionRepository.getRequestsByCallee(application.getApplicationId(), timeRangeFrom, timeRangeTo);

    List<Long> allRequests = Lists.newArrayList();
    for (ClientRequestCollector req : requests) {
      allRequests.addAll(req.getReqTimestamps());
    }

    ClientRequestCollector allRequestsTogether = new ClientRequestCollector(requests.size() + " different callers", application.getApplicationId(), null, null, allRequests);
    return aggregatedRequestsDtoFactory.create(new AggregatedClientRequestCollector(allRequestsTogether, strategy));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<AggregatedMicroserviceRequestsDto> getAggregatedRequestsByCaller(
      final DbApplication application,
      final AggregationInterval aggregationInterval,
      final Long timeRangeFrom,
      final Long timeRangeTo) {
    List<ClientRequestCollector> requests = procedureExecutionRepository.getRequestsByCaller(application.getApplicationId(), timeRangeFrom, timeRangeTo);
    return this.aggregateRequestsByInterval(requests, aggregationInterval, timeRangeFrom, timeRangeTo);
  }

  /**
   * Helper function that aggregates all requests by given aggregation interval and puts it in a DTO
   * 
   * @param requests
   * @param aggregationInterval
   * @return a list of aggregated microservice requests with statistics (min, max, avg, etc.) as data transfer objects
   */
  private List<AggregatedMicroserviceRequestsDto> aggregateRequestsByInterval(
      List<ClientRequestCollector> requests,
      final AggregationInterval aggregationInterval,
      final Long timeRangeFrom,
      final Long timeRangeTo) {
    final RequestAggregationStrategy strategy = new RequestAggregationStrategyImpl(aggregationInterval, timeRangeFrom, timeRangeTo);

    List<AggregatedMicroserviceRequestsDto> dtoList = Lists.newArrayList();
    for (ClientRequestCollector request : requests) {
      AggregatedClientRequestCollector aggregatedRequest = new AggregatedClientRequestCollector(request, strategy);
      AggregatedMicroserviceRequestsDto aggregatedRequestDTO = aggregatedRequestsDtoFactory.create(aggregatedRequest);
      dtoList.add(aggregatedRequestDTO);
    }
    return dtoList;
  }
}
