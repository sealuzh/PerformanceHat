package eu.cloudwave.wp5.common.model.impl;

import eu.cloudwave.wp5.common.model.Metric;
import eu.cloudwave.wp5.common.model.MetricType;

/**
 * Default implementation of {@link Metric}.
 */
public class MetricImpl extends AbstractMetric implements Metric {

  private MetricType type;

  private String qualifier;

  public MetricImpl(final MetricType type, final String qualifier, final Number value) {
    super(value);
    this.type = type;
    this.qualifier = qualifier;
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
  public String getQualifier() {
    return qualifier;
  }

}
