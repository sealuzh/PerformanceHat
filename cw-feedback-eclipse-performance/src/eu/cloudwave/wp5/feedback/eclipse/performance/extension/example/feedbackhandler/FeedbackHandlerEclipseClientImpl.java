/*******************************************************************************
 * Copyright 2015 Software Evolution and Architecture Lab, University of Zurich
 * 
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
package eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.feedbackhandler;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;

import com.google.inject.Inject;

import eu.cloudwave.wp5.common.dto.AggregatedProcedureMetricsDto;
import eu.cloudwave.wp5.common.dto.model.ProcedureExecutionMetricDto;
import eu.cloudwave.wp5.common.dto.newrelic.MethodInfoSummarized;
import eu.cloudwave.wp5.feedback.eclipse.base.core.preferences.FeedbackPreferences;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.FeedbackProject;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.properties.PerformanceFeedbackProperties;
import eu.cloudwave.wp5.feedback.eclipse.performance.infrastructure.config.PerformanceConfigs;

/**
 * Implementation of {@link FeedbackHandlerEclipseClient}.
 */
public class FeedbackHandlerEclipseClientImpl implements FeedbackHandlerEclipseClient {

  private static final String EMPTY = "";

  @Inject
  private FeedbackHandlerClientFactory feedbackHandlerClientFactory;

  private FeedbackHandlerClient feedbackHandlerClient;

  /**
   * {@inheritDoc}
   */
  @Override
  public MethodInfoSummarized newRelicSummarized(final FeedbackProject project, final String className, final String procedureName) {
    final String apiKey = property(project, PerformanceFeedbackProperties.NEW_RELIC__API_KEY);
    final String applicationId = property(project, PerformanceFeedbackProperties.NEW_RELIC__APPLICATION_ID);
    return feedbackHandlerClient().newRelicSummarized(apiKey, applicationId, className, procedureName);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AggregatedProcedureMetricsDto[] hotspots(final FeedbackProject project) {
    final Double threshold = project.getFeedbackProperties().getDouble(PerformanceFeedbackProperties.TRESHOLD__HOTSPOTS, PerformanceConfigs.DEFAULT_THRESHOLD_HOTSPOTS);
    return feedbackHandlerClient().hotspots(project.getAccessToken(), project.getApplicationId(), threshold);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ProcedureExecutionMetricDto[] procedure(final FeedbackProject project, final String className, final String procedureName, final String[] arguments) {
    return feedbackHandlerClient().procedure(project.getAccessToken(), project.getApplicationId(), className, procedureName, arguments);
  }

  @Override
  public Double avgExecTime(final FeedbackProject project, final String className, final String procedureName, final String[] arguments) {
    return feedbackHandlerClient().avgExecTime(project.getAccessToken(), project.getApplicationId(), className, procedureName, arguments);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Double collectionSize(final FeedbackProject project, final String className, final String procedureName, final String[] arguments, final String number) {
    return feedbackHandlerClient().collectionSize(project.getAccessToken(), project.getApplicationId(), className, procedureName, arguments, number);
  }

  private FeedbackHandlerClient feedbackHandlerClient() {
    if (feedbackHandlerClient == null) {
      final String rootUrl = FeedbackPreferences.getString(FeedbackPreferences.FEEDBACK_HANDLER__URL);
      feedbackHandlerClient = feedbackHandlerClientFactory.create(rootUrl);
    }
    return feedbackHandlerClient;
  }

  private String property(final FeedbackProject project, final String key) {
    final IEclipsePreferences properties = project.getFeedbackProperties();
    return properties.get(key, EMPTY);
  }
}
