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

import com.google.inject.Inject;

import eu.cloudwave.wp5.common.dto.AggregatedMicroserviceRequestsDto;
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
   * {@inheritDoc}
   */
  @Override
  public AggregatedMicroserviceRequestsDto[] all(final FeedbackProject project) {
    return feedbackHandlerClient().allRequests(project.getAccessToken(), project.getApplicationId());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AggregatedMicroserviceRequestsDto[] requestsByCallee(FeedbackProject project) {
    return feedbackHandlerClient().requestsByCallee(project.getAccessToken(), project.getApplicationId());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AggregatedMicroserviceRequestsDto[] requestsByCaller(FeedbackProject project) {
    return feedbackHandlerClient().requestsByCaller(project.getAccessToken(), project.getApplicationId());
  }

  private FeedbackHandlerClient feedbackHandlerClient() {
    if (feedbackHandlerClient == null) {
      final String rootUrl = FeedbackPreferences.getString(FeedbackPreferences.FEEDBACK_HANDLER__URL);
      feedbackHandlerClient = feedbackHandlerClientFactory.create(rootUrl);
    }
    return feedbackHandlerClient;
  }
}
