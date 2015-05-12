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
package eu.cloudwave.wp5.feedbackhandler.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import eu.cloudwave.wp5.feedbackhandler.model.db.DbApplication;
import eu.cloudwave.wp5.feedbackhandler.model.db.impl.DbApplicationImpl;

/**
 * Implementation of {@link ApplicationRepositoryCustom}. It is named according to the naming conventions:
 * <ul>
 * <li>http://www.javabeat.net/spring-data-custom-repository/</li>
 * <li>http://stackoverflow.com/a/20789040/3721915</li>
 * </ul>
 */
public class ApplicationRepositoryImpl implements ApplicationRepositoryCustom {

  private static final String APPLICATION_ID = "applicationId";

  @Autowired
  private MongoTemplate mongoTemplate;

  /**
   * {@inheritDoc}
   */
  @Override
  public DbApplication findOne(final String applicationId) {
    final Criteria criteria = new Criteria(APPLICATION_ID).is(applicationId);
    return mongoTemplate.findOne(new Query(criteria), DbApplicationImpl.class);
  }

}
