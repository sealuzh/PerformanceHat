package eu.cloudwave.wp5.common.model;

/**
 * A {@link Metric} is any kind of metric that can be monitored during the runtime of an application.
 */
public interface Metric {

  /**
   * Returns the type of the {@link Metric}.
   * 
   * @return the type of the {@link Metric}
   */
  public MetricType getType();

  /**
   * Returns the value of the {@link Metric}. The value is any numerical value given in the unit of the respective
   * {@link MetricType} (see {@link #getType()}.
   * 
   * @return the value of the {@link Metric}
   */
  public Number getValue();

  /**
   * Returns a textual qualifier. The qualifier together with the type allows to distinguish between different metrics.
   * 
   * @return a textual qualifier
   */
  public String getQualifier();

}
