package eu.cloudwave.wp5.feedbackhandler.cw.model;

/**
 * In CloudWave metrics have slightly different attributes than the common feedback handler metics. This interface
 * defines the attributes of a metric in the CloudWave scope.
 */
public interface CwMetric {

  /**
   * The name of the metric.
   * 
   * @return the name of the metric
   */
  public String getName();

  /**
   * Data related to the metric.
   * 
   * @return data related to the metric
   */
  public String getData();

  /**
   * The unit of the value.
   * 
   * @return the unit of the value
   */
  public String getUnit();

  /**
   * The value of the metric.
   * 
   * @return the value of the metric
   */
  public Number getValue();

}
