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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import eu.cloudwave.wp5.common.constants.Headers;
import eu.cloudwave.wp5.common.constants.Params;
import eu.cloudwave.wp5.common.constants.Urls;
import eu.cloudwave.wp5.common.dto.newrelic.MethodInfoSummarized;
import eu.cloudwave.wp5.common.error.RequestException;
import eu.cloudwave.wp5.feedbackhandler.metricsources.MetricSourceClient;
import eu.cloudwave.wp5.feedbackhandler.metricsources.MetricSourceClientException;
import eu.cloudwave.wp5.feedbackhandler.metricsources.MetricSourceClientProvider;

@RestController
public class NewRelicController extends AbstractBaseRestController {

  @Autowired
  private MetricSourceClientProvider metricSourceClientProvider;

  @RequestMapping(Urls.NEW_RELIC__SUMMARIZE)
  @ResponseStatus(HttpStatus.OK)
  public MethodInfoSummarized summarize(
      @RequestHeader(Headers.X_API_KEY) final String apiKey,
      @RequestParam(Params.APPLICATION_ID) final String applicationId,
      @RequestParam(Params.CLASS_NAME) final String className,
      @RequestParam(Params.PROCEDURE_NAME) final String methodName) throws RequestException {

    final MetricSourceClient metricSourceClient = metricSourceClientProvider.getNewRelicClient(apiKey);
    try {
      return metricSourceClient.summarized(applicationId, className, methodName);
    }
    catch (final MetricSourceClientException e) {
      throw new RequestException(e.getType(), e.getMessage());
    }
  }
}
