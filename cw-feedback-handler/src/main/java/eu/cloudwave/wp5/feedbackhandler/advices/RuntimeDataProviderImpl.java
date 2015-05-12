/*******************************************************************************
 * Copyright 2015 Software Evolution and Architecture Lab, University of Zurich
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package eu.cloudwave.wp5.feedbackhandler.advices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import eu.cloudwave.wp5.common.dto.AggregatedProcedureMetricsDto;
import eu.cloudwave.wp5.common.model.MetricType;
import eu.cloudwave.wp5.common.model.impl.MetricTypeImpl;
import eu.cloudwave.wp5.feedbackhandler.controller.dto.MetricDtoFactory;
import eu.cloudwave.wp5.feedbackhandler.model.db.DbApplication;
import eu.cloudwave.wp5.feedbackhandler.repositories.MetricRepository;
import eu.cloudwave.wp5.feedbackhandler.repositories.aggregations.ProcedureMetricAggregation;

/**
 * Implementation of {@link RuntimeDataProvider}.
 */
@Service
public class RuntimeDataProviderImpl implements RuntimeDataProvider {

  @Autowired
  private MetricRepository metricRepository;

  @Autowired
  private MetricDtoFactory metricDtoFactory;

  /**
   * {@inheritDoc}
   */
  @Override
  public List<AggregatedProcedureMetricsDto> hotspots(final DbApplication application, final double threshold) {
    final AggregationResults<ProcedureMetricAggregation> averageExecutionTimes = getAggregatedValues(application, MetricTypeImpl.EXECUTION_TIME);
    final AggregationResults<ProcedureMetricAggregation> averageCpuUsageValues = getAggregatedValues(application, MetricTypeImpl.CPU_USAGE);
    final List<AggregatedProcedureMetricsDto> aggregationDtos = metricDtoFactory.create(averageExecutionTimes.getMappedResults(), averageCpuUsageValues.getMappedResults());
    return filterWithThreshold(aggregationDtos, threshold);
  }

  private AggregationResults<ProcedureMetricAggregation> getAggregatedValues(final DbApplication application, final MetricType metricType) {
    return metricRepository.aggregateProcedureMetrics(application, metricType);
  }

  private List<AggregatedProcedureMetricsDto> filterWithThreshold(final List<AggregatedProcedureMetricsDto> potentialHotspots, final double threshold) {
    final List<AggregatedProcedureMetricsDto> hotspots = Lists.newArrayList();
    for (final AggregatedProcedureMetricsDto potentialHotpsot : potentialHotspots) {
      if (potentialHotpsot.getAverageExecutionTime() >= threshold) {
        hotspots.add(potentialHotpsot);
      }
    }
    return hotspots;
  }
}
