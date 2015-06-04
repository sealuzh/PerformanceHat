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
package eu.cloudwave.wp5.common.error;

import eu.cloudwave.wp5.common.error.ErrorType;

/**
 * An error that can be thrown in a controller if the request from the client was not valid.
 */
public class RequestException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private ErrorType type;

  public RequestException(final ErrorType type, final String message) {
    super(message);
    this.type = type;
  }

  public RequestException(final String message) {
    super(message);
    this.type = ErrorType.GENERAL;
  }

  public ErrorType getType() {
    return type;
  }

}
