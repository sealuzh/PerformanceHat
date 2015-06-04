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
package eu.cloudwave.wp5.feedback.eclipse.costs.core.feedbackhandler;

import eu.cloudwave.wp5.common.constants.AggregationInterval;
import eu.cloudwave.wp5.common.dto.AggregatedProcedureMetricsDto;
import eu.cloudwave.wp5.common.dto.ApplicationDto;
import eu.cloudwave.wp5.common.dto.costs.AggregatedIncomingRequestsDto;
import eu.cloudwave.wp5.common.dto.costs.AggregatedMicroserviceRequestsDto;
import eu.cloudwave.wp5.common.dto.costs.InitialInvocationCheckDto;
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
  public AggregatedMicroserviceRequestsDto[] all(FeedbackProject project, AggregationInterval aggregationInterval, String timeRangeFrom, String timeRangeTo);

  /**
   * Get statistics about all requests to the given callee (application id) but grouped by caller
   * 
   * @param project
   *          the project
   * @return An array of {@link AggregatedMicroserviceRequestsDto}
   */
  public AggregatedMicroserviceRequestsDto[] requestsByCallee(FeedbackProject project, AggregationInterval aggregationInterval, String timeRangeFrom, String timeRangeTo);

  /**
   * Get overall statistics about all requests to the given callee (application id)
   * 
   * @param project
   *          the project
   * @return An {@link AggregatedMicroserviceRequestsDto} object
   */
  public AggregatedMicroserviceRequestsDto requestsByCalleeOverall(FeedbackProject project, AggregationInterval aggregationInterval, String timeRangeFrom, String timeRangeTo);

  /**
   * Get all microservice requests from the given caller (application id)
   * 
   * @param project
   *          the project
   * @return An array of {@link AggregatedMicroserviceRequestsDto}
   */
  public AggregatedMicroserviceRequestsDto[] requestsByCaller(FeedbackProject project, AggregationInterval aggregationInterval, String timeRangeFrom, String timeRangeTo);

  /**
   * All incoming microservice requests aggregated by given time interval
   * 
   * @param project
   *          the project
   * @param aggregationInterval
   * @param timeRangeFrom
   * @param timeRangeTo
   * 
   * @return an array of aggregated requests
   */
  public AggregatedIncomingRequestsDto[] allIncomingRequests(FeedbackProject project, final AggregationInterval aggregationInterval, final String timeRangeFrom, final String timeRangeTo);

  /**
   * Incoming microservice requests to the given application aggregated by given time interval. Separate statistics for
   * each service identifier and each service method.
   * 
   * @param project
   *          the project
   * @param aggregationInterval
   * @param timeRangeFrom
   * @param timeRangeTo
   * 
   * @return an array of aggregated requests
   */
  public AggregatedIncomingRequestsDto[] incomingRequestsByIdentifier(FeedbackProject project, final AggregationInterval aggregationInterval, final String timeRangeFrom, final String timeRangeTo);

  /**
   * Incoming microservice requests to the given application aggregated by given time interval without grouping by
   * service method. No separate statistics for each service method, statistics are only grouped by service identifier.
   * 
   * @param project
   *          the project
   * @param aggregationInterval
   * @param timeRangeFrom
   * @param timeRangeTo
   * 
   * @return an array of aggregated requests
   */
  public AggregatedIncomingRequestsDto incomingRequestsByIdentifierOverall(FeedbackProject project, final AggregationInterval aggregationInterval, final String timeRangeFrom, final String timeRangeTo);

  /**
   * Check if the given method has ever been called within the given caller method/class
   * 
   * @param project
   *          the project
   * @param invocation
   *          names of invoked method/class and caller method/class
   * @return boolean true means that the invoked method has never been called by the given caller
   */
  public Boolean isNewlyInvoked(FeedbackProject project, final InitialInvocationCheckDto invocation);

  /**
   * Get data about the current application like the number of instances, price per instance and so on
   * 
   * @param project
   *          the project
   * @return {@link ApplicationDto}
   */
  public ApplicationDto currentApplication(FeedbackProject project);

  /**
   * Get data about another application like the number of instances, price per instance and so on
   * 
   * @param project
   *          the project
   * @param requestedApplicationId
   *          applicationId of the reuqested app
   * @return {@link ApplicationDto}
   */
  public ApplicationDto application(FeedbackProject project, final String requestedApplicationId);
}
