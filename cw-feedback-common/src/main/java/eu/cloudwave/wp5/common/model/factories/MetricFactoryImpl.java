package eu.cloudwave.wp5.common.model.factories;

import eu.cloudwave.wp5.common.model.MetricType;
import eu.cloudwave.wp5.common.model.Procedure;
import eu.cloudwave.wp5.common.model.ProcedureExecution;
import eu.cloudwave.wp5.common.model.ProcedureExecutionMetric;
import eu.cloudwave.wp5.common.model.ProcedureMetric;
import eu.cloudwave.wp5.common.model.impl.ProcedureExecutionMetricImpl;
import eu.cloudwave.wp5.common.model.impl.ProcedureMetricImpl;

/**
 * Implementation of {@link MetricFactory}.
 */
public class MetricFactoryImpl implements MetricFactory {

  private static final String EMPTY = "";

  /**
   * {@inheritDoc}
   */
  @Override
  public ProcedureMetric create(final MetricType type, final Procedure procedure, final Number value) {
    return create(type, procedure, EMPTY, value);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ProcedureMetric create(final MetricType type, final Procedure procedure, final String additionalQualifier, final Number value) {
    return new ProcedureMetricImpl(type, procedure, additionalQualifier, value);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ProcedureExecutionMetric create(final MetricType type, final ProcedureExecution procedureExecution, final Number value) {
    return new ProcedureExecutionMetricImpl(type, procedureExecution, EMPTY, value);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ProcedureExecutionMetric create(final MetricType type, final ProcedureExecution procedureExecution, final String additionalQualifier, final Number value) {
    return new ProcedureExecutionMetricImpl(type, procedureExecution, additionalQualifier, value);
  }

}
