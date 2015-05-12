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
import eu.cloudwave.wp5.common.dto.AggregatedProcedureMetricsDto;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.FeedbackProject;

/**
 * An eclipse-specific client to talk to the feedback handler. Delegates the actual communication with the feedback
 * handler to a {@link FeedbackHandlerClient} and provides eclipse-specific functionality around it.
 */
public interface FeedbackHandlerEclipseClient {

  /**
   * Get all microservice requests
   * 
   * @param project
   *          the project
   * @return An array of {@link AggregatedProcedureMetricsDto}
   */
  public AggregatedMicroserviceRequestsDto[] all(FeedbackProject project);

  /**
   * Get all microservice requests to the given callee (application id)
   * 
   * @param project
   *          the project
   * @return An array of {@link AggregatedMicroserviceRequestsDto}
   */
  public AggregatedMicroserviceRequestsDto[] requestsByCallee(FeedbackProject project);

  /**
   * Get all microservice requests from the given caller (application id)
   * 
   * @param project
   *          the project
   * @return An array of {@link AggregatedMicroserviceRequestsDto}
   */
  public AggregatedMicroserviceRequestsDto[] requestsByCaller(FeedbackProject project);
}
