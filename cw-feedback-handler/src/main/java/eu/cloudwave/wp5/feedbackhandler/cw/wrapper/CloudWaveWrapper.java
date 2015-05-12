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
package eu.cloudwave.wp5.feedbackhandler.cw.wrapper;

import eu.cloudwave.wp5.feedbackhandler.cw.model.CwMetric;

/**
 * This is an additional wrapper for the CloudWave metrics backend. It decouples the rest of the feedback handler
 * related code from the actual CloudWave wrapper. Therefore changes in the CloudWave wrapper (which are conducted
 * independently of the feedback handler) do not influence the rest of the feedback handler.
 */
public interface CloudWaveWrapper {

  /**
   * Record a metric into the CloudWave backend.
   * 
   * @param metric
   *          the metric to be recorded
   * @return <code>true</code> if the metric could successfully be recorded, <code>false</code> otherwise
   */
  public boolean recordMetric(CwMetric metric);
  
  /**
   * Record a metric into the CloudWave backend with additional data appended to metadata.
   * 
   * @param metric
   *          the metric to be recorded
   * @param additionalData
   *          additional data appended to metadata         
   * @return <code>true</code> if the metric could successfully be recorded, <code>false</code> otherwise
   */
  public boolean recordMetric(CwMetric metric, String additionalData);  
}
