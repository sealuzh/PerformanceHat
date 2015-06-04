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

import eu.cloudwave.wp5.common.model.Procedure;
import eu.cloudwave.wp5.common.model.ProcedureExecution;
import eu.cloudwave.wp5.common.model.impl.ProcedureExecutionImpl;

/**
 * A DTO for {@link ProcedureExecution}.
 */
public class ProcedureExecutionDto extends ProcedureExecutionImpl implements ProcedureExecution {

  private static final String CALLER__CALLEES__REFERENCE = "caller-callees";

  private List<ProcedureExecutionDto> callees;

  // default constructor required for jackson deserialization
  public ProcedureExecutionDto() {
    this(null, -1);
  }

  public ProcedureExecutionDto(final Procedure procedure, final long startTime) {
    super(procedure, startTime);
    this.callees = Lists.newArrayList();
  }

  /**
   * Adds a a procedure execution to the {@link List} of callees of the current procedure execution.
   * 
   * @param procedureExecution
   *          the {@link ProcedureExecutionDto} to be added
   */
  public void addCallee(final ProcedureExecutionDto procedureExecution) {
    this.callees.add(procedureExecution);
  }

  /**
   * Returns the {@link List} of all procedure executions called by the current one.
   * 
   * @return the {@link List} of all procedure executions called by the current one
   */
  @JsonManagedReference(CALLER__CALLEES__REFERENCE)
  public List<ProcedureExecutionDto> getCallees() {
    return callees;
  }

  /**
   * {@inheritDoc}
   */
  @JsonBackReference(CALLER__CALLEES__REFERENCE)
  @Override
  public ProcedureExecution getCaller() {
    return super.getCaller();
  }

  /**
   * {@inheritDoc}
   */
  @JsonDeserialize(as = ProcedureDto.class)
  @Override
  public Procedure getProcedure() {
    return super.getProcedure();
  }

}
