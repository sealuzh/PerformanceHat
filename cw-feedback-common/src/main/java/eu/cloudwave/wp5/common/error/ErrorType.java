/*******************************************************************************
 * Copyright 2015 Software Evolution and Architecture Lab, University of Zurich
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 ******************************************************************************/
package eu.cloudwave.wp5.common.error;

public enum ErrorType {

  // unauthorized
  INVALID_APPLICATION_ID("Invalid Application-ID"),
  WRONG_ACCESS_TOKEN("Wrong Access-Token"),

  // metric sources
  METRIC_SOURCE_NOT_AVAILABLE("Metric Source not available"),
  UNKNOWN_METRIC("Unknown Metric"),
  INVALID_PARAMETER("Invalid Parameter"),

  // new relic
  NEW_RELIC__INVALID_API_KEY("Bad API Key"),
  NEW_RELIC__INVALID_APPLICATION_ID("Invalid Application ID"),
  NEW_RELIC__GENERAL("General New Relic Error"),

  // feedback handler
  FEEDBACK_HANDLER_NOT_AVAILABLE("Feedback Handler not available"),

  // not found
  REQUESTED_APPLICATION_ID_NOT_FOUND("Requested-Application-ID not found"),

  // general
  GENERAL("General Error"),

  // default
  UNKNOWN("<unknown error>");

  private String title;

  private ErrorType(final String title) {
    this.title = title;
  }

  public String getTitle() {
    return title;
  }

}
