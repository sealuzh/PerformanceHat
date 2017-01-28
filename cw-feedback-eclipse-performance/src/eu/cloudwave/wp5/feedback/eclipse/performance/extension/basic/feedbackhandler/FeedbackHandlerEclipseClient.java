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
package eu.cloudwave.wp5.feedback.eclipse.performance.extension.basic.feedbackhandler;

import eu.cloudwave.wp5.common.dto.AggregatedProcedureMetricsDto;
import eu.cloudwave.wp5.common.dto.model.ProcedureExecutionMetricDto;
import eu.cloudwave.wp5.common.dto.newrelic.MethodInfoSummarized;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.FeedbackProject;

/**
 * An eclipse-specific client to talk to the feedback handler. Delegates the actual communication with the feedback
 * handler to a {@link FeedbackHandlerClient} and provides eclipse-specific functionality around it.
 */
public interface FeedbackHandlerEclipseClient {

  /**
   * Get the average execution time of the procedure with the given properties.
   * 
   * @param project
   *          the project
   * @param className
   *          the name of the class of the procedure
   * @param procedureName
   *          the name of the procedure
   * @param arguments
   *          the arguments of the procedure (qualified class names)
   * @return the average execution time of the procedure with the given properties
   */
  public Double avgExecTime(final FeedbackProject project, final String className, final String procedureName, final String[] arguments);

  /**
   * Get the average collection size for a given procedure (i.e. the procedure with the given attributes). The number
   * determines the position of the parameter for which the collection size is searched in the method signature. An
   * empty number means that the collection size of the return value is searched.
   * 
   * @param project
   *          the project
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
  public Double collectionSize(final FeedbackProject project, final String className, final String procedureName, String[] arguments, final Integer number);

}
