/*******************************************************************************
 * Copyright 2015 Software Evolution and Architecture Lab, University of Zurich
 * 
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
package eu.cloudwave.wp5.common.dto.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.collect.Lists;

import eu.cloudwave.wp5.common.model.MetricType;
import eu.cloudwave.wp5.common.model.Procedure;
import eu.cloudwave.wp5.common.model.ProcedureExecution;
import eu.cloudwave.wp5.common.model.ProcedureExecutionMetric;

/**
 * Special case of {@link ProcedureExecutionDto} that holds all the metrics related to itself. Is used for transfer
 * between monitoring component and the feedback handler
 */
public class MetricContainingProcedureExecutionDto extends ProcedureExecutionDto implements ProcedureExecution {

  private static final String PROCEDURE_EXECUTION__METRICS__REFERENCE = "procedureExecution-metrics";

  private List<ContainedMetricDto> metrics;

  // default constructor is required for jackson deserialization
  public MetricContainingProcedureExecutionDto() {
    this(null, -1);
  }

  public MetricContainingProcedureExecutionDto(final Procedure procedure, final long startTime) {
    super(procedure, startTime);
    this.metrics = Lists.newArrayList();
  }

  /**
   * Returns the {@link List} of attached metrics.
   * 
   * @return the {@link List} of attached metrics
   */
  @JsonManagedReference(PROCEDURE_EXECUTION__METRICS__REFERENCE)
  public List<? extends ProcedureExecutionMetric> getMetrics() {
    return this.metrics;
  }

  /**
   * {@inheritDoc}
   */
  @JsonDeserialize(contentAs = MetricContainingProcedureExecutionDto.class)
  @Override
  public List<ProcedureExecutionDto> getCallees() {
    return super.getCallees();
  }

  /**
   * Attaches a metric to the current {@link RunningProcedureExecution}.
   * 
   * @param metric
   *          the metric to be attached
   */
  public void addMetric(final ProcedureExecutionMetric metric) {
    metrics.add(ContainedMetricDto.of(metric));
  }

  private static class ContainedMetricDto extends ProcedureExecutionMetricDto {

    // default constructor is required for jackson deserialization
    @SuppressWarnings("unused")
    public ContainedMetricDto() {
      super();
    }

    public ContainedMetricDto(final MetricType type, final ProcedureExecution procedureExecution, final String additionalQualifier, final Number value) {
      super(type, procedureExecution, additionalQualifier, value);
    }

    /**
     * {@inheritDoc}
     */
    @JsonBackReference(PROCEDURE_EXECUTION__METRICS__REFERENCE)
    @Override
    public ProcedureExecution getProcedureExecution() {
      return super.getProcedureExecution();
    }

    /**
     * Static factory method to create a {@link ContainedMetricDto}.
     * 
     * @param metric
     *          the input metric
     * @return the created {@link ContainedMetricDto}
     */
    public static ContainedMetricDto of(final ProcedureExecutionMetric metric) {
      return new ContainedMetricDto(metric.getType(), metric.getProcedureExecution(), metric.getAdditionalQualifier(), metric.getValue());
    }

  }

}
