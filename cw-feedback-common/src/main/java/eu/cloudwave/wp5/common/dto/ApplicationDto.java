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
package eu.cloudwave.wp5.common.dto;

/**
 * A DTO to transmit application data.
 */
public class ApplicationDto {

  private String applicationId;

  private String accessToken;

  private Integer instances;

  private Double pricePerInstanceInUSD;

  private Double maxRequestsPerInstancePerSecond;

  // default constructor is required for jackson deserialization
  public ApplicationDto() {}

  public ApplicationDto(final String applicationId, final String accessToken) {
    this.applicationId = applicationId;
    this.accessToken = accessToken;
  }

  public ApplicationDto(String applicationId, String accessToken, Integer instances, Double pricePerInstanceInUSD, Double maxRequestsPerInstancePerSecond) {
    this.applicationId = applicationId;
    this.accessToken = accessToken;
    this.instances = instances;
    this.pricePerInstanceInUSD = pricePerInstanceInUSD;
    this.maxRequestsPerInstancePerSecond = maxRequestsPerInstancePerSecond;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public String getApplicationId() {
    return applicationId;
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
}
