package eu.cloudwave.wp5.common.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import eu.cloudwave.wp5.common.dto.model.ProcedureDto;
import eu.cloudwave.wp5.common.model.Procedure;

/**
 * Contains the aggregated values of the execution time and the CPU usage of a procedure.
 */
public class AggregatedProcedureMetricsDto {

  private Procedure procedure;

  private double averageExecutionTime;

  private double averageCpuUsage;

  // Default constructor is required for deserialization
  public AggregatedProcedureMetricsDto() {
    this(null, -1, -1);
  }

  public AggregatedProcedureMetricsDto(final Procedure procedure, final double averageExecutionTime, final double averageCpuUsage) {
    this.procedure = procedure;
    this.averageExecutionTime = averageExecutionTime;
    this.averageCpuUsage = averageCpuUsage;
  }

  @JsonDeserialize(as = ProcedureDto.class)
  public Procedure getProcedure() {
    return procedure;
  }

  public double getAverageExecutionTime() {
    return averageExecutionTime;
  }

  public double getAverageCpuUsage() {
    return averageCpuUsage;
  }

}
