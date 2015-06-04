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
package eu.cloudwave.wp5.feedbackhandler.service;

import eu.cloudwave.wp5.common.dto.model.MetricContainingProcedureExecutionDto;
import eu.cloudwave.wp5.feedbackhandler.model.db.DbApplication;

/**
 * Provides methods to deal with monitoring data.
 */
public interface MonitoringDataService {

  /**
   * The data that the feedback handler receives from a monitored component is transmitted as a
   * {@link MetricContainingProcedureExecutionDto}. The whole call trace (including its metrics) is stored in a
   * tree-like structure with one procedure execution as a root.
   * 
   * This method goes through the whole call trace tree and persists all the entities (procedure executions and metrics)
   * into the database.
   * 
   * @param application
   *          the application the data belongs to
   * @param procedureExecution
   *          the root procedure execution containing the whole call trace
   */
  public void persist(final DbApplication application, final MetricContainingProcedureExecutionDto procedureExecution);

}
