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
package eu.cloudwave.wp5.feedback.eclipse.costs.core.feedbackhandler;

import eu.cloudwave.wp5.common.dto.AggregatedMicroserviceRequestsDto;

/**
 * Provides methods to talk to the Feedback Handler Server.
 */
public interface FeedbackHandlerClient {

  /**
   * Get all microservice requests
   * 
   * @param accessToken
   *          the access token
   * @param applicationId
   *          the application ID
   * @return An array of {@link AggregatedMicroserviceRequestsDto} containing all requests
   */
  public AggregatedMicroserviceRequestsDto[] allRequests(final String accessToken, final String applicationId);

  /**
   * Get all microservice requests to the given callee (application id)
   * 
   * @param accessToken
   *          the access token
   * @param applicationId
   *          the application ID which will be used to identify the callee
   * @return An array of {@link AggregatedMicroserviceRequestsDto}
   */
  public AggregatedMicroserviceRequestsDto[] requestsByCallee(final String accessToken, final String applicationId);

  /**
   * Get all microservice requests from the given caller (application id)
   * 
   * @param accessToken
   *          the access token
   * @param applicationId
   *          the application ID which will be used to identify the caller
   * @return An array of {@link AggregatedMicroserviceRequestsDto}
   */
  public AggregatedMicroserviceRequestsDto[] requestsByCaller(final String accessToken, final String applicationId);
}
