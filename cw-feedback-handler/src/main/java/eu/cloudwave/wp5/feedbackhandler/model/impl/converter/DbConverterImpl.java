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
package eu.cloudwave.wp5.feedbackhandler.model.impl.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.cloudwave.wp5.common.model.Procedure;
import eu.cloudwave.wp5.common.model.ProcedureExecution;
import eu.cloudwave.wp5.common.model.ProcedureExecutionMetric;
import eu.cloudwave.wp5.common.model.impl.MetricTypeImpl;
import eu.cloudwave.wp5.feedbackhandler.model.db.DbApplication;
import eu.cloudwave.wp5.feedbackhandler.model.db.DbProcedure;
import eu.cloudwave.wp5.feedbackhandler.model.db.DbProcedureExecution;
import eu.cloudwave.wp5.feedbackhandler.model.db.DbProcedureExecutionMetric;
import eu.cloudwave.wp5.feedbackhandler.model.db.impl.DbProcedureExecutionImpl;
import eu.cloudwave.wp5.feedbackhandler.model.db.impl.DbProcedureExecutionMetricImpl;
import eu.cloudwave.wp5.feedbackhandler.model.db.impl.DbProcedureImpl;

/**
 * Implementation of {@link DbConverter}.
 */
@Service
public class DbConverterImpl implements DbConverter {

  @Autowired
  private ModelConverter modelConverter;

  @Override
  public DbProcedure convert(final Procedure procedure, final DbApplication application) {
    return new DbProcedureImpl(application, procedure.getClassName(), procedure.getName(), procedure.getKind(), procedure.getArguments(), procedure.getAnnotations());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DbProcedureExecution convert(final ProcedureExecution procedureExecution, final DbApplication application) {
    return new DbProcedureExecutionImpl(application, modelConverter.convert(procedureExecution.getProcedure()), procedureExecution.getStartTime());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DbProcedureExecutionMetric convert(final ProcedureExecutionMetric metric, final DbProcedureExecution procedureExecution, final DbApplication application) {
    return new DbProcedureExecutionMetricImpl(application, (MetricTypeImpl) metric.getType(), procedureExecution, metric.getAdditionalQualifier(), metric.getValue());
  }
}
