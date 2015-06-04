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

import java.util.Date;
import java.util.IntSummaryStatistics;
import java.util.stream.Collectors;

import org.apache.commons.lang3.time.DateUtils;

import eu.cloudwave.wp5.common.constants.AggregationInterval;
import eu.cloudwave.wp5.common.util.AggregationIntervalConverter;
import eu.cloudwave.wp5.feedbackhandler.aggregations.RequestCollector;

/**
 * Simple implementation of a {@link RequestAggregationStrategy} that uses Java 8 streams and
 * {@link IntSummaryStatistics}
 */
public class SimpleRequestAggregationStrategyImpl implements RequestAggregationStrategy {

  /**
   * Time Interval int from {@link java.util.Calendar}
   */
  private int timestampAggregation;

  /**
   * Strategy Constructor
   * 
   * @param timestampAggregation
   *          Time Interval int from {@link java.util.Calendar}
   */
  public SimpleRequestAggregationStrategyImpl(AggregationInterval interval) {
    this.timestampAggregation = AggregationIntervalConverter.toCalendarInt(interval);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public RequestAggregationValues aggregate(RequestCollector requests) {

    IntSummaryStatistics stats = requests.getReqTimestamps().stream().collect(Collectors.groupingBy(timestamp -> DateUtils.round(new Date(timestamp), timestampAggregation), Collectors.counting()))
        .values().stream().mapToInt(p -> toInt(p)).summaryStatistics();

    return new RequestAggregationValuesImpl(stats.getMin(), stats.getMax(), stats.getAverage(), stats.getSum(), stats.getCount());
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
