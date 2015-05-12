package eu.cloudwave.wp5.common.dto.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import eu.cloudwave.wp5.common.model.Metric;
import eu.cloudwave.wp5.common.model.MetricType;
import eu.cloudwave.wp5.common.model.impl.MetricImpl;
import eu.cloudwave.wp5.common.model.impl.MetricTypeImpl;

/**
 * A DTO for {@link Metric}.
 */
public class MetricDto extends MetricImpl implements Metric {

  // default constructor is required for jackson deserialization
  public MetricDto() {
    super(null, null, null);
  }

  public MetricDto(final MetricType type, final String qualifier, final Number value) {
    super(type, qualifier, value);
  }

  /**
   * {@inheritDoc}
   */
  @JsonDeserialize(as = MetricTypeImpl.class)
  @Override
  public MetricType getType() {
    return super.getType();
  }

}
