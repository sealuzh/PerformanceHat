package eu.cloudwave.wp5.feedback.eclipse.costs.core.feedbackhandler;

import java.util.Calendar;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.cloudwave.wp5.common.constants.Headers;
import eu.cloudwave.wp5.common.constants.Urls;
import eu.cloudwave.wp5.common.dto.AggregatedMicroserviceRequestsDto;
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
  public AggregatedMicroserviceRequestsDto[] allRequests(final String accessToken, final String applicationId) {
    final String url = url(Urls.COST__ALL);
    final Map<String, String> urlVariables = ImmutableMap.of();

    return restClient.get(url, urlVariables, AggregatedMicroserviceRequestsDto[].class, RestRequestHeader.of(Headers.APPLICATION_ID, applicationId),
        RestRequestHeader.of(Headers.ACCESS_TOKEN, accessToken), RestRequestHeader.of(Headers.AGGREGATION_INTERVAL, defaultAggregationInterval()));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AggregatedMicroserviceRequestsDto[] requestsByCallee(String accessToken, String applicationId) {
    final String url = url(Urls.COST__FILTER__CALLEE);
    final Map<String, String> urlVariables = ImmutableMap.of();

    return restClient.get(url, urlVariables, AggregatedMicroserviceRequestsDto[].class, RestRequestHeader.of(Headers.APPLICATION_ID, applicationId),
        RestRequestHeader.of(Headers.ACCESS_TOKEN, accessToken), RestRequestHeader.of(Headers.AGGREGATION_INTERVAL, defaultAggregationInterval()));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AggregatedMicroserviceRequestsDto[] requestsByCaller(String accessToken, String applicationId) {
    final String url = url(Urls.COST__FILTER__CALLEE);
    final Map<String, String> urlVariables = ImmutableMap.of();

    return restClient.get(url, urlVariables, AggregatedMicroserviceRequestsDto[].class, RestRequestHeader.of(Headers.APPLICATION_ID, applicationId),
        RestRequestHeader.of(Headers.ACCESS_TOKEN, accessToken), RestRequestHeader.of(Headers.AGGREGATION_INTERVAL, defaultAggregationInterval()));
  }

  private String defaultAggregationInterval() {
    return Integer.toString(Calendar.MINUTE);
  }

  private String url(final String urlFragment) {
    return Urls.concatenate(rootUrl, urlFragment);
  }
}
