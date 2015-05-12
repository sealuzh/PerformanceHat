package eu.cloudwave.wp5.feedbackhandler.repositories.aggregations;

/**
 * Result item of an aggregation of collection size values.
 */
public class AggregatedAverage {

  private String additionalQualifier;
  private double averageValue;

  public AggregatedAverage(final String additionalQualifier, final double averageValue) {
    this.additionalQualifier = additionalQualifier;
    this.averageValue = averageValue;
  }

  public String getAdditionalQualifier() {
    return additionalQualifier;
  }

  public double getAverageValue() {
    return averageValue;
  }

}
