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

import com.google.inject.Inject;

import eu.cloudwave.wp5.common.constants.AggregationInterval;
import eu.cloudwave.wp5.common.dto.ApplicationDto;
import eu.cloudwave.wp5.common.dto.costs.AggregatedIncomingRequestsDto;
import eu.cloudwave.wp5.common.dto.costs.AggregatedMicroserviceRequestsDto;
import eu.cloudwave.wp5.common.dto.costs.InitialInvocationCheckDto;
import eu.cloudwave.wp5.feedback.eclipse.base.core.preferences.FeedbackPreferences;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.FeedbackProject;

/**
 * Implementation of {@link FeedbackHandlerEclipseClient}.
 */
public class FeedbackHandlerEclipseClientImpl implements FeedbackHandlerEclipseClient {

  @Inject
  private FeedbackHandlerClientFactory feedbackHandlerClientFactory;

  private FeedbackHandlerClient feedbackHandlerClient;

  /**
   * Private Constructor
   * 
   * @return
   */
  private FeedbackHandlerClient feedbackHandlerClient() {
    if (feedbackHandlerClient == null) {
      final String rootUrl = FeedbackPreferences.getString(FeedbackPreferences.FEEDBACK_HANDLER__URL);
      feedbackHandlerClient = feedbackHandlerClientFactory.create(rootUrl);
    }
    return feedbackHandlerClient;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AggregatedMicroserviceRequestsDto[] all(final FeedbackProject project, AggregationInterval aggregationInterval, String timeRangeFrom, String timeRangeTo) {
    return feedbackHandlerClient().allRequests(project.getAccessToken(), project.getApplicationId(), aggregationInterval, timeRangeFrom, timeRangeTo);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AggregatedMicroserviceRequestsDto[] requestsByCallee(FeedbackProject project, AggregationInterval aggregationInterval, String timeRangeFrom, String timeRangeTo) {
    return feedbackHandlerClient().requestsByCallee(project.getAccessToken(), project.getApplicationId(), aggregationInterval, timeRangeFrom, timeRangeTo);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AggregatedMicroserviceRequestsDto requestsByCalleeOverall(FeedbackProject project, AggregationInterval aggregationInterval, String timeRangeFrom, String timeRangeTo) {
    return feedbackHandlerClient().requestsByCalleeOverall(project.getAccessToken(), project.getApplicationId(), aggregationInterval, timeRangeFrom, timeRangeTo);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AggregatedMicroserviceRequestsDto[] requestsByCaller(FeedbackProject project, AggregationInterval aggregationInterval, String timeRangeFrom, String timeRangeTo) {
    return feedbackHandlerClient().requestsByCaller(project.getAccessToken(), project.getApplicationId(), aggregationInterval, timeRangeFrom, timeRangeTo);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AggregatedIncomingRequestsDto[] allIncomingRequests(FeedbackProject project, AggregationInterval aggregationInterval, String timeRangeFrom, String timeRangeTo) {
    return feedbackHandlerClient().allIncomingRequests(project.getAccessToken(), project.getApplicationId(), aggregationInterval, timeRangeFrom, timeRangeTo);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AggregatedIncomingRequestsDto[] incomingRequestsByIdentifier(FeedbackProject project, AggregationInterval aggregationInterval, String timeRangeFrom, String timeRangeTo) {
    return feedbackHandlerClient().incomingRequestsByIdentifier(project.getAccessToken(), project.getApplicationId(), aggregationInterval, timeRangeFrom, timeRangeTo);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AggregatedIncomingRequestsDto[] incomingRequestsByIdentifier(
      FeedbackProject project,
      String requestedApplicationId,
      AggregationInterval aggregationInterval,
      String timeRangeFrom,
      String timeRangeTo) {
    return feedbackHandlerClient().incomingRequestsByIdentifier(project.getAccessToken(), project.getApplicationId(), requestedApplicationId, aggregationInterval, timeRangeFrom, timeRangeTo);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AggregatedIncomingRequestsDto overallIncomingRequestsByIdentifier(
      FeedbackProject project,
      String requestedApplicationId,
      AggregationInterval aggregationInterval,
      String timeRangeFrom,
      String timeRangeTo) {
    return feedbackHandlerClient().overallIncomingRequestsByIdentifier(project.getAccessToken(), project.getApplicationId(), requestedApplicationId, aggregationInterval, timeRangeFrom, timeRangeTo);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AggregatedIncomingRequestsDto overallIncomingRequestsByIdentifier(FeedbackProject project, AggregationInterval aggregationInterval, String timeRangeFrom, String timeRangeTo) {
    return feedbackHandlerClient().overallIncomingRequestsByIdentifier(project.getAccessToken(), project.getApplicationId(), aggregationInterval, timeRangeFrom, timeRangeTo);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Boolean isNewlyInvoked(FeedbackProject project, InitialInvocationCheckDto invocation) {
    return feedbackHandlerClient().isNewlyInvoked(project.getAccessToken(), project.getApplicationId(), invocation);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ApplicationDto currentApplication(FeedbackProject project) {
    return feedbackHandlerClient().currentApplication(project.getAccessToken(), project.getApplicationId());
  }

  @Override
  public ApplicationDto application(FeedbackProject project, String requestedApplicationId) {
    return feedbackHandlerClient().application(project.getAccessToken(), project.getApplicationId(), requestedApplicationId);
  }
}
