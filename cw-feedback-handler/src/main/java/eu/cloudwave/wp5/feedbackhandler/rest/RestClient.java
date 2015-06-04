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

import eu.cloudwave.wp5.common.rest.RestRequestBody;
import eu.cloudwave.wp5.common.rest.RestRequestHeader;

/**
 * Provides methods to perform requests to a REST API.
 */
public interface RestClient {

  /**
   * Perform a GET request.
   * 
   * @param url
   *          the URL of the request
   * @param responseType
   *          the type of the object in the response body
   * @param requestHeaders
   *          the headers of the request
   * @return the {@link ResponseEntity}
   */
  public <T> ResponseEntity<T> get(final String url, final Class<T> responseType, final RestRequestHeader... requestHeaders);

  /**
   * Perform a POST request.
   * 
   * @param url
   *          the URL of the request
   * @param responseType
   *          the type of the object in the response body
   * @param requestBody
   *          the body of the request
   * @param requestHeaders
   *          the headers of the request
   * @return the {@link ResponseEntity}
   */
  public <T> ResponseEntity<T> post(final String url, final Class<T> responseType, final RestRequestBody requestBody, final RestRequestHeader... requestHeaders);

}
