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
package eu.cloudwave.wp5.feedbackhandler.model.db.impl;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import eu.cloudwave.wp5.common.model.ProcedureExecution;
import eu.cloudwave.wp5.common.model.impl.AbstractProcedureExecutionMetric;
import eu.cloudwave.wp5.common.model.impl.MetricTypeImpl;
import eu.cloudwave.wp5.feedbackhandler.model.db.DbApplication;
import eu.cloudwave.wp5.feedbackhandler.model.db.DbProcedureExecutionMetric;

public class DbProcedureExecutionMetricImpl extends AbstractProcedureExecutionMetric implements DbProcedureExecutionMetric {

  @Id
  private ObjectId id;

  @DBRef
  private DbApplication application;

  @DBRef
  private ProcedureExecution procedureExecution;

  private MetricTypeImpl type;

  public DbProcedureExecutionMetricImpl(final DbApplication application, final MetricTypeImpl type, final ProcedureExecution procedureExecution, final String additionalQualifier, final Number value) {
    super(procedureExecution.getStartTime(), procedureExecution.getProcedure(), additionalQualifier, value);
    this.application = application;
    this.type = type;
    this.procedureExecution = procedureExecution;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ObjectId getId() {
    return id;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DbApplication getApplication() {
    return application;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public MetricTypeImpl getType() {
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
