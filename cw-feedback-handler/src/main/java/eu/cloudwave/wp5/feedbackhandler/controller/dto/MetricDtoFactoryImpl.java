package eu.cloudwave.wp5.feedbackhandler.controller.dto;

import java.util.List;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import eu.cloudwave.wp5.common.dto.AggregatedProcedureMetricsDto;
import eu.cloudwave.wp5.common.dto.model.ProcedureExecutionMetricDto;
import eu.cloudwave.wp5.common.model.Procedure;
import eu.cloudwave.wp5.common.model.ProcedureExecution;
import eu.cloudwave.wp5.common.model.ProcedureExecutionMetric;
import eu.cloudwave.wp5.feedbackhandler.repositories.aggregations.ProcedureMetricAggregation;

/**
 * Implementation of {@link MetricDtoFactory}.
 */
@Service
public class MetricDtoFactoryImpl implements MetricDtoFactory {

  private static final double NULL_VALUE = -1;

  /**
   * {@inheritDoc}
   */
  @Override
  public ProcedureExecutionMetricDto create(final ProcedureExecutionMetric metric) {
    final ProcedureExecution procExec = metric.getProcedureExecution();
    return new ProcedureExecutionMetricDto(procExec.getStartTime(), metric.getType(), procExec.getProcedure(), metric.getAdditionalQualifier(), metric.getValue());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<ProcedureExecutionMetricDto> create(final List<? extends ProcedureExecutionMetric> metrics) {
    final List<ProcedureExecutionMetricDto> metricDtos = Lists.newArrayList();
    for (final ProcedureExecutionMetric metric : metrics) {
      metricDtos.add(create(metric));
    }
    return metricDtos;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<AggregatedProcedureMetricsDto> create(final List<ProcedureMetricAggregation> aggregatedExecutionTimes, final List<ProcedureMetricAggregation> aggregatedCpuUsages) {
    final List<ProcedureMetricAggregation> remainingAggregatedCpuUsages = Lists.newArrayList(aggregatedCpuUsages);
    final List<AggregatedProcedureMetricsDto> aggregatedMetricsDtos = Lists.newArrayList();
    // add average execution time and where found, average cpu usages.
    for (final ProcedureMetricAggregation executionTimeAggregation : aggregatedExecutionTimes) {
      final Procedure procedure = executionTimeAggregation.getProcedure();
      final double averageExecutionTime = executionTimeAggregation.getAverageValue();
      final ProcedureMetricAggregation cpuUsageAggregation = popIfContains(remainingAggregatedCpuUsages, procedure);
      final double averageCpuUsage = cpuUsageAggregation != null ? cpuUsageAggregation.getAverageValue() : NULL_VALUE;
      aggregatedMetricsDtos.add(new AggregatedProcedureMetricsDto(procedure, averageExecutionTime, averageCpuUsage));
    }
    // add remaining average CPU usage values
    for (final ProcedureMetricAggregation cpuUsageAggregation : remainingAggregatedCpuUsages) {
      aggregatedMetricsDtos.add(new AggregatedProcedureMetricsDto(cpuUsageAggregation.getProcedure(), NULL_VALUE, cpuUsageAggregation.getAverageValue()));
    }
    return aggregatedMetricsDtos;
  }

  /**
   * Checks whether an item with the given {@link Procedure} is in the given {@link List} of
   * {@link ProcedureMetricAggregation}'s. If yes, removes the item from the {@link List} and returns it.
   * 
   * @param aggregations
   *          the {@link List} of {@link ProcedureMetricAggregation}'s.
   * @param procedure
   *          the {@link Procedure}
   * @return the item with the given {@link Procedure} if contained in the list or <code>null</code> otherwise
   */
  private ProcedureMetricAggregation popIfContains(final List<ProcedureMetricAggregation> aggregations, final Procedure procedure) {
    ProcedureMetricAggregation result = null;
    for (final ProcedureMetricAggregation aggregation : aggregations) {
      if (aggregation.getProcedure().equals(procedure)) {
        result = aggregation;
        break;
      }
    }
    if (result != null) {
      aggregations.remove(result);
    }
    return result;
  }
}
