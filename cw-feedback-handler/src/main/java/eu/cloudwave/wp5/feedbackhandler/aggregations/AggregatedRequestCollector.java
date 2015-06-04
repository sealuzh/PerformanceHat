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
package eu.cloudwave.wp5.feedbackhandler.aggregations;

import eu.cloudwave.wp5.feedbackhandler.aggregations.strategies.RequestAggregationStrategy;
import eu.cloudwave.wp5.feedbackhandler.aggregations.strategies.RequestAggregationValues;

public class AggregatedRequestCollector {

  /**
   * Aggreagtion Values like min, avg or max
   */
  private RequestAggregationValues aggregationValues;

  /**
   * RequestCollector that contains a list of requests
   */
  private RequestCollector collector;

  /**
   * Constructor
   * 
   * @param requests
   *          The requests of type {@link ClientRequestCollector}
   * @param strategy
   *          The implementation of {@link RequestAggregationStrategy}
   */
  public AggregatedRequestCollector(RequestCollector requests, RequestAggregationStrategy strategy) {
    this.collector = requests;
    this.aggregationValues = strategy.aggregate(requests);
  }

  /**
   * Setter for strategy that aggregates requests
   * 
   * @param strategy
   */
  public void setStrategy(RequestAggregationStrategy strategy) {
    this.aggregationValues = strategy.aggregate(collector);
  }

  /**
   * Getter for aggregated requests that have been aggregated by the strategy
   * 
   * @return {@link RequestAggregationValues}
   */
  public RequestAggregationValues getRequestAggregationValues() {
    return this.aggregationValues;
  }

  public RequestCollector getCollector() {
    return collector;
  }
}
