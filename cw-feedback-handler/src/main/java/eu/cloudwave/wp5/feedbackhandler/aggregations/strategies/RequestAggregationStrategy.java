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
package eu.cloudwave.wp5.feedbackhandler.aggregations.strategies;

import eu.cloudwave.wp5.feedbackhandler.aggregations.RequestCollector;

/**
 * Microservices send requests to each other. This is an interface that provides a strategy to aggregate those requests
 * in order to get values like min, avg or max.
 */
public interface RequestAggregationStrategy {

  /**
   * Executes the aggregation of the microservice requests
   * 
   * @param requests
   * @return An object of {@link RequestAggregationValues} with min, avg and max values
   */
  public RequestAggregationValues aggregate(RequestCollector requests);
}
