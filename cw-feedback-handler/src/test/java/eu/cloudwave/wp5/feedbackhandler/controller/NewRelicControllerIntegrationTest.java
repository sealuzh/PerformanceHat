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
package eu.cloudwave.wp5.feedbackhandler.controller;

import static eu.cloudwave.wp5.feedbackhandler.controller.mocks.NewRelicClientMock.EXPECTED_API_KEY;
import static eu.cloudwave.wp5.feedbackhandler.controller.mocks.NewRelicClientMock.EXPECTED_APPLICATION_ID;
import static eu.cloudwave.wp5.feedbackhandler.controller.mocks.NewRelicClientMock.EXPECTED_CLASS_NAME;
import static eu.cloudwave.wp5.feedbackhandler.controller.mocks.NewRelicClientMock.EXPECTED_METHOD_NAME;
import static eu.cloudwave.wp5.feedbackhandler.controller.mocks.NewRelicClientMock.INVALID_API_KEY_MSG;
import static eu.cloudwave.wp5.feedbackhandler.controller.mocks.NewRelicClientMock.INVALID_APPLICATION_ID_MSG;
import static eu.cloudwave.wp5.feedbackhandler.controller.mocks.NewRelicClientMock.UNKNOWN_METRIC_MSG;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import eu.cloudwave.wp5.common.constants.Headers;
import eu.cloudwave.wp5.common.constants.Params;
import eu.cloudwave.wp5.common.constants.Urls;
import eu.cloudwave.wp5.common.dto.RestRequestErrorDto;
import eu.cloudwave.wp5.common.error.ErrorType;
import eu.cloudwave.wp5.feedbackhandler.tests.fakes.DtoStubs;

@ContextConfiguration(locations = { "/spring-newrelic-controller-integration-test.xml" })
public class NewRelicControllerIntegrationTest extends AbstractBaseControllerIntegrationTest {

  private static final String INVALID_API_KEY = "1";
  private static final String INVALID_APPLICATION_ID = "1";
  private static final String UNKNOWN_CLASS_NAME = "unknown.Class";
  private static final String UNKNOWN_METHOD_NAME = "unknownMethod";

  private static final String SLASH = "/";

  @Test
  public void testSummarizeOk() throws Exception {
    final MockHttpServletRequestBuilder requestBuilder = getSummarizeRequestBuilder(EXPECTED_API_KEY, EXPECTED_APPLICATION_ID, EXPECTED_CLASS_NAME, EXPECTED_METHOD_NAME);
    assertObjectResponseOk(requestBuilder, DtoStubs.METHOD_INFO_SUMARIZED);
  }

  @Test
  public void testSummarizeWithInvalidApiKey() throws Exception {
    final MockHttpServletRequestBuilder requestBuilder = getSummarizeRequestBuilder(INVALID_API_KEY, EXPECTED_APPLICATION_ID, EXPECTED_CLASS_NAME, EXPECTED_METHOD_NAME);
    assertObjectResponse(requestBuilder, status().is4xxClientError(), new RestRequestErrorDto(ErrorType.NEW_RELIC__INVALID_API_KEY, INVALID_API_KEY_MSG));
  }

  @Test
  public void testSummarizeWithInvalidApplicationId() throws Exception {
    final MockHttpServletRequestBuilder requestBuilder = getSummarizeRequestBuilder(EXPECTED_API_KEY, INVALID_APPLICATION_ID, EXPECTED_CLASS_NAME, EXPECTED_METHOD_NAME);
    assertObjectResponse(requestBuilder, status().is4xxClientError(), new RestRequestErrorDto(ErrorType.NEW_RELIC__INVALID_APPLICATION_ID, INVALID_APPLICATION_ID_MSG));
  }

  @Test
  public void testSummarizeWithUnknownMetrics() throws Exception {
    // unknown class name
    MockHttpServletRequestBuilder requestBuilder = getSummarizeRequestBuilder(EXPECTED_API_KEY, EXPECTED_APPLICATION_ID, UNKNOWN_CLASS_NAME, EXPECTED_METHOD_NAME);
    assertObjectResponse(requestBuilder, status().is4xxClientError(), new RestRequestErrorDto(ErrorType.UNKNOWN_METRIC, UNKNOWN_METRIC_MSG));

    // unknown method name
    requestBuilder = getSummarizeRequestBuilder(EXPECTED_API_KEY, EXPECTED_APPLICATION_ID, EXPECTED_CLASS_NAME, UNKNOWN_METHOD_NAME);
    assertObjectResponse(requestBuilder, status().is4xxClientError(), new RestRequestErrorDto(ErrorType.UNKNOWN_METRIC, UNKNOWN_METRIC_MSG));
  }

  private MockHttpServletRequestBuilder getSummarizeRequestBuilder(final String apiKey, final String applicationId, final String className, final String methodName) {
    final String url = SLASH + Urls.NEW_RELIC__SUMMARIZE;
    return get(url).header(Headers.X_API_KEY, apiKey).param(Params.APPLICATION_ID, applicationId).param(Params.CLASS_NAME, className).param(Params.PROCEDURE_NAME, methodName);
  }

}
