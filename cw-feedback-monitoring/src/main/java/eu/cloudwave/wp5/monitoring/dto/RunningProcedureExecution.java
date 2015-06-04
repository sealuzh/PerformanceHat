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
package eu.cloudwave.wp5.monitoring.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import eu.cloudwave.wp5.common.dto.model.MetricContainingProcedureExecutionDto;
import eu.cloudwave.wp5.common.model.Procedure;
import eu.cloudwave.wp5.common.model.ProcedureExecution;

/**
 * A extension of a {@link MetricContainingProcedureExecutionDto} that additionally allows to specify whether the
 * procedure execution has already been finished. This is only used in the monitoring component (while constructing the
 * call trace) and should never be transmitted to any other component.
 */
public class RunningProcedureExecution extends MetricContainingProcedureExecutionDto implements ProcedureExecution {

  private boolean isFinish;

  public RunningProcedureExecution(final Procedure procedure, final long startTime) {
    super(procedure, startTime);
    this.isFinish = false;
  }

  /**
   * Checks whether the current {@link ProcedureExecution} is still executing or already finished.
   * 
   * @return <code>true</code> if the current {@link ProcedureExecution} is already finished, <code>false</code>
   *         otherwise
   */
  @JsonIgnore
  public boolean isFinished() {
    return isFinish;
  }

  /**
   * Finish the execution.
   */
  public void finish() {
    this.isFinish = true;
  }

}
