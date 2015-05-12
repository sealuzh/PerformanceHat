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
