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
package eu.cloudwave.wp5.feedbackhandler.advices;

import java.util.List;

import eu.cloudwave.wp5.common.dto.AggregatedMicroserviceRequestsDto;
import eu.cloudwave.wp5.feedbackhandler.model.db.DbApplication;

/**
 * This class provides bunch of methods to get filtered an aggregated cost data.
 */
public interface CostDataProvider {

  /**
   * All microservice requests aggregated by given time interval
   * 
   * @param aggregationInterval
   *          {@link java.util.Calendar} time interval int value which will be used to aggregate the request time
   *          interval
   * @return a list of aggregated requests
   */
  public List<AggregatedMicroserviceRequestsDto> getAllAggregatedRequests(final int aggregationInterval);

  /**
   * Microservice requests to a given service aggregated by given time interval
   * 
   * @param application
   *          the callee application
   * @param aggregationInterval
   *          {@link java.util.Calendar} time interval int value which will be used to aggregate the request time
   *          interval
   * @return a filtered list of aggregated requests
   */
  public List<AggregatedMicroserviceRequestsDto> getAggregatedRequestsByCallee(final DbApplication application, final int aggregationInterval);

  /**
   * Microservice requests from a given service aggregated by given time interval
   * 
   * @param application
   *          the caller
   * @param aggregationInterval
   *          {@link java.util.Calendar} time interval int value which will be used to aggregate the request time
   *          interval
   * @return a filtered list of aggregated requests
   */
  public List<AggregatedMicroserviceRequestsDto> getAggregatedRequestsByCaller(final DbApplication application, final int aggregationInterval);

}
