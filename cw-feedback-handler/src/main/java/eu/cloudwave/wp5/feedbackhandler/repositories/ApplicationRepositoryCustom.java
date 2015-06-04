/*******************************************************************************
 * Copyright 2015 Software Evolution and Architecture Lab, University of Zurich
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 ******************************************************************************/
package eu.cloudwave.wp5.feedbackhandler.repositories;

import java.util.List;

import eu.cloudwave.wp5.feedbackhandler.model.db.DbApplication;
import eu.cloudwave.wp5.feedbackhandler.model.db.DbCostApplication;

/**
 * This class extends the default spring repository with custom methods. It has to be named according to the naming
 * convention (see http://www.javabeat.net/spring-data-custom-repository/).
 */
public interface ApplicationRepositoryCustom {

  /**
   * Finds the application with the given id.
   * 
   * @param applicationId
   *          the application id
   * @return the application with the given id
   */
  public DbApplication findOne(String applicationId);

  /**
   * Finds the cost application with the given id.
   * 
   * @param applicationId
   *          the application id
   * @return the application with the given id
   */
  public DbCostApplication findOneCostApplication(final String applicationId);

  /**
   * Finds all cost applications with information about number of instances, price per instance and max number of
   * requests per instance
   * 
   * @return list of all applications
   */
  public List<? extends DbCostApplication> findAllCostApplications();
}
