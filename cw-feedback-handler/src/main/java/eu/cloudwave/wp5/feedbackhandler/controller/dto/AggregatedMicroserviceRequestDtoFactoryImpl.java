/*******************************************************************************
 * Copyright 2015 Software Evolution and Architecture Lab, University of Zurich
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
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
