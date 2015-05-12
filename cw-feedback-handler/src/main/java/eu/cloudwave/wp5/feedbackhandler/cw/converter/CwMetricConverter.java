package eu.cloudwave.wp5.feedbackhandler.cw.converter;

import eu.cloudwave.wp5.common.model.Metric;
import eu.cloudwave.wp5.feedbackhandler.cw.model.CwMetric;

/**
 * Provides methods to convert different types of metrics.
 */
public interface CwMetricConverter {

  /**
   * Converts a {@link Metric} into a {@link CwMetric}.
   * 
   * @param metric
   *          the {@link Metric} to be converted
   * @return the converted metric
   */
  public CwMetric convert(Metric metric);

}
