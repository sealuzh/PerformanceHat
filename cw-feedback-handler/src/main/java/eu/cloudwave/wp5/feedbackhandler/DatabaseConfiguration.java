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
package eu.cloudwave.wp5.feedbackhandler;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

/**
 * Configurations for the MongoDB.
 */
@Configuration
@EnableMongoRepositories
public class DatabaseConfiguration extends AbstractMongoConfiguration {

  private static final String DB_NAME = "feedback-handler";
  private static final String REPO_BASE_PACKAGE = "eu.cloudwave.wp5.feedbackhandler.repositories";

  /**
   * {@inheritDoc}
   */
  @Override
  protected String getDatabaseName() {
    return DB_NAME;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Mongo mongo() throws Exception {
    return new MongoClient();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String getMappingBasePackage() {
    return REPO_BASE_PACKAGE;
  }

}
