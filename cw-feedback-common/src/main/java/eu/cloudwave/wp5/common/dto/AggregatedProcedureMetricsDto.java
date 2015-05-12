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
