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
package eu.cloudwave.wp5.common.model.impl;

import eu.cloudwave.wp5.common.model.MetricType;
import eu.cloudwave.wp5.common.model.Procedure;
import eu.cloudwave.wp5.common.model.ProcedureExecution;
import eu.cloudwave.wp5.common.model.ProcedureExecutionMetric;

/**
 * Default implementation of {@link ProcedureExecutionMetric}.
 */
public class ProcedureExecutionMetricImpl extends AbstractProcedureExecutionMetric implements ProcedureExecutionMetric {

  private MetricType type;
  private ProcedureExecution procedureExecution;

  public ProcedureExecutionMetricImpl(final MetricType type, final ProcedureExecution procedureExecution, final String additionalQualifier, final Number value) {
    super(procedureExecution.getStartTime(), procedureExecution.getProcedure(), additionalQualifier, value);
    this.type = type;
    this.procedureExecution = procedureExecution;
  }

  public ProcedureExecutionMetricImpl(final long timestamp, final MetricType type, final Procedure procedure, final String additionalQualifier, final Number value) {
    super(timestamp, procedure, additionalQualifier, value);
    this.type = type;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public MetricType getType() {
    return type;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ProcedureExecution getProcedureExecution() {
    return procedureExecution;
  }

}
