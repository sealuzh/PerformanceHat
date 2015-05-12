package eu.cloudwave.wp5.common.model.factories;

import eu.cloudwave.wp5.common.model.MetricType;
import eu.cloudwave.wp5.common.model.Procedure;
import eu.cloudwave.wp5.common.model.ProcedureExecution;
import eu.cloudwave.wp5.common.model.ProcedureExecutionMetric;
import eu.cloudwave.wp5.common.model.ProcedureMetric;

/**
 * A factory for all kinds of metrics
 */
public interface MetricFactory {

  /**
   * Creates a {@link ProcedureMetric}.
   * 
   * @param type
   *          the type of the metric
   * @param procedure
   *          the related {@link Procedure}
   * @param value
   *          the value
   * @return the created {@link ProcedureMetric}
   */
  public ProcedureMetric create(final MetricType type, final Procedure procedure, final Number value);

  /**
   * Creates a {@link ProcedureMetric}.
   * 
   * @param type
   *          the type of the metric
   * @param procedure
   *          the related {@link Procedure}
   * @param additionalQualifier
   *          the additional qualifier
   * @param value
   *          the value
   * @return the created {@link ProcedureMetric}
   */
  public ProcedureMetric create(final MetricType type, final Procedure procedure, final String additionalQualifier, final Number value);

  /**
   * Creates a {@link ProcedureMetric}.
   * 
   * @param type
   *          the type of the metric
   * @param procedureExecution
   *          the related {@link ProcedureExecution}
   * @param value
   *          the value
   * @return the created {@link ProcedureMetric}
   */
  public ProcedureExecutionMetric create(final MetricType type, final ProcedureExecution procedureExecution, final Number value);

  /**
   * Creates a {@link ProcedureMetric}.
   * 
   * @param type
   *          the type of the metric
   * @param procedureExecution
   *          the related {@link ProcedureExecution}
   * @param additionalQualifier
   *          the additional qualifier
   * @param value
   *          the value
   * @return the created {@link ProcedureMetric}
   */
  public ProcedureExecutionMetric create(final MetricType type, final ProcedureExecution procedureExecution, final String additionalQualifier, final Number value);

}
