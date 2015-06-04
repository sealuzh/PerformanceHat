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
package eu.cloudwave.wp5.feedbackhandler.metricsources;

import eu.cloudwave.wp5.common.dto.newrelic.MethodInfoSummarized;

/**
 * This is a general interface to access an API of an external metric source.
 */
public interface MetricSourceClient {

  /**
   * Get summarized information about the given method.
   * 
   * @param applicationId
   *          the application ID
   * @param className
   *          the name of the class that contains the method
   * @param methodName
   *          the name of the method
   * @return summarized information about the given method
   * @throws MetricSourceClientException
   *           if the request was not successful
   */
  public MethodInfoSummarized summarized(String applicationId, String className, String methodName) throws MetricSourceClientException;

}
