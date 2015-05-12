package eu.cloudwave.wp5.common.model;

/**
 * A {@link ProcedureExecution} represents the execution of a {@link Procedure} at one point in time.
 */
public interface ProcedureExecution {

  /**
   * Returns the {@link Procedure} of the current {@link ProcedureExecution}.
   * 
   * @return the {@link Procedure} of the current {@link ProcedureExecution}
   */
  public Procedure getProcedure();

  /**
   * Returns the start time of the {@link ProcedureExecution} in milliseconds.
   * 
   * @return the start time of the {@link ProcedureExecution} in milliseconds
   */
  public long getStartTime();

  /**
   * Sets the {@link ProcedureExecution} that called the current {@link ProcedureExecution}.
   * 
   * @param caller
   *          the {@link ProcedureExecution} that called the current {@link ProcedureExecution}
   */
  public void setCaller(ProcedureExecution caller);

  /**
   * Returns the {@link ProcedureExecution} that called the current {@link ProcedureExecution}.
   * 
   * @return the {@link ProcedureExecution} that called the current {@link ProcedureExecution}
   */
  public ProcedureExecution getCaller();

}
