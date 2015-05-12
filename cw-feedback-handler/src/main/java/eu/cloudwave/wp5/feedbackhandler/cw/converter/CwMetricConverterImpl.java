package eu.cloudwave.wp5.feedbackhandler.cw.converter;

import org.springframework.stereotype.Service;

import eu.cloudwave.wp5.common.model.Metric;
import eu.cloudwave.wp5.feedbackhandler.cw.model.CwMetric;
import eu.cloudwave.wp5.feedbackhandler.cw.model.CwMetricImpl;

/**
 * Implementation of {@link CwMetricConverter}.
 */
@Service
public class CwMetricConverterImpl implements CwMetricConverter {

  /**
   * {@inheritDoc}
   */
  @Override
  public CwMetric convert(final Metric metric) {
    return CwMetricImpl.of(metric.getType().getName(), metric.getQualifier(), metric.getType().getUnit(), metric.getValue());
  }
}
