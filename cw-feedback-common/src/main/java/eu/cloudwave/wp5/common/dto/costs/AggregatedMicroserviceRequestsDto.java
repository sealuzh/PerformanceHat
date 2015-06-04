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
package eu.cloudwave.wp5.common.dto.costs;

/**
 * Contains the aggregated values of microservice requests from caller to callee
 */
public class AggregatedMicroserviceRequestsDto {

  private String caller;
  private String callee;
  private String calleeMethod;
  private int min;
  private int max;
  private double avg;
  private long sum;

  /**
   * Constructor
   * 
   * @param caller
   * @param callee
   * @param calleeMethod
   * @param min
   * @param max
   * @param avg
   * @param sum
   */
  public AggregatedMicroserviceRequestsDto(String caller, String callee, String calleeMethod, int min, int max, double avg, long sum) {
    this.caller = caller;
    this.callee = callee;
    this.calleeMethod = calleeMethod;
    this.min = min;
    this.max = max;
    this.avg = avg;
    this.sum = sum;
  }

  /**
   * Default constructor should not be used, but is required for deserialization
   */
  public AggregatedMicroserviceRequestsDto() {
    this(null, null, null, 0, 0, 0, 0);
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

  public int getMin() {
    return min;
  }

  public int getMax() {
    return max;
  }

  public double getAvg() {
    return avg;
  }

  public long getSum() {
    return sum;
  }
}
