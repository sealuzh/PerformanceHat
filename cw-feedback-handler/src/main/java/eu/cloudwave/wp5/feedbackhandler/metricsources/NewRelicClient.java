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
package eu.cloudwave.wp5.feedbackhandler.metricsources;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import eu.cloudwave.wp5.common.dto.newrelic.MethodInfoSummarized;
import eu.cloudwave.wp5.common.error.ErrorType;
import eu.cloudwave.wp5.common.rest.RestRequestBody;
import eu.cloudwave.wp5.common.rest.RestRequestHeader;
import eu.cloudwave.wp5.feedbackhandler.messages.Config;
import eu.cloudwave.wp5.feedbackhandler.messages.Messages;
import eu.cloudwave.wp5.feedbackhandler.rest.JsonRestClient;

/**
 * An implementation of {@link MetricSourceClient} for the New Relic API.
 */
@Service(MetricSourceClientNames.NEW_RELIC_CLIENT)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
// temporary workaround: remove @SuppressWarnings
@SuppressWarnings("unused")
public class NewRelicClient extends AbstractMetricSourceClient implements MetricSourceClient {

  private static final String URL_PATTERN__METRICS = Config.NEW_RELIC__URL + "applications/%s/metrics/data.json";

  private static final String METRIC_NAME_PATTERN = "Java/%s/%s";

  private static final String PARAM__NAMES = "names";
  private static final String PARAM__SUMMARIZE = "summarize";

  private static final String HEADER__API_KEY = "X-Api-Key";

  private static final String JSON__AVG_RESPONSE_TIME = "average_response_time";
  private static final String JSON__CALL_COUNT = "call_count";
  private static final String JSON__TITLE = "title";

  private static final String ERROR__INVALID_APPLICATION_ID = "No record found with id";
  private static final String ERROR__INVALID_PARAMETER = "invalid parameter";
  private static final String ERROR__INVALID_PARAMETER_APPLICATION_ID = "invalid parameter: application_id";
  private static final String ERROR__UNKNOWN_METRIC = "Unknown metric";

  @Autowired
  private JsonRestClient jsonRestClient;

  private String apiKey;

  public NewRelicClient(final String apiKey) {
    this.apiKey = apiKey;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public MethodInfoSummarized summarized(final String applicationId, final String className, final String methodName) throws MetricSourceClientException {
    return metrics(applicationId, className, methodName, true);
  }

  private MethodInfoSummarized metrics(final String applicationId, final String className, final String methodName, final Boolean summarized) throws MetricSourceClientException {
    // temporary workaround: no access to new relic API
    // final RestRequestBody requestBody = RestRequestBody.of(PARAM__NAMES, metricName(className,
    // methodName)).and(PARAM__SUMMARIZE, summarized.toString()).create();
    // final JsonNode response = executePostRequest(metricsUrl(applicationId), requestBody);
    // return new MethodInfoSummarized(response.findValue(JSON__AVG_RESPONSE_TIME).asDouble(),
    // response.findValue(JSON__CALL_COUNT).asInt());
    return new MethodInfoSummarized(1.5, 5);
  }

  private String metricName(final String className, final String methodName) {
    return String.format(METRIC_NAME_PATTERN, className, methodName);
  }

  private String metricsUrl(final String applicationId) {
    return String.format(URL_PATTERN__METRICS, applicationId);
  }

  private JsonNode executePostRequest(final String url, final RestRequestBody requestBody) throws MetricSourceClientException {
    try {
      return jsonRestClient.post(url, requestBody, RestRequestHeader.of(HEADER__API_KEY, apiKey));
    }
    catch (final HttpStatusCodeException | IOException e) {
      handleException(e);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void handleHttpServerException(final HttpStatusCodeException serverError) throws MetricSourceClientException, IOException {
    // if the status code is 401 UNAUTHORIZED -> the API key is not valid
    if (serverError.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
      throw new MetricSourceClientException(ErrorType.NEW_RELIC__INVALID_API_KEY, Messages.ERRORS__NEW_RELIC__INVALID_API_KEY);
    }

    final ObjectMapper mapper = new ObjectMapper();
    final JsonNode errorNode = mapper.readTree(serverError.getResponseBodyAsString());
    final JsonNode titleNode = errorNode.findValue(JSON__TITLE);
    if (titleNode != null) {
      final String message = titleNode.asText();
      ErrorType type = ErrorType.NEW_RELIC__GENERAL;

      if (message.contains(ERROR__INVALID_APPLICATION_ID) || message.equals(ERROR__INVALID_PARAMETER_APPLICATION_ID)) {
        type = ErrorType.NEW_RELIC__INVALID_APPLICATION_ID;
      }
      else if (message.contains(ERROR__UNKNOWN_METRIC)) {
        type = ErrorType.UNKNOWN_METRIC;
      }
      else if (message.contains(ERROR__INVALID_PARAMETER)) {
        type = ErrorType.INVALID_PARAMETER;
      }
      throw new MetricSourceClientException(type, message);
    }
  }
}
