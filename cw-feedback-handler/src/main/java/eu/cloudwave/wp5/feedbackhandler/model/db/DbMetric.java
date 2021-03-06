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
package eu.cloudwave.wp5.feedbackhandler.model.db;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import eu.cloudwave.wp5.common.model.Metric;
import eu.cloudwave.wp5.common.model.impl.MetricTypeImpl;
import eu.cloudwave.wp5.feedbackhandler.constants.DbTableNames;

/**
 * MongoDB-specific extension of {@link Metric}.
 */
@Document(collection = DbTableNames.METRICS)
@CompoundIndexes({
    @CompoundIndex(name = "metrics_lookup", def = "{'type': 1, 'procedure.className': 1, 'procedure.name': 1, 'procedure.arguments': 1, 'additionalQualifier': 1}")
})
public interface DbMetric extends Metric, DbEntity {

  public DbApplication getApplication();

  /**
   * {@inheritDoc}
   */
  @Override
  public MetricTypeImpl getType();

}
