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
package eu.cloudwave.wp5.common.constants;

/**
 * Constants related to HTTP headers.
 */
public class Headers {

  // Keys
  public static final String X_API_KEY = "X-Api-Key";
  public static final String ACCESS_TOKEN = "Access-Token";
  public static final String APPLICATION_ID = "Application-ID";
  public static final String REQUESTED_APPLICATION_ID = "Requested-Application-ID";
  public static final String CONTENT_TYPE = "Content-Type";
  public static final String AGGREGATION_INTERVAL = "Aggregation-Interval";
  public static final String TIME_RANGE_FROM = "Time-Range-From";
  public static final String TIME_RANGE_TO = "Time-Range-To";
  public static final String INVOKED_CLASS = "Invoked-Class";
  public static final String INVOKED_METHOD = "Invoked-Method";
  public static final String CALLER_CLASS = "Caller-Class";
  public static final String CALLER_METHOD = "Caller-Method";

  private Headers() {}
}
