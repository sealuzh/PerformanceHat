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
package eu.cloudwave.wp5.feedbackhandler.controller.dto;

import org.springframework.stereotype.Service;

import eu.cloudwave.wp5.common.dto.costs.AggregatedIncomingRequestsDto;
import eu.cloudwave.wp5.common.dto.costs.AggregatedMicroserviceRequestsDto;
import eu.cloudwave.wp5.feedbackhandler.aggregations.AggregatedClientRequestCollector;
import eu.cloudwave.wp5.feedbackhandler.aggregations.AggregatedIncomingRequestCollector;
import eu.cloudwave.wp5.feedbackhandler.aggregations.strategies.RequestAggregationValues;

/**
 * Implementation of {@link AggregatedMicroserviceRequestDtoFactory}, @service needed for Spring autowiring
 */
@Service
public class AggregatedMicroserviceRequestDtoFactoryImpl implements AggregatedMicroserviceRequestDtoFactory {

  @Override
  public AggregatedMicroserviceRequestsDto create(AggregatedClientRequestCollector collector) {
    final RequestAggregationValues aggreagtion = collector.getRequestAggregationValues();
    return new AggregatedMicroserviceRequestsDto(collector.getCaller(), collector.getCallee(), collector.getCalleeMethod(), aggreagtion.getMin(), aggreagtion.getMax(), aggreagtion.getAvg(),
        aggreagtion.getSum());
  }

  @Override
  public AggregatedIncomingRequestsDto create(AggregatedIncomingRequestCollector collector) {
    final RequestAggregationValues aggreagtion = collector.getRequestAggregationValues();
    return new AggregatedIncomingRequestsDto(collector.getIdentifier(), collector.getMethod(), aggreagtion.getMin(), aggreagtion.getMax(), aggreagtion.getAvg(), aggreagtion.getSum());
  }
}
