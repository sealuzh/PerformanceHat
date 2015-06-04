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
package eu.cloudwave.wp5.feedback.eclipse.performance.core.feedbackhandler;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.cloudwave.wp5.common.constants.Headers;
import eu.cloudwave.wp5.common.constants.Params;
import eu.cloudwave.wp5.common.constants.Urls;
import eu.cloudwave.wp5.common.dto.AggregatedProcedureMetricsDto;
import eu.cloudwave.wp5.common.dto.model.ProcedureExecutionMetricDto;
import eu.cloudwave.wp5.common.dto.newrelic.MethodInfoSummarized;
import eu.cloudwave.wp5.common.rest.RestRequestHeader;
import eu.cloudwave.wp5.common.util.Joiners;
import eu.cloudwave.wp5.feedback.eclipse.base.infrastructure.rest.RestClient;

/**
 * Implementation of {@link FeedbackHandlerClient}.
 * 
 * This class makes use of the generic {@link RestClient} and provides methods on top of it to access the Feedback
 * Handler Server. THe URL for this server is loaded from the plug-in preferences store.
 * */
public class FeedbackHandlerClientImpl implements FeedbackHandlerClient {

  @Inject
  private RestClient restClient;

  private final String rootUrl;

  @Inject
  public FeedbackHandlerClientImpl(final @Assisted String rootUrl) {
    this.rootUrl = rootUrl;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public MethodInfoSummarized newRelicSummarized(final String apiKey, final String applicationId, final String className, final String procedureName) {
    final String url = url(Urls.NEW_RELIC__SUMMARIZE);
    final Map<String, String> urlVariables = ImmutableMap.of(Params.APPLICATION_ID, applicationId, Params.CLASS_NAME, className, Params.PROCEDURE_NAME, procedureName);
    return restClient.get(url, urlVariables, MethodInfoSummarized.class, RestRequestHeader.of(Headers.X_API_KEY, apiKey));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AggregatedProcedureMetricsDto[] hotspots(final String accessToken, final String applicationId, final Double threshold) {
    final String url = url(Urls.ANALYIS__HOTSPOTS);
    final Map<String, String> urlVariables = ImmutableMap.of(Params.THRESHOLD, threshold.toString());
    return restClient.get(url, urlVariables, AggregatedProcedureMetricsDto[].class, RestRequestHeader.of(Headers.APPLICATION_ID, applicationId),
        RestRequestHeader.of(Headers.ACCESS_TOKEN, accessToken));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ProcedureExecutionMetricDto[] procedure(final String accessToken, final String applicationId, final String className, final String procedureName, final String[] arguments) {
    final String url = url(Urls.ANALYIS__PROCEDURE);
    final Map<String, String> urlVariables = ImmutableMap.of(Params.CLASS_NAME, className, Params.PROCEDURE_NAME, procedureName, Params.ARGUMENTS, Joiners.onComma(arguments));
    return restClient.get(url, urlVariables, ProcedureExecutionMetricDto[].class, RestRequestHeader.of(Headers.APPLICATION_ID, applicationId), RestRequestHeader.of(Headers.ACCESS_TOKEN, accessToken));
  }

  @Override
  public Double avgExecTime(final String accessToken, final String applicationId, final String className, final String procedureName, final String[] arguments) {
    final String url = url(Urls.ANALYIS__AVG_EXEC_TIME);
    final Map<String, String> urlVariables = ImmutableMap.of(Params.CLASS_NAME, className, Params.PROCEDURE_NAME, procedureName, Params.ARGUMENTS, Joiners.onComma(arguments));
    return restClient.get(url, urlVariables, Double.class, RestRequestHeader.of(Headers.APPLICATION_ID, applicationId), RestRequestHeader.of(Headers.ACCESS_TOKEN, accessToken));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Double collectionSize(final String accessToken, final String applicationId, final String className, final String procedureName, final String[] arguments, final String number) {
    final String url = url(Urls.ANALYIS__COLLECTION_SIZE);
    final Map<String, String> urlVariables = ImmutableMap.of(Params.CLASS_NAME, className, Params.PROCEDURE_NAME, procedureName, Params.ARGUMENTS, Joiners.onComma(arguments), Params.NUMBER, number);
    return restClient.get(url, urlVariables, Double.class, RestRequestHeader.of(Headers.APPLICATION_ID, applicationId), RestRequestHeader.of(Headers.ACCESS_TOKEN, accessToken));
  }

  private String url(final String urlFragment) {
    return Urls.concatenate(rootUrl, urlFragment);
  }
}
