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
package eu.cloudwave.wp5.feedback.eclipse.tests.mocks;

import eu.cloudwave.wp5.common.dto.AggregatedProcedureMetricsDto;
import eu.cloudwave.wp5.common.dto.model.ProcedureExecutionMetricDto;
import eu.cloudwave.wp5.common.dto.newrelic.MethodInfoSummarized;
import eu.cloudwave.wp5.common.error.ErrorType;
import eu.cloudwave.wp5.common.error.RequestException;
import eu.cloudwave.wp5.feedback.eclipse.base.core.FeedbackProperties;
import eu.cloudwave.wp5.feedback.eclipse.base.core.preferences.FeedbackPreferences;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.FeedbackProject;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.feedbackhandler.FeedbackHandlerEclipseClient;
import eu.cloudwave.wp5.feedback.eclipse.tests.stubs.Stubs;

/**
 * A mock for {@link FeedbackHandlerEclipseClient}'s that is used in testing. Simply returns stubs instead of connecting
 * to a Feedback Handler instance.
 */
public class FeedbackHandlerEclipseClientMock implements FeedbackHandlerEclipseClient {

  public static final String INVALID_VALUE = "<invalid-value>";

  private static final String INVALID_MSG_PATTERN = "Invalid %s.";
  private static final String INVALID_URL_MSG = String.format(INVALID_MSG_PATTERN, "URL");
  private static final String INVALID_APPLICATION_ID_MSG = String.format(INVALID_MSG_PATTERN, "Application-ID");
  private static final String WRONG_ACCESS_TOKEN_MSG = String.format(INVALID_MSG_PATTERN, "Access-Token");
  private static final String EMPTY = "";

  /**
   * {@inheritDoc}
   */
  @Override
  public MethodInfoSummarized newRelicSummarized(final FeedbackProject project, final String className, final String methodName) {
    throwErrors(project);
    return Stubs.METHOD_INFO_SUMMARIZED;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AggregatedProcedureMetricsDto[] hotspots(final FeedbackProject project) {
    throwErrors(project);
    return Stubs.HOTSPOTS;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ProcedureExecutionMetricDto[] procedure(final FeedbackProject project, final String className, final String methodName, final String arguments[]) {
    return new ProcedureExecutionMetricDto[0];
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Double avgExecTime(final FeedbackProject project, final String className, final String procedureName, final String[] arguments) {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Double collectionSize(final FeedbackProject project, final String className, final String procedureName, final String[] arguments, final String number) {
    return null;
  }

  private void throwErrors(final FeedbackProject project) {
    final String rootUrl = FeedbackPreferences.getString(FeedbackPreferences.FEEDBACK_HANDLER__URL);
    if (rootUrl.equals(INVALID_VALUE)) {
      throw new RequestException(ErrorType.FEEDBACK_HANDLER_NOT_AVAILABLE, INVALID_URL_MSG);
    }
    final String applicationId = project.getFeedbackProperties().get(FeedbackProperties.FEEDBACK_HANDLER__APPLICATION_ID, EMPTY);
    if (applicationId.equals(INVALID_VALUE)) {
      throw new RequestException(ErrorType.INVALID_APPLICATION_ID, INVALID_APPLICATION_ID_MSG);
    }
    final String apiKey = project.getFeedbackProperties().get(FeedbackProperties.FEEDBACK_HANDLER__ACCESS_TOKEN, EMPTY);
    if (apiKey.equals(INVALID_VALUE)) {
      throw new RequestException(ErrorType.WRONG_ACCESS_TOKEN, WRONG_ACCESS_TOKEN_MSG);
    }
  }

}
