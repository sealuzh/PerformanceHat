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
package eu.cloudwave.wp5.feedbackhandler.aggregations;

import java.util.List;

import eu.cloudwave.wp5.common.util.Splitters;

/**
 * Mongodb result item of microservice requests from a caller to a callee method of another service at different points
 * of time
 */
public class ClientRequestCollector implements RequestCollector {

  /**
   * The identifier of the caller service
   */
  private String caller;

  /**
   * The identifier of the callee service
   */
  private String callee;

  /**
   * The method/endpoint of the callee that is called by the caller
   */
  private String calleeMethod;

  /**
   * The name of the callee client method that is called
   */
  private String methodName;

  /**
   * List of timestamps of requests in milliseconds
   */
  private List<Long> reqTimestamps;

  /**
   * Constructor
   * 
   * @param caller
   *          The identifier of the caller service
   * @param callee
   *          The identifier of the callee service
   * @param calleeMethod
   *          The method/endpoint of the callee that is called by the caller
   * @param methodName
   *          The name of the callee method that is called The name of the callee method that is called
   * @param reqTimestamps
   *          List of timestamps of requests in milliseconds
   */
  public ClientRequestCollector(String caller, String callee, String calleeMethod, String methodName, List<Long> reqTimestamps) {
    this.caller = Splitters.inBetweenBrackets(caller);
    this.callee = Splitters.inBetweenBrackets(callee);
    this.calleeMethod = Splitters.inBetweenBrackets(calleeMethod);
    this.methodName = methodName;
    this.reqTimestamps = reqTimestamps;
  }

  public String getCaller() {
    return caller;
  }

  public String getCallee() {
    return callee;
  }

  public String getCalleeMethod() {
    return calleeMethod;
  }

  public String getMethodName() {
    return methodName;
  }

  /**
   * {@inheritDoc}
   */
  public List<Long> getReqTimestamps() {
    return reqTimestamps;
  }
}
