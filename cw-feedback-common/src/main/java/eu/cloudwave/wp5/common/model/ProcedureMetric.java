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
package eu.cloudwave.wp5.common.model;

/**
 * A {@link ProcedureMetric} is a metric that is attached to a {@link Procedure}. The qualifier of a
 * {@link ProcedureMetric} is composed of a basic qualifier, which is the qualifier of the related {@link Procedure},
 * and an additional qualifier. This allows to attach multiple {@link ProcedureMetric}'s of the same metric type to the
 * same {@link Procedure} and however being able to uniquely identify them.
 */
public interface ProcedureMetric extends Metric {

  /**
   * Returns the {@link Procedure} the current {@link ProcedureMetric} is attached to.
   * 
   * @return the {@link Procedure} the current {@link ProcedureMetric} is attached to
   */
  public Procedure getProcedure();

  /**
   * Returns the additional qualifier of the {@link ProcedureMetric}.
   * 
   * The additional qualifier guarantees that each metric is uniquely identifiable, even if there are multiple metrics
   * of the same type attached to the same {@link Procedure}. For example if the {@link Procedure} is a procedure or a
   * procedure execution, there could be multiple metrics of the same type for the different parameters (e.g. collection
   * size). In this case the name of the parameter can be used as additional qualifier. The additional qualifier can
   * also return an empty value for metrics that are uniquely identifiable by their base qualifier and their type. For
   * example there is only one metric of type execution time for a procedure execution. In this case
   * {@link #getAdditionalQualifier()} can return an empty {@link String}.
   * 
   * @return the additional qualifier of the {@link ProcedureMetric}
   */
  public String getAdditionalQualifier();

}
