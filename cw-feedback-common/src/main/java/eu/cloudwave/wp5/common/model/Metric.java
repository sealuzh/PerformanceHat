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
 * A {@link Metric} is any kind of metric that can be monitored during the runtime of an application.
 */
public interface Metric {

  /**
   * Returns the type of the {@link Metric}.
   * 
   * @return the type of the {@link Metric}
   */
  public MetricType getType();

  /**
   * Returns the value of the {@link Metric}. The value is any numerical value given in the unit of the respective
   * {@link MetricType} (see {@link #getType()}.
   * 
   * @return the value of the {@link Metric}
   */
  public Number getValue();

  /**
   * Returns a textual qualifier. The qualifier together with the type allows to distinguish between different metrics.
   * 
   * @return a textual qualifier
   */
  public String getQualifier();

}
