package eu.cloudwave.wp5.common.model.impl;

import eu.cloudwave.wp5.common.model.Procedure;
import eu.cloudwave.wp5.common.model.ProcedureExecution;

/**
 * Implementation of {@link ProcedureExecution}.
 */
public class ProcedureExecutionImpl extends AbstractProcedureExecution implements ProcedureExecution {

  private ProcedureExecution caller;

  public ProcedureExecutionImpl(final Procedure procedure, final long startTime) {
    super(procedure, startTime);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ProcedureExecution getCaller() {
    return caller;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setCaller(final ProcedureExecution caller) {
    this.caller = caller;
  }

}
