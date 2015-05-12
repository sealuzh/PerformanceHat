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
