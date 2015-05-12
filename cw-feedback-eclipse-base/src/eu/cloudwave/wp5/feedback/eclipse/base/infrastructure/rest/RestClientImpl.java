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
package eu.cloudwave.wp5.feedback.eclipse.base.infrastructure.rest;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;

import com.fasterxml.jackson.databind.ObjectMapper;

import eu.cloudwave.wp5.common.dto.RestRequestErrorDto;
import eu.cloudwave.wp5.common.error.ErrorType;
import eu.cloudwave.wp5.common.error.RequestException;
import eu.cloudwave.wp5.common.rest.AbstractRestClient;
import eu.cloudwave.wp5.common.rest.RestRequestBody;
import eu.cloudwave.wp5.common.rest.RestRequestHeader;

/**
 * Implementation of {@link RestClient}.
 */
public class RestClientImpl extends AbstractRestClient implements RestClient {

  private static final String READ_SERVER_ERROR_EXCEPTION = "Exception while trying to read error response from the server.";

  private static final String SLASH = "/";

  private static final String QUESTION_MARK = "?";
  private static final String AMPERSAND = "&";
  private static final String EQUALITY_SIGN = "=";

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> T get(final String url, final Class<T> responseType, final RestRequestHeader... requestHeaders) {
    return getInternal(url, responseType, requestHeaders);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> T get(final String url, final Map<String, String> urlVariables, final Class<T> responseType, final RestRequestHeader... requestHeaders) {
    return getInternal(parametrizedUrl(url, urlVariables), responseType, requestHeaders);
  }

  private <T> T getInternal(final String url, final Class<T> responseType, final RestRequestHeader... requestHeaders) {
    try {
      return restTemplate(requestHeaders).getForObject(url, responseType);
    }
    catch (final RestClientException e) {
      handleErrors(e);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> T post(final String url, final Class<T> responseType, final RestRequestBody requestBody, final RestRequestHeader... requestHeaders) {
    return restTemplate(requestHeaders).postForObject(url, requestBody.get(), responseType);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> T post(final String url, final Map<String, String> urlVariables, final Class<T> responseType, final RestRequestBody requestBody, final RestRequestHeader... requestHeaders) {
    return postInternal(parametrizedUrl(url, urlVariables), responseType, requestBody, requestHeaders);
  }

  private <T> T postInternal(final String url, final Class<T> responseType, final RestRequestBody requestBody, final RestRequestHeader... requestHeaders) {
    return restTemplate(requestHeaders).postForObject(url, requestBody.get(), responseType);
  }

  private void handleErrors(final RestClientException exception) {
    // handle 404 NOT FOUND error
    if (exception instanceof ResourceAccessException) {
      throw new RequestException(ErrorType.FEEDBACK_HANDLER_NOT_AVAILABLE, exception.getMessage());
    }
    else if (exception instanceof HttpStatusCodeException) {
      final HttpStatusCodeException httpException = (HttpStatusCodeException) exception;

      // handle 404 NOT FOUND error
      if (httpException.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
        throw new RequestException(ErrorType.FEEDBACK_HANDLER_NOT_AVAILABLE, exception.getMessage());
      }

      // handle other errors
      final ObjectMapper mapper = new ObjectMapper();
      try {
        final RestRequestErrorDto error = mapper.readValue(httpException.getResponseBodyAsString(), RestRequestErrorDto.class);
        throw new RequestException(error.getType(), error.getMessage());
      }
      catch (final IOException e) {}
    }
    throw new RuntimeException(READ_SERVER_ERROR_EXCEPTION, exception);
  }

  private String parametrizedUrl(final String url, final Map<String, String> urlVariables) {
    String parametrizedUrl = url.endsWith(SLASH) ? url.substring(0, url.length() - 1) : url;
    String delimiter = QUESTION_MARK;
    for (final Map.Entry<String, String> variable : urlVariables.entrySet()) {
      parametrizedUrl = parametrizedUrl + delimiter + variable.getKey() + EQUALITY_SIGN + variable.getValue();
      delimiter = AMPERSAND;
    }
    return parametrizedUrl;
  }
}
