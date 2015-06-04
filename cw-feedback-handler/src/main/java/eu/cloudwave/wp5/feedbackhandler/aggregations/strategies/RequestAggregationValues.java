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

public interface RequestAggregationValues {

  /**
   * 
   * @return The result of the minimum aggregation function
   */
  public int getMin();

  /**
   * 
   * @return The result of the maximum aggregation function
   */
  public int getMax();

  /**
   * 
   * @return The result of the average aggregation function
   */
  public double getAvg();

  /**
   * 
   * @return The result of the sum aggregation function
   */
  public long getSum();

  /**
   * 
   * @return The result of the count aggregation function
   */
  public long getCount();
}
