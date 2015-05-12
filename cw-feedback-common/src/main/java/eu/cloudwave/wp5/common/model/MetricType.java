package eu.cloudwave.wp5.common.model;

/**
 * A {@link MetricType} is a specific type of a monitored metrics (e.g. execution time).
 */
public interface MetricType {

  /**
   * Returns the name of the {@link MetricType} (e.g. execution time).
   * 
   * @return the name of the {@link MetricType}
   */
  public String getName();

  /**
   * Returns the abbreviation of the unit of the {@link MetricType} (e.g. 'ms' for milliseconds).
   * 
   * @return the abbreviation of the unit of the {@link MetricType}.
   */
  public String getUnit();

}
