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
package eu.cloudwave.wp5.common.dto.newrelic;

import eu.cloudwave.wp5.common.util.HashCodes;

public class MethodInfoSummarized {

  private static final double AVG_DEFAULT_VALUE = -1;
  private static final int NR_DEFAULT_VALUE = -1;

  private double averageExecutionTime;

  private int numberOfCalls;

  public MethodInfoSummarized() {
    // jackson requires default constructor for deserialization
    this(AVG_DEFAULT_VALUE, NR_DEFAULT_VALUE);
  }

  public MethodInfoSummarized(final double averageExecutionTime, final int numberOfCalls) {
    this.averageExecutionTime = averageExecutionTime;
    this.numberOfCalls = numberOfCalls;
  }

  public double getAverageExecutionTime() {
    return averageExecutionTime;
  }

  public int getNumberOfCalls() {
    return numberOfCalls;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(final Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj instanceof MethodInfoSummarized) {
      final MethodInfoSummarized mis = (MethodInfoSummarized) obj;
      if (mis.getAverageExecutionTime() == this.getAverageExecutionTime() && mis.getNumberOfCalls() == this.getNumberOfCalls()) {
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
    return HashCodes.combinedHashCode(HashCodes.hashCode(numberOfCalls), HashCodes.hashCode(averageExecutionTime));
  }
}
