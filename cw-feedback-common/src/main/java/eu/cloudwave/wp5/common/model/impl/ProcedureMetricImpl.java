package eu.cloudwave.wp5.common.model.impl;

import eu.cloudwave.wp5.common.model.MetricType;
import eu.cloudwave.wp5.common.model.Procedure;
import eu.cloudwave.wp5.common.model.ProcedureMetric;

/**
 * Default implementation of {@link AbstractMetric}
 */
public class ProcedureMetricImpl extends AbstractProcedureMetric implements ProcedureMetric {

  private MetricType type;

  public ProcedureMetricImpl(final MetricType type, final Procedure procedure, final String additionalQualifier, final Number value) {
    super(procedure, additionalQualifier, value);
    this.type = type;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public MetricType getType() {
    return type;
  }

}
