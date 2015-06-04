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

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.cloudwave.wp5.common.constants.AggregationInterval;
import eu.cloudwave.wp5.common.constants.Headers;
import eu.cloudwave.wp5.common.constants.Urls;
import eu.cloudwave.wp5.common.dto.ApplicationDto;
import eu.cloudwave.wp5.common.dto.costs.AggregatedIncomingRequestsDto;
import eu.cloudwave.wp5.common.dto.costs.AggregatedMicroserviceRequestsDto;
import eu.cloudwave.wp5.common.dto.costs.InitialInvocationCheckDto;
import eu.cloudwave.wp5.common.rest.RestRequestHeader;
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
  public AggregatedMicroserviceRequestsDto[] allRequests(
      final String accessToken,
      final String applicationId,
      final AggregationInterval aggregationInterval,
      final String timeRangeFrom,
      final String timeRangeTo) {
    final String url = url(Urls.COST__ALL);
    return performGETRequest(url, accessToken, applicationId, aggregationInterval, timeRangeFrom, timeRangeTo);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AggregatedMicroserviceRequestsDto[] requestsByCallee(
      String accessToken,
      String applicationId,
      final AggregationInterval aggregationInterval,
      final String timeRangeFrom,
      final String timeRangeTo) {
    final String url = url(Urls.COST__FILTER__CALLEE);
    return performGETRequest(url, accessToken, applicationId, aggregationInterval, timeRangeFrom, timeRangeTo);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AggregatedMicroserviceRequestsDto requestsByCalleeOverall(
      String accessToken,
      String applicationId,
      final AggregationInterval aggregationInterval,
      final String timeRangeFrom,
      final String timeRangeTo) {
    final String url = url(Urls.COST__FILTER__CALLEE__OVERALL);
    final Map<String, String> urlVariables = ImmutableMap.of();
    RestRequestHeader headerId = RestRequestHeader.of(Headers.APPLICATION_ID, applicationId);
    RestRequestHeader headerToken = RestRequestHeader.of(Headers.ACCESS_TOKEN, accessToken);
    RestRequestHeader headerInterval = RestRequestHeader.of(Headers.AGGREGATION_INTERVAL, aggregationInterval.toString());
    RestRequestHeader headerTimeFrom = RestRequestHeader.of(Headers.TIME_RANGE_FROM, timeRangeFrom);
    RestRequestHeader headerTimeTo = RestRequestHeader.of(Headers.TIME_RANGE_TO, timeRangeTo);

    return restClient.get(url, urlVariables, AggregatedMicroserviceRequestsDto.class, headerId, headerToken, headerInterval, headerTimeFrom, headerTimeTo);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AggregatedMicroserviceRequestsDto[] requestsByCaller(
      String accessToken,
      String applicationId,
      final AggregationInterval aggregationInterval,
      final String timeRangeFrom,
      final String timeRangeTo) {
    final String url = url(Urls.COST__FILTER__CALLEE);
    return performGETRequest(url, accessToken, applicationId, aggregationInterval, timeRangeFrom, timeRangeTo);
  }

  /**
   * Cost Controller methods that return {@link AggregatedMicroserviceRequestsDto} objects require the same headers but
   * have a different url. This helper class performs the GET request and returns the list of results.
   * 
   * @param url
   * @param accessToken
   * @param applicationId
   * @param timeRangeFrom
   * @param timeRangeTo
   * 
   * @return An array of {@link AggregatedMicroserviceRequestsDto}
   */
  private AggregatedMicroserviceRequestsDto[] performGETRequest(
      final String url,
      String accessToken,
      String applicationId,
      final AggregationInterval aggregationInterval,
      final String timeRangeFrom,
      final String timeRangeTo) {
    final Map<String, String> urlVariables = ImmutableMap.of();

    RestRequestHeader headerId = RestRequestHeader.of(Headers.APPLICATION_ID, applicationId);
    RestRequestHeader headerToken = RestRequestHeader.of(Headers.ACCESS_TOKEN, accessToken);
    RestRequestHeader headerInterval = RestRequestHeader.of(Headers.AGGREGATION_INTERVAL, aggregationInterval.toString());
    RestRequestHeader headerTimeFrom = RestRequestHeader.of(Headers.TIME_RANGE_FROM, timeRangeFrom);
    RestRequestHeader headerTimeTo = RestRequestHeader.of(Headers.TIME_RANGE_TO, timeRangeTo);

    return restClient.get(url, urlVariables, AggregatedMicroserviceRequestsDto[].class, headerId, headerToken, headerInterval, headerTimeFrom, headerTimeTo);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AggregatedIncomingRequestsDto[] allIncomingRequests(String accessToken, String applicationId, AggregationInterval aggregationInterval, String timeRangeFrom, String timeRangeTo) {
    final String url = url(Urls.COST__INCOMING__ALL);
    return getIncomingRequest(url, accessToken, applicationId, aggregationInterval, timeRangeFrom, timeRangeTo);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AggregatedIncomingRequestsDto[] incomingRequestsByIdentifier(String accessToken, String applicationId, AggregationInterval aggregationInterval, String timeRangeFrom, String timeRangeTo) {
    final String url = url(Urls.COST__INCOMING__FILTER__IDENTIFIER);
    return getIncomingRequest(url, accessToken, applicationId, aggregationInterval, timeRangeFrom, timeRangeTo);
  }

  /**
   * Cost Controller methods that return {@link AggregatedIncomingRequestsDto} objects require the same headers but have
   * a different url. This helper class performs the GET request and returns the list of results.
   * 
   * @param url
   * @param accessToken
   * @param applicationId
   * @param timeRangeFrom
   * @param timeRangeTo
   * @return An array of {@link AggregatedMicroserviceRequestsDto}
   */
  private AggregatedIncomingRequestsDto[] getIncomingRequest(
      final String url,
      String accessToken,
      String applicationId,
      final AggregationInterval aggregationInterval,
      final String timeRangeFrom,
      final String timeRangeTo) {
    final Map<String, String> urlVariables = ImmutableMap.of();

    RestRequestHeader headerId = RestRequestHeader.of(Headers.APPLICATION_ID, applicationId);
    RestRequestHeader headerToken = RestRequestHeader.of(Headers.ACCESS_TOKEN, accessToken);
    RestRequestHeader headerInterval = RestRequestHeader.of(Headers.AGGREGATION_INTERVAL, aggregationInterval.toString());
    RestRequestHeader headerTimeFrom = RestRequestHeader.of(Headers.TIME_RANGE_FROM, timeRangeFrom);
    RestRequestHeader headerTimeTo = RestRequestHeader.of(Headers.TIME_RANGE_TO, timeRangeTo);

    return restClient.get(url, urlVariables, AggregatedIncomingRequestsDto[].class, headerId, headerToken, headerInterval, headerTimeFrom, headerTimeTo);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AggregatedIncomingRequestsDto incomingRequestsByIdentifierOverall(String accessToken, String applicationId, AggregationInterval aggregationInterval, String timeRangeFrom, String timeRangeTo) {
    final String url = url(Urls.COST__INCOMING__FILTER__IDENTIFIER__OVERALL);
    final Map<String, String> urlVariables = ImmutableMap.of();

    RestRequestHeader headerId = RestRequestHeader.of(Headers.APPLICATION_ID, applicationId);
    RestRequestHeader headerToken = RestRequestHeader.of(Headers.ACCESS_TOKEN, accessToken);
    RestRequestHeader headerInterval = RestRequestHeader.of(Headers.AGGREGATION_INTERVAL, aggregationInterval.toString());
    RestRequestHeader headerTimeFrom = RestRequestHeader.of(Headers.TIME_RANGE_FROM, timeRangeFrom);
    RestRequestHeader headerTimeTo = RestRequestHeader.of(Headers.TIME_RANGE_TO, timeRangeTo);

    return restClient.get(url, urlVariables, AggregatedIncomingRequestsDto.class, headerId, headerToken, headerInterval, headerTimeFrom, headerTimeTo);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Boolean isNewlyInvoked(String accessToken, String applicationId, InitialInvocationCheckDto invocation) {
    final String url = url(Urls.COST__INVOCATION__CHECK);
    final Map<String, String> urlVariables = ImmutableMap.of();

    RestRequestHeader headerId = RestRequestHeader.of(Headers.APPLICATION_ID, applicationId);
    RestRequestHeader headerToken = RestRequestHeader.of(Headers.ACCESS_TOKEN, accessToken);
    RestRequestHeader invokedClass = RestRequestHeader.of(Headers.INVOKED_CLASS, invocation.getInvokedClassName());
    RestRequestHeader invokedMethod = RestRequestHeader.of(Headers.INVOKED_METHOD, invocation.getInvokedMethodName());
    RestRequestHeader callerClass = RestRequestHeader.of(Headers.CALLER_CLASS, invocation.getCallerClassName());
    RestRequestHeader callerMethod = RestRequestHeader.of(Headers.CALLER_METHOD, invocation.getCallerMethodName());

    return restClient.get(url, urlVariables, Boolean.class, headerId, headerToken, invokedClass, invokedMethod, callerClass, callerMethod);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ApplicationDto currentApplication(String accessToken, String applicationId) {
    final String url = url(Urls.MONITORING__LOGIN);
    final Map<String, String> urlVariables = ImmutableMap.of();

    RestRequestHeader headerId = RestRequestHeader.of(Headers.APPLICATION_ID, applicationId);
    RestRequestHeader headerToken = RestRequestHeader.of(Headers.ACCESS_TOKEN, accessToken);

    return restClient.get(url, urlVariables, ApplicationDto.class, headerId, headerToken);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ApplicationDto application(String accessToken, String applicationId, String requestedApplicationId) {
    final String url = url(Urls.MONITORING__APPLICATION);
    final Map<String, String> urlVariables = ImmutableMap.of();

    RestRequestHeader headerId = RestRequestHeader.of(Headers.APPLICATION_ID, applicationId);
    RestRequestHeader headerToken = RestRequestHeader.of(Headers.ACCESS_TOKEN, accessToken);
    RestRequestHeader headerReqId = RestRequestHeader.of(Headers.REQUESTED_APPLICATION_ID, requestedApplicationId);

    return restClient.get(url, urlVariables, ApplicationDto.class, headerId, headerToken, headerReqId);
  }

  /**
   * Helper that concatenates a given url with the root url
   * 
   * @param urlFragment
   * @return
   */
  private String url(final String urlFragment) {
    return Urls.concatenate(rootUrl, urlFragment);
  }
}
