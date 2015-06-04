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
package eu.cloudwave.wp5.feedbackhandler.model.db;

import org.springframework.data.mongodb.core.mapping.Document;

import eu.cloudwave.wp5.feedbackhandler.constants.DbTableNames;

/**
 * Represents applications that are registered to be used with the feedback handler.
 */
@Document(collection = DbTableNames.APPLICATIONS)
public interface DbCostApplication extends DbApplication {

  /**
   * Returns the id of the application.
   * 
   * @return the id of the application
   */
  public String getApplicationId();

  /**
   * Returns the authentication token of this application.
   * 
   * @return the authentication token
   */
  public String getAccessToken();

  /**
   * Get number of currently deployed instances of this application
   * 
   * @return
   */
  public Integer getInstances();

  /**
   * Get price per instance per hour in USD
   * 
   * @return
   */
  public Double getPricePerInstanceInUSD();

  /**
   * Get maximal number of requests per second
   * 
   * @return
   */
  public Double getMaxRequestsPerInstancePerSecond();

  /**
   * Set number of currently deployed instances of this application
   * 
   * @param instances
   */
  public void setInstances(Integer instances);

  /**
   * Set price per instance per hour in USD
   * 
   * @param pricePerInstanceInUSD
   */
  public void setPricePerInstanceInUSD(Double pricePerInstanceInUSD);

  /**
   * Set maximal number of requests per second
   * 
   * @param maxRequestsPerInstancePerSecond
   */
  public void setMaxRequestsPerInstancePerSecond(Double maxRequestsPerInstancePerSecond);
}
