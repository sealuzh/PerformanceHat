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
package eu.cloudwave.wp5.feedbackhandler.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import eu.cloudwave.wp5.common.rest.AbstractRestClient;
import eu.cloudwave.wp5.common.rest.RestRequestBody;
import eu.cloudwave.wp5.common.rest.RestRequestHeader;

/**
 * Implementation of {@link RestClient}.
 */
@Service
public class RestClientImpl extends AbstractRestClient implements RestClient {

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> ResponseEntity<T> get(final String url, final Class<T> responseType, final RestRequestHeader... requestHeaders) {
    return restTemplate(requestHeaders).getForEntity(url, responseType);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> ResponseEntity<T> post(final String url, final Class<T> responseType, final RestRequestBody requestBody, final RestRequestHeader... requestHeaders) {
    return restTemplate(requestHeaders).postForEntity(url, requestBody.get(), responseType);
  }

}
