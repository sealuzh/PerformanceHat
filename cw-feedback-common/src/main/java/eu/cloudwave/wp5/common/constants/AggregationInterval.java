package eu.cloudwave.wp5.common.constants;

/**
 * Aggregation Interval, e.g. to aggregate requests between microservices
 */
public enum AggregationInterval {
  SECOND("second"),
  MINUTE("minute"),
  HOUR("hour"),
  DAY("day"),
  MONTH("month");

  private String value;

  AggregationInterval(final String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return this.getValue();
  }

  public static final String[] POSSIBLE_VALUES = new String[] { SECOND.toString(), MINUTE.toString(), HOUR.toString(), DAY.toString(), MONTH.toString() };
}
