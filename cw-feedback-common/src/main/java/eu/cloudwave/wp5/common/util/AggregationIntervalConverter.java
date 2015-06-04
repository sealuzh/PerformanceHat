package eu.cloudwave.wp5.common.util;

import java.util.Calendar;

import eu.cloudwave.wp5.common.constants.AggregationInterval;

public class AggregationIntervalConverter {

  /*
   * Helper function that takes the aggregation interval and returns a valid calendar-specific int from {@link
   * java.util.Calendar}
   */
  public static final int toCalendarInt(AggregationInterval interval) {
    switch (interval) {
      case MONTH:
        return Calendar.MONTH;
      case DAY:
        return Calendar.DAY_OF_MONTH;
      case HOUR:
        return Calendar.HOUR;
      case MINUTE:
        return Calendar.MINUTE;
      case SECOND:
        return Calendar.SECOND;
      default:
        return Calendar.SECOND;
    }
  }

  /*
   * Helper function that takes a string and returns an aggregation interval value, e.g. from a HTTP header
   */
  public static final AggregationInterval fromString(String interval) {
    switch (interval) {
      case "month":
        return AggregationInterval.MONTH;
      case "day":
        return AggregationInterval.DAY;
      case "hour":
        return AggregationInterval.HOUR;
      case "minute":
        return AggregationInterval.MINUTE;
      case "second":
        return AggregationInterval.SECOND;
      default:
        return AggregationInterval.SECOND;
    }
  }
}
