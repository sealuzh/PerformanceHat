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

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;

import eu.cloudwave.wp5.common.error.ErrorType;

/**
 * An abstract base implementation for {@link MetricSourceClient}'s.
 */
public abstract class AbstractMetricSourceClient implements MetricSourceClient {

  /**
   * This method can be used by subclass for error handling. It directly handles 404 Errors and general errors that are
   * not treated specially and calls the hook {@link #handleHttpServerException(HttpServerErrorException)} for errors
   * that have to be treated specially by subclasses. By implementing the hook methods subclasses can handles those
   * errors individually.
   * 
   * @param throwable
   *          the exception
   * @throws MetricSourceClientException
   *           for errors that do not have to be treated specially by subclasses
   */
  protected final void handleException(final Throwable throwable) throws MetricSourceClientException {
    if (throwable instanceof HttpStatusCodeException) {
      final HttpStatusCodeException httpError = (HttpStatusCodeException) throwable;

      // handle errors individually by subclasses
      try {
        handleHttpServerException(httpError);
      }
      catch (final IOException e) {
        /**
         * This error can occur while trying to identify the cause for the original error. It is ignored and the
         * original error is thrown instead.
         */
      }

      // handle 404 NOT FOUND errors
      if (httpError.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
        throw new MetricSourceClientException(ErrorType.METRIC_SOURCE_NOT_AVAILABLE, httpError);
      }
    }

    // if no precise cause could be identified, throw the original exception
    throw new MetricSourceClientException(ErrorType.NEW_RELIC__GENERAL, throwable);
  }

  /**
   * Has to be implemented by subclasses to individually handle {@link HttpStatusCodeException}'s.
   * 
   * @param httpError
   *          the HTTP error
   * @throws MetricSourceClientException
   *           according to the particular {@link HttpStatusCodeException}
   * @throws IOException
   *           if the server response error could not be parsed correctly.
   */
  protected abstract void handleHttpServerException(HttpStatusCodeException httpError) throws MetricSourceClientException, IOException;

}
