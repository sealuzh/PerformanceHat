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
package eu.cloudwave.wp5.feedbackhandler.controller.mocks;

import static eu.cloudwave.wp5.feedbackhandler.tests.assertion.Asserts.assertSet;
import eu.cloudwave.wp5.common.dto.newrelic.MethodInfoSummarized;
import eu.cloudwave.wp5.common.error.ErrorType;
import eu.cloudwave.wp5.feedbackhandler.metricsources.MetricSourceClientException;
import eu.cloudwave.wp5.feedbackhandler.metricsources.NewRelicClient;
import eu.cloudwave.wp5.feedbackhandler.tests.fakes.DtoStubs;

/**
 * A Mock object for a {@link NewRelicClient} that just returns sample content without contacting the New Relic API.
 */
public class NewRelicClientMock extends NewRelicClient {

  public static final String EXPECTED_API_KEY = "0";
  public static final String EXPECTED_APPLICATION_ID = "0";
  public static final String EXPECTED_CLASS_NAME = "my.Class";
  public static final String EXPECTED_METHOD_NAME = "myMethod";

  public static final String INVALID_API_KEY_MSG = "Wrong API key";
  public static final String INVALID_APPLICATION_ID_MSG = "Wrong application ID";
  public static final String UNKNOWN_METRIC_MSG = "Unknown metric";

  private String apiKey;

  public NewRelicClientMock(final String apiKey) {
    super(apiKey);
    this.apiKey = apiKey;
  }

  @Override
  public MethodInfoSummarized summarized(final String applicationId, final String className, final String methodName) throws MetricSourceClientException {
    assertSet(applicationId, className, methodName);

    if (!apiKey.equals(EXPECTED_API_KEY)) {
      throw new MetricSourceClientException(ErrorType.NEW_RELIC__INVALID_API_KEY, INVALID_API_KEY_MSG);
    }
    else if (!applicationId.equals(EXPECTED_APPLICATION_ID)) {
      throw new MetricSourceClientException(ErrorType.NEW_RELIC__INVALID_APPLICATION_ID, INVALID_APPLICATION_ID_MSG);
    }
    else if (!className.equals(EXPECTED_CLASS_NAME) || !methodName.equals(EXPECTED_METHOD_NAME)) {
      throw new MetricSourceClientException(ErrorType.UNKNOWN_METRIC, UNKNOWN_METRIC_MSG);
    }
    return DtoStubs.METHOD_INFO_SUMARIZED;
  }
}
