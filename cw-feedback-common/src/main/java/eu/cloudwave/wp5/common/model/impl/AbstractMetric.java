package eu.cloudwave.wp5.common.model.impl;

import eu.cloudwave.wp5.common.model.Metric;

/**
 * Abstract base implementation of {@link Metric}.
 * 
 * REMARK: As long as the concrete metric type is implemented as enum, the type-attribute cannot be set in the abstract
 * base class, because the concrete implementation requires the exact type of the enum to be able to convert the String
 * representation back to the enum. Therefore the type attribute has to be set separately in each concrete
 * implementation.
 */
public abstract class AbstractMetric implements Metric {

  private Number value;

  public AbstractMetric(final Number value) {
    this.value = value;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final Number getValue() {
    return value;
  }

}
