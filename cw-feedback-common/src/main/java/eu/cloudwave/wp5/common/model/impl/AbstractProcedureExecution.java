package eu.cloudwave.wp5.common.model.impl;

import eu.cloudwave.wp5.common.model.Procedure;
import eu.cloudwave.wp5.common.model.ProcedureExecution;

/**
 * Abstract base implementation of {@link ProcedureExecution}.
 */
public abstract class AbstractProcedureExecution implements ProcedureExecution {

  private Procedure procedure;
  private long startTime;

  public AbstractProcedureExecution(final Procedure procedure, final long startTime) {
    this.procedure = procedure;
    this.startTime = startTime;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Procedure getProcedure() {
    return procedure;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long getStartTime() {
    return startTime;
  }

}
