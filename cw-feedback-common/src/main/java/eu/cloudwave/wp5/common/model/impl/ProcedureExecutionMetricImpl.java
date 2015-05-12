package eu.cloudwave.wp5.common.model.impl;

import eu.cloudwave.wp5.common.model.MetricType;
import eu.cloudwave.wp5.common.model.Procedure;
import eu.cloudwave.wp5.common.model.ProcedureExecution;
import eu.cloudwave.wp5.common.model.ProcedureExecutionMetric;

/**
 * Default implementation of {@link ProcedureExecutionMetric}.
 */
public class ProcedureExecutionMetricImpl extends AbstractProcedureExecutionMetric implements ProcedureExecutionMetric {

  private MetricType type;
  private ProcedureExecution procedureExecution;

  public ProcedureExecutionMetricImpl(final MetricType type, final ProcedureExecution procedureExecution, final String additionalQualifier, final Number value) {
    super(procedureExecution.getStartTime(), procedureExecution.getProcedure(), additionalQualifier, value);
    this.type = type;
    this.procedureExecution = procedureExecution;
  }

  public ProcedureExecutionMetricImpl(final long timestamp, final MetricType type, final Procedure procedure, final String additionalQualifier, final Number value) {
    super(timestamp, procedure, additionalQualifier, value);
    this.type = type;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public MetricType getType() {
    return type;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ProcedureExecution getProcedureExecution() {
    return procedureExecution;
  }

}
