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
import eu.cloudwave.wp5.common.model.ProcedureMetric;

/**
 * Abstract base implementation of {@link ProcedureMetric}.
 * 
 * The qualifier of the related procedure is used as basic qualifier. An additional qualifier can optionally be
 * specified. If so, {@link #getQualifier()} is composed of both the basic- and the additional qualifier. Otherwise it
 * simply returns the basic qualifier.
 */
public abstract class AbstractProcedureMetric extends AbstractMetric implements ProcedureMetric {

  private static final String SEPARATOR = "/";
  protected static final String EMPTY = "";

  private Procedure procedure;
  private String additionalQualifier;

  public AbstractProcedureMetric(final Procedure procedure, final String additionalQualifier, final Number value) {
    super(value);
    this.procedure = procedure;
    this.additionalQualifier = additionalQualifier;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getQualifier() {
    final String basicQualifier = getProcedure().getQualifier();
    return getAdditionalQualifier().equals(EMPTY) ? basicQualifier : basicQualifier + SEPARATOR + getAdditionalQualifier();
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
  public String getAdditionalQualifier() {
    return additionalQualifier;
  }

}
