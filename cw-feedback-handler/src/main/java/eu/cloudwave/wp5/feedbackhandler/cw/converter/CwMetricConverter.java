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
package eu.cloudwave.wp5.feedbackhandler.cw.converter;

import eu.cloudwave.wp5.common.model.Metric;
import eu.cloudwave.wp5.feedbackhandler.cw.model.CwMetric;

/**
 * Provides methods to convert different types of metrics.
 */
public interface CwMetricConverter {

  /**
   * Converts a {@link Metric} into a {@link CwMetric}.
   * 
   * @param metric
   *          the {@link Metric} to be converted
   * @return the converted metric
   */
  public CwMetric convert(Metric metric);

}
