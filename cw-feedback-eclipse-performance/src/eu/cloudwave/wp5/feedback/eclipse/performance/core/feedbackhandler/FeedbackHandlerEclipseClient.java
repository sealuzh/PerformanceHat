package eu.cloudwave.wp5.feedback.eclipse.performance.core.feedbackhandler;

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
   * Gets summarized information about the execution of a method from New Relic.
   * 
   * @param project
   *          the project
   * @param className
   *          the name of the class that contains the method
   * @param procedureName
   *          the name of the method
   * @return summarized information about the execution of a method from New Relic
   */
  public MethodInfoSummarized newRelicSummarized(FeedbackProject project, String className, String procedureName);

  /**
   * Get information about hotspots (i.e. methods that take very or too long)
   * 
   * @param project
   *          the project
   * @return An array of {@link AggregatedProcedureMetricsDto} containing the hotspots
   */
  public AggregatedProcedureMetricsDto[] hotspots(FeedbackProject project);

  /**
   * Fetches all execution metrics for the procedure with the given properties (if it exists).
   * 
   * @param project
   *          the project
   * @param className
   *          the name of the class of the procedure
   * @param procedureName
   *          the name of the procedure
   * @param arguments
   *          the arguments of the procedure (qualified class names)
   * @return An array containing all metrics for the procedure with the given properties
   */
  public ProcedureExecutionMetricDto[] procedure(final FeedbackProject project, final String className, final String procedureName, final String[] arguments);

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
  public Double collectionSize(final FeedbackProject project, final String className, final String procedureName, String[] arguments, final String number);

}
