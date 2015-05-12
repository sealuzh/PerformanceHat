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
import org.springframework.data.mongodb.core.mapping.Document;

import eu.cloudwave.wp5.feedbackhandler.constants.DbTableNames;
import eu.cloudwave.wp5.feedbackhandler.model.db.DbApplication;

/**
 * Implementation of {@link DbApplication}.
 */
@Document(collection = DbTableNames.APPLICATIONS)
public class DbApplicationImpl implements DbApplication {

  @Id
  private ObjectId id;

  private String applicationId;
  private String accessToken;

  public DbApplicationImpl(final String applicationId, final String accessToken) {
    this.applicationId = applicationId;
    this.accessToken = accessToken;
  }

  @Override
  public ObjectId getId() {
    return id;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getApplicationId() {
    return applicationId;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getAccessToken() {
    return accessToken;
  }

}
