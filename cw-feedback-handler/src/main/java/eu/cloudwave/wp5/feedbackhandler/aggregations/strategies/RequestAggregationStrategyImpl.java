/*******************************************************************************
 * Copyright 2015 Software Evolution and Architecture Lab, University of Zurich
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 ******************************************************************************/
package eu.cloudwave.wp5.feedbackhandler.aggregations.strategies;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.IntSummaryStatistics;
import java.util.stream.Collectors;

import org.apache.commons.lang3.time.DateUtils;

import eu.cloudwave.wp5.common.constants.AggregationInterval;
import eu.cloudwave.wp5.common.util.AggregationIntervalConverter;
import eu.cloudwave.wp5.feedbackhandler.aggregations.RequestCollector;

/**
 * Implementation of a {@link RequestAggregationStrategy} that does not only calculate the min, avg and max of the given
 * timestamps but also the corresponding aggregation time period
 */
public class RequestAggregationStrategyImpl implements RequestAggregationStrategy {

  /**
   * Time Interval int from {@link java.util.Calendar}, used by this strategy because it is easier to group by
   */
  private int timestampAggregation;

  private Long timeRangeFrom;

  private Long timeRangeTo;

  /**
   * Strategy Constructor
   * 
   * @param timestampAggregation
   *          Time Interval int from {@link java.util.Calendar}
   */
  public RequestAggregationStrategyImpl(AggregationInterval interval, Long timeRangeFrom, Long timeRangeTo) {
    this.timestampAggregation = AggregationIntervalConverter.toCalendarInt(interval);

    if (timeRangeFrom != null) {
      this.timeRangeFrom = roundDate(timeRangeFrom);
    }

    if (timeRangeTo != null) {
      this.timeRangeTo = roundDate(timeRangeTo);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public RequestAggregationValues aggregate(RequestCollector requests) {
    double expectedCount = getExpectedNumberOfMeasurementValueGroups();

    /*
     * Group by aggregation interval and create summary statistics with min, avg, max and count
     */
    Collection<Long> groupedByAggregationInterval = requests.getReqTimestamps().stream()
        .collect(Collectors.groupingBy(timestamp -> DateUtils.round(new Date(timestamp), timestampAggregation), Collectors.counting())).values();
    int calculatedCount = groupedByAggregationInterval.size();

    try {
      if (calculatedCount != 0) {
        // use integer summaryStatistics to get min, avg, max
        IntSummaryStatistics stats = groupedByAggregationInterval.stream().mapToInt(p -> toInt(p)).summaryStatistics();

        // no time range selected, just return int summary
        if (expectedCount == 0) {
          return new RequestAggregationValuesImpl(stats.getMin(), stats.getMax(), stats.getAverage(), stats.getSum(), stats.getCount());
        }
        else {
          // if calculated count != expected count --> adjust minimum, average and count value
          if (Double.compare(calculatedCount, expectedCount) != 0) {
            double newAverage = (double) (stats.getSum() / expectedCount);
            return new RequestAggregationValuesImpl(0, stats.getMax(), newAverage, stats.getSum(), (int) expectedCount);
          }
          else {
            return new RequestAggregationValuesImpl(stats.getMin(), stats.getMax(), stats.getAverage(), stats.getSum(), (int) expectedCount);
          }
        }
      }
      else {
        return new RequestAggregationValuesImpl(0, 0, 0, 0, 0);
      }
    }
    catch (ArithmeticException e) {
      System.out.println(e.getMessage());
      return new RequestAggregationValuesImpl(0, 0, 0, 0, 0);
    }
  }

  private Long roundDate(Long timestamp) {
    return DateUtils.round(new Date(timestamp), this.timestampAggregation).getTime();
  }

  /**
   * Number of expected measurement value groups in the chosen time range based on the chosen aggregation interval
   * 
   * Example: time range: 1 day, aggregation interval: hours --> expects you to have 24 value groups, if this is not the
   * case, we have to add groups with 0 values, otherwise the minimum and average value will be wrong
   * 
   * @return number of groups as int
   */
  private double getExpectedNumberOfMeasurementValueGroups() {

    // no statement about expected number of measurement value groups because no time range is selected
    if (this.timeRangeFrom == null || this.timeRangeTo == null)
      return 0;

    double expectedCount = 0;

    switch (this.timestampAggregation) {
      case Calendar.SECOND:
        expectedCount = Math.floor((this.timeRangeTo - this.timeRangeFrom) / 1000);
        break;
      case Calendar.MINUTE:
        expectedCount = Math.floor((this.timeRangeTo - this.timeRangeFrom) / (1000 * 60));
        break;
      case Calendar.HOUR:
        expectedCount = Math.floor((this.timeRangeTo - this.timeRangeFrom) / (1000 * 60 * 60));
        break;
      case Calendar.DAY_OF_MONTH:
        expectedCount = Math.floor((this.timeRangeTo - this.timeRangeFrom) / (1000 * 60 * 60 * 24));
        break;
      case Calendar.MONTH:
        expectedCount = Math.floor((this.timeRangeTo - this.timeRangeFrom) / (1000 * 60 * 60 * 24 * 30));
        break;
    }

    return expectedCount;
  }

  /**
   * Helper method to convert a long to int
   * 
   * @param myLong
   * @return int
   */
  private int toInt(long myLong) {
    if (myLong < (long) Integer.MAX_VALUE) {
      return (int) myLong;
    }
    else {
      return Integer.MAX_VALUE;
    }
  }
}
