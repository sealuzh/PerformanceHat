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
 * A {@link ProcedureMetric} is a metric that is attached to a {@link ProcedureExecution}.
 */
public interface ProcedureExecutionMetric extends ProcedureMetric {

  /**
   * Returns the {@link ProcedureExecution} the current metric is attached to.
   * 
   * @return the {@link ProcedureExecution} the current metric is attached to
   */
  public ProcedureExecution getProcedureExecution();

  /**
   * Returns the timestamp of the current metric (i.e. the point in time when the metric has been measured).
   * 
   * @return the timestamp of the current metric
   */
  public long getTimestamp();

}
