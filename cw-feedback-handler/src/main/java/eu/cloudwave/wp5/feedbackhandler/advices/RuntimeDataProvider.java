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

import eu.cloudwave.wp5.common.dto.AggregatedProcedureMetricsDto;
import eu.cloudwave.wp5.feedbackhandler.model.db.DbApplication;

/**
 * This class provides bunch of methods to get filtered an aggregated runtime data.
 */
public interface RuntimeDataProvider {

  /**
   * Get all hotspot methods of a given application.
   * 
   * @param application
   *          the application
   * @param threshold
   *          the hotspots threshold
   * @return a list of all hotspot methods
   */
  public List<AggregatedProcedureMetricsDto> hotspots(DbApplication application, double threshold);

}
