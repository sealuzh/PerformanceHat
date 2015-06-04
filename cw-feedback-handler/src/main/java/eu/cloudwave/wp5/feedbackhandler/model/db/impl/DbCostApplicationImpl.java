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
package eu.cloudwave.wp5.feedbackhandler.model.db.impl;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import eu.cloudwave.wp5.feedbackhandler.constants.DbTableNames;
import eu.cloudwave.wp5.feedbackhandler.model.db.DbApplication;
import eu.cloudwave.wp5.feedbackhandler.model.db.DbCostApplication;

/**
 * Implementation of {@link DbApplication}.
 */
@Document(collection = DbTableNames.APPLICATIONS)
public class DbCostApplicationImpl implements DbCostApplication {

  @Id
  private ObjectId id;

  private String applicationId;

  private String accessToken;

  private Integer instances;

  private Double pricePerInstanceInUSD;

  private Double maxRequestsPerInstancePerSecond;

  public DbCostApplicationImpl(String applicationId, String accessToken, Integer instances, Double pricePerInstanceInUSD, Double maxRequestsPerInstancePerSecond) {
    this.applicationId = applicationId;
    this.accessToken = accessToken;
    this.instances = instances;
    this.pricePerInstanceInUSD = pricePerInstanceInUSD;
    this.maxRequestsPerInstancePerSecond = maxRequestsPerInstancePerSecond;
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

  public Integer getInstances() {
    return instances;
  }

  public Double getPricePerInstanceInUSD() {
    return pricePerInstanceInUSD;
  }

  public Double getMaxRequestsPerInstancePerSecond() {
    return maxRequestsPerInstancePerSecond;
  }

  public void setInstances(Integer instances) {
    this.instances = instances;
  }

  public void setPricePerInstanceInUSD(Double pricePerInstanceInUSD) {
    this.pricePerInstanceInUSD = pricePerInstanceInUSD;
  }

  public void setMaxRequestsPerInstancePerSecond(Double maxRequestsPerInstancePerSecond) {
    this.maxRequestsPerInstancePerSecond = maxRequestsPerInstancePerSecond;
  }
}
