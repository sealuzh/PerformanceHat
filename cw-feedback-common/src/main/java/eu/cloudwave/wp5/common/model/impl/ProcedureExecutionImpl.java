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

import eu.cloudwave.wp5.common.model.Procedure;
import eu.cloudwave.wp5.common.model.ProcedureExecution;

/**
 * Implementation of {@link ProcedureExecution}.
 */
public class ProcedureExecutionImpl extends AbstractProcedureExecution implements ProcedureExecution {

  private ProcedureExecution caller;

  public ProcedureExecutionImpl(final Procedure procedure, final long startTime) {
    super(procedure, startTime);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ProcedureExecution getCaller() {
    return caller;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setCaller(final ProcedureExecution caller) {
    this.caller = caller;
  }

}
