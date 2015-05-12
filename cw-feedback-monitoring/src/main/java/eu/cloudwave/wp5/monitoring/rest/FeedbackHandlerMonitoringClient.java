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
package eu.cloudwave.wp5.monitoring.rest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import eu.cloudwave.wp5.common.constants.Headers;
import eu.cloudwave.wp5.common.constants.Urls;
import eu.cloudwave.wp5.common.dto.model.MetricContainingProcedureExecutionDto;
import eu.cloudwave.wp5.monitoring.dto.RunningProcedureExecution;
import eu.cloudwave.wp5.monitoring.properties.PropertiesLoader;

/**
 * Client that allows to send monitoring data to the Feedback Handler.
 */
public class FeedbackHandlerMonitoringClient {

  private static final String ACCESS_TOKEN = "monitoring.access_token";
  private static final String APP_ID = "monitoring.app_id";
  private static final String FEEDBACK_HANDLER_URL = "monitoring.feedback_handler_url";
  private static final String SLASH = "/";

  private PropertiesLoader propertiesLoader;

  public FeedbackHandlerMonitoringClient() {
    this.propertiesLoader = new PropertiesLoader();
  }

  /**
   * Sends monitoring data (i.e. the call trace) and its attached metrics to the Feedback Handler.
   * 
   * @param executions
   *          the {@link RunningProcedureExecution}'s (i.e. the call trace)
   * @return <code>true</code> if the data has been successfully sent to the Feedback Handler, <code>false</code>
   *         otherwise
   */
  public boolean postData(final RunningProcedureExecution rootProcedureExecution) {
    final MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
    headers.add(Headers.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    headers.add(Headers.ACCESS_TOKEN, accessToken());
    headers.add(Headers.APPLICATION_ID, applicationId());
    final HttpEntity<MetricContainingProcedureExecutionDto> httpEntity = new HttpEntity<MetricContainingProcedureExecutionDto>(rootProcedureExecution, headers);
    final ResponseEntity<Boolean> result = new RestTemplate().exchange(url(), HttpMethod.POST, httpEntity, Boolean.class);
    return result.getBody();
  }

  private String accessToken() {
    return propertiesLoader.get(ACCESS_TOKEN).get();
  }

  private String applicationId() {
    return propertiesLoader.get(APP_ID).get();
  }

  private String url() {
    String rootUrl = propertiesLoader.get(FEEDBACK_HANDLER_URL).get();
    if (!rootUrl.endsWith(SLASH)) {
      rootUrl += SLASH;
    }
    return rootUrl + Urls.MONITORING__DATA;
  }

}
