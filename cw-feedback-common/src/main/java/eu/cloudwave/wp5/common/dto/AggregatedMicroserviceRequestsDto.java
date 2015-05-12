package eu.cloudwave.wp5.common.dto;

/**
 * Contains the aggregated values of microservice requests from caller to callee
 */
public class AggregatedMicroserviceRequestsDto {

  private String caller;
  private String callee;
  private int min;
  private int max;
  private double avg;
  private long sum;

  /**
   * Default constructor should not be used, but is required for deserialization
   */
  public AggregatedMicroserviceRequestsDto() {
    this(null, null, 0, 0, 0, 0);
  }

  /**
   * Constructor
   * 
   * @param caller
   * @param callee
   * @param min
   * @param max
   * @param avg
   * @param sum
   */
  public AggregatedMicroserviceRequestsDto(String caller, String callee, int min, int max, double avg, long sum) {
    this.caller = caller;
    this.callee = callee;
    this.min = min;
    this.max = max;
    this.avg = avg;
    this.sum = sum;
  }

  public String getCaller() {
    return caller;
  }

  public String getCallee() {
    return callee;
  }

  public int getMin() {
    return min;
  }

  public int getMax() {
    return max;
  }

  public double getAvg() {
    return avg;
  }

  public long getSum() {
    return sum;
  }
}
