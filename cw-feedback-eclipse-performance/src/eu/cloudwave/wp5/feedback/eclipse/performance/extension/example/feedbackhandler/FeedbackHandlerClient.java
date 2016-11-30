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
package eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.feedbackhandler;

import eu.cloudwave.wp5.common.dto.AggregatedProcedureMetricsDto;
import eu.cloudwave.wp5.common.dto.model.ProcedureExecutionMetricDto;
import eu.cloudwave.wp5.common.dto.newrelic.MethodInfoSummarized;

/**
 * Provides methods to talk to the Feedback Handler Server.
 */
public interface FeedbackHandlerClient {

  /**
   * Gets summarized information about the execution of a method from New Relic.
   * 
   * @param apiKey
   *          the New Relic API key
   * @param applicationId
   *          the application ID
   * @param className
   *          the name of the class that contains the method
   * @param procedureName
   *          the name of the method
   * @return summarized information about the execution of a method from New Relic
   */
  public MethodInfoSummarized newRelicSummarized(final String apiKey, final String applicationId, String className, String procedureName);

  /**
   * Get information about hotspots (i.e. methods that take very or too long)
   * 
   * @param accessToken
   *          the access token
   * @param applicationId
   *          the application ID
   * @param threshold
   *          the threshold specifying the minimum value of a hotspot
   * @return An array of {@link AggregatedProcedureMetricsDto} containing the hotspots
   */
  public AggregatedProcedureMetricsDto[] hotspots(final String accessToken, final String applicationId, final Double threshold);

  /**
   * Fetches all execution metrics for the procedure with the given properties (if it exists).
   * 
   * @param accessToken
   *          the access token
   * @param applicationId
   *          the application ID
   * @param className
   *          the name of the class of the procedure
   * @param procedureName
   *          the name of the procedure (if it is a constructor, the name should be '\<init\>')
   * @param arguments
   *          the arguments of the procedure (qualified class names)
   * @return A an array of {@link ProcedureExecutionMetricDto} containing the metrics of the procedure with the given
   *         properties
   */
  public ProcedureExecutionMetricDto[] procedure(final String accessToken, final String applicationId, final String className, final String procedureName, final String[] arguments);

  /**
   * Get the average execution time of the procedure with the given properties.
   * 
   * @param accessToken
   *          the access token
   * @param applicationId
   *          the application ID
   * @param className
   *          the name of the class of the procedure
   * @param procedureName
   *          the name of the procedure
   * @param arguments
   *          the arguments of the procedure (qualified class names)
   * @return the average execution time of the procedure with the given properties
   */
  public Double avgExecTime(final String accessToken, final String applicationId, final String className, final String procedureName, final String[] arguments);

  /**
   * Get the average collection size for a given procedure (i.e. the procedure with the given attributes). The number
   * determines the position of the parameter for which the collection size is searched in the method signature. An
   * empty number means that the collection size of the return value is searched.
   * 
   * @param accessToken
   *          the access token
   * @param applicationId
   *          the application ID
   * @param className
   *          the name of the class of the procedure
   * @param procedureName
   *          the name of the procedure (if it is a constructor, the name should be '\<init\>')
   * @param arguments
   *          the arguments of the procedure (qualified class names)
   * @param number
   *          determines the position of the parameter (for which the collection size is searched) in the method
   *          signature (an empty {@link String} means that the collection size of the return value is searched)
   * @return
   */
  public Double collectionSize(final String accessToken, final String applicationId, final String className, final String procedureName, String[] arguments, final String number);

}
