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

import eu.cloudwave.wp5.common.error.ErrorType;

/**
 * This exception is thrown if an error occurred while fetching metrics data from an external metric source.
 */
public class MetricSourceClientException extends Exception {

  private static final long serialVersionUID = 1L;

  private ErrorType type;

  public MetricSourceClientException(final ErrorType type, final String message) {
    super(message);
    this.type = type;
  }

  public MetricSourceClientException(final ErrorType type, final Throwable cause) {
    super(cause);
    this.type = type;
  }

  public ErrorType getType() {
    return type;
  }

}
