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
package eu.cloudwave.wp5.common.model;

/**
 * A {@link MetricType} is a specific type of a monitored metrics (e.g. execution time).
 */
public interface MetricType {

  /**
   * Returns the name of the {@link MetricType} (e.g. execution time).
   * 
   * @return the name of the {@link MetricType}
   */
  public String getName();

  /**
   * Returns the abbreviation of the unit of the {@link MetricType} (e.g. 'ms' for milliseconds).
   * 
   * @return the abbreviation of the unit of the {@link MetricType}.
   */
  public String getUnit();

}
