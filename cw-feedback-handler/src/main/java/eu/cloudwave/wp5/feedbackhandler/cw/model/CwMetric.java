/*******************************************************************************
 * Copyright 2015 Software Evolution and Architecture Lab, University of Zurich
 * 
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
package eu.cloudwave.wp5.feedbackhandler.cw.model;

/**
 * In CloudWave metrics have slightly different attributes than the common feedback handler metics. This interface
 * defines the attributes of a metric in the CloudWave scope.
 */
public interface CwMetric {

  /**
   * The name of the metric.
   * 
   * @return the name of the metric
   */
  public String getName();

  /**
   * Data related to the metric.
   * 
   * @return data related to the metric
   */
  public String getData();

  /**
   * The unit of the value.
   * 
   * @return the unit of the value
   */
  public String getUnit();

  /**
   * The value of the metric.
   * 
   * @return the value of the metric
   */
  public Number getValue();

}
