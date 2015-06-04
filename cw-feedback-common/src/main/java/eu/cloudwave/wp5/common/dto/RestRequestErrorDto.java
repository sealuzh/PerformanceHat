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
package eu.cloudwave.wp5.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import eu.cloudwave.wp5.common.error.ErrorType;
import eu.cloudwave.wp5.common.util.HashCodes;

/**
 * A DTO to transmit error messages.
 */
public class RestRequestErrorDto {

  private static final String UNKNOWN = "<unknown error>";

  private String message;
  private ErrorType type;

  public RestRequestErrorDto(final ErrorType type, final String message) {
    this.type = type;
    this.message = message;
  }

  public RestRequestErrorDto() {
    // jackson requires default constructor for deserialization
    this(ErrorType.UNKNOWN, UNKNOWN);
  }

  public ErrorType getType() {
    return type;
  }

  @JsonIgnore
  public String getTitle() {
    return type.getTitle();
  }

  public String getMessage() {
    return message;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(final Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj instanceof RestRequestErrorDto) {
      final RestRequestErrorDto rre = (RestRequestErrorDto) obj;
      if (rre.getMessage().equals(this.getMessage()) && rre.getType().equals(this.getType())) {
        return true;
      }
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    return HashCodes.combinedHashCode(message.hashCode(), type.hashCode());
  }

}
