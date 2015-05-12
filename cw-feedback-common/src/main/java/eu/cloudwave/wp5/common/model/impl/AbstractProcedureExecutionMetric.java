package eu.cloudwave.wp5.common.model.impl;

import eu.cloudwave.wp5.common.model.Procedure;
import eu.cloudwave.wp5.common.model.ProcedureExecutionMetric;

public abstract class AbstractProcedureExecutionMetric extends AbstractProcedureMetric implements ProcedureExecutionMetric {

  private long timestamp;

  public AbstractProcedureExecutionMetric(final long timestamp, final Procedure procedure, final String additionalQualifier, final Number value) {
    super(procedure, additionalQualifier, value);
    this.timestamp = timestamp;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long getTimestamp() {
    return timestamp;
  }

}
