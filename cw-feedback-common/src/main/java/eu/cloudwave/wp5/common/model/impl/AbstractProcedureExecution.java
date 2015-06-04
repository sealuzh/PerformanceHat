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
 * Abstract base implementation of {@link ProcedureExecution}.
 */
public abstract class AbstractProcedureExecution implements ProcedureExecution {

  private Procedure procedure;
  private long startTime;

  public AbstractProcedureExecution(final Procedure procedure, final long startTime) {
    this.procedure = procedure;
    this.startTime = startTime;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Procedure getProcedure() {
    return procedure;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long getStartTime() {
    return startTime;
  }

}
