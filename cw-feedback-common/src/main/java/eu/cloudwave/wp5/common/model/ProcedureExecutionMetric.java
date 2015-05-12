package eu.cloudwave.wp5.common.model;

/**
 * A {@link ProcedureMetric} is a metric that is attached to a {@link ProcedureExecution}.
 */
public interface ProcedureExecutionMetric extends ProcedureMetric {

  /**
   * Returns the {@link ProcedureExecution} the current metric is attached to.
   * 
   * @return the {@link ProcedureExecution} the current metric is attached to
   */
  public ProcedureExecution getProcedureExecution();

  /**
   * Returns the timestamp of the current metric (i.e. the point in time when the metric has been measured).
   * 
   * @return the timestamp of the current metric
   */
  public long getTimestamp();

}
