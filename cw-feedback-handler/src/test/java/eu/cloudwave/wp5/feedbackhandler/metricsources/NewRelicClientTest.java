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

import static org.fest.assertions.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.io.IOException;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import eu.cloudwave.wp5.common.dto.newrelic.MethodInfoSummarized;
import eu.cloudwave.wp5.feedbackhandler.rest.JsonRestClient;
import eu.cloudwave.wp5.feedbackhandler.rest.JsonRestClientImpl;
import eu.cloudwave.wp5.feedbackhandler.tests.base.AbstractRestClientTest;
import eu.cloudwave.wp5.feedbackhandler.tests.fakes.DtoStubs;

public class NewRelicClientTest extends AbstractRestClientTest {

  private static final String API_KEY = "0";
  private static final String RESPONSE_STUB_PATH = "/stubs/NewRelicMetricsDataResponse.json";

  @InjectMocks
  private NewRelicClient newRelicClient = new NewRelicClient(API_KEY);

  @Spy
  @InjectMocks
  private JsonRestClient jsonRestClient = new JsonRestClientImpl();

  @Test
  public void testSummarized() throws MetricSourceClientException, IOException {
    final String url = "https://api.newrelic.com/v2/applications/0/metrics/data.json";
    final String applicationId = "0";
    final String className = "my.Class";
    final String methodName = "myMethod";

    final String response = getResourceAsString(RESPONSE_STUB_PATH);
    mockServer.expect(requestTo(url)).andExpect(method(HttpMethod.POST)).andRespond(withSuccess(response, MediaType.APPLICATION_JSON));

    final MethodInfoSummarized actualResponse = newRelicClient.summarized(applicationId, className, methodName);
    assertThat(actualResponse).isEqualTo(DtoStubs.METHOD_INFO_SUMARIZED);
  }
}
