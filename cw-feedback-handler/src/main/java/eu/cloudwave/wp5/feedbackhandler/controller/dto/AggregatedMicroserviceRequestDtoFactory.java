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

import eu.cloudwave.wp5.common.dto.costs.AggregatedIncomingRequestsDto;
import eu.cloudwave.wp5.common.dto.costs.AggregatedMicroserviceRequestsDto;
import eu.cloudwave.wp5.feedbackhandler.aggregations.AggregatedClientRequestCollector;
import eu.cloudwave.wp5.feedbackhandler.aggregations.AggregatedIncomingRequestCollector;

/**
 * A factory for microservice request related data transfer objects.
 */
public interface AggregatedMicroserviceRequestDtoFactory {

  /**
   * Creates an {@link AggregatedMicroserviceRequestsDto} out of an {@link AggregatedClientRequestCollector}.
   * 
   * @param request
   *          input request of type {@link AggregatedClientRequestCollector}.
   * @return output request of type {@link AggregatedMicroserviceRequestsDto}
   */
  public AggregatedMicroserviceRequestsDto create(final AggregatedClientRequestCollector request);

  /**
   * Creates an {@link AggregatedIncomingRequestsDto} out of an {@link AggregatedIncomingRequestCollector}.
   * 
   * @param request
   *          input request of type {@link AggregatedIncomingRequestCollector}.
   * @return output request of type {@link AggregatedMicroserviceRequestsDto}
   */
  public AggregatedIncomingRequestsDto create(final AggregatedIncomingRequestCollector request);

}
