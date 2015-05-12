package eu.cloudwave.wp5.feedbackhandler.repositories.aggregations;

import java.util.Calendar;
import java.util.Date;
import java.util.IntSummaryStatistics;
import java.util.stream.Collectors;

import org.apache.commons.lang3.time.DateUtils;

/*
 * Aggregate Microservice Client Request by given java.util.Calendar integer (default is Calendar.MINUTE)
 */
public class AggregatedMicroserviceClientRequest {

  private int timestampAggregation;
  private MicroserviceClientRequest microserviceClientRequest;

  public AggregatedMicroserviceClientRequest(MicroserviceClientRequest msClientReq, int timestampAggregation) {
    this.timestampAggregation = timestampAggregation;
    this.microserviceClientRequest = msClientReq;
  }

  public AggregatedMicroserviceClientRequest(MicroserviceClientRequest msClienReq) {
    this.microserviceClientRequest = msClienReq;

    // set default timestamp aggregation to minute
    this.timestampAggregation = Calendar.MINUTE;
  }

  public String getCaller() {
    return microserviceClientRequest.getCaller();
  }

  public String getCallee() {
    return microserviceClientRequest.getCallee();
  }

  private int toInt(long myLong) {
    if (myLong < (long) Integer.MAX_VALUE) {
      return (int) myLong;
    }
    else {
      return Integer.MAX_VALUE;
    }
  }

  public IntSummaryStatistics getStatistics() {
    // Map<Date, Long> groupedRequests = microserviceClientRequest.getReqTimestamps().stream()
    // .collect(Collectors.groupingBy(timestamp -> DateUtils.round(new Date(timestamp), timestampAggregation),
    // Collectors.counting()));

    return microserviceClientRequest.getReqTimestamps().stream().collect(Collectors.groupingBy(timestamp -> DateUtils.round(new Date(timestamp), timestampAggregation), Collectors.counting()))
        .values().stream().mapToInt(p -> toInt(p)).summaryStatistics();
  }
}
