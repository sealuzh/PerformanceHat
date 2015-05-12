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
package eu.cloudwave.wp5.feedbackhandler.repositories.aggregations;

import java.util.List;

/**
 * Mongodb result item of a microservice client request
 */
public class MicroserviceClientRequest {

  private String caller;
  private String callee;
  private String methodName;
  private List<Long> reqTimestamps;

  public MicroserviceClientRequest(String caller, String callee, String methodName, List<Long> reqTimestamps) {
    /*
     * We only need the text in between the quotes
     */
    this.caller = caller.split("\"")[1];
    this.callee = callee.split("\"")[1];
    this.methodName = methodName;
    this.reqTimestamps = reqTimestamps;
  }

  public String getCaller() {
    return caller;
  }

  public String getCallee() {
    return callee;
  }

  public String getMethodName() {
    return methodName;
  }

  /*
   * Request timestamps in milliseconds
   */
  public List<Long> getReqTimestamps() {
    return reqTimestamps;
  }
}
