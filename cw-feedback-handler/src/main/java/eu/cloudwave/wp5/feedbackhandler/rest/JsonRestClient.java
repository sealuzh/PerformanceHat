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
package eu.cloudwave.wp5.feedbackhandler.rest;

import java.io.IOException;

import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import eu.cloudwave.wp5.common.rest.RestRequestBody;
import eu.cloudwave.wp5.common.rest.RestRequestHeader;

/**
 * A REST client that returns the responses as {@link JsonNode}.
 */
public interface JsonRestClient {

  /**
   * Perform a GET request.
   * 
   * @param url
   *          the URL of the request
   * @param requestHeaders
   *          the headers of the request
   * @return the response as {@link JsonNode}
   * @throws JsonProcessingException
   * @throws IOException
   */
  public JsonNode get(final String url, final RestRequestHeader... requestHeaders) throws JsonProcessingException, IOException;

  /**
   * Perform a POST request.
   * 
   * @param url
   *          the URL of the request
   * @param requestBody
   *          the body of the request
   * @param requestHeaders
   *          the headers of the request
   * @return the {@link ResponseEntity}
   * @throws JsonProcessingException
   * @throws IOException
   */
  public JsonNode post(final String url, final RestRequestBody requestBody, final RestRequestHeader... requestHeaders) throws JsonProcessingException, IOException;

}
