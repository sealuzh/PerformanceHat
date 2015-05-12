package eu.cloudwave.wp5.feedbackhandler.model.impl.converter;

import eu.cloudwave.wp5.common.model.Procedure;
import eu.cloudwave.wp5.common.model.ProcedureExecution;
import eu.cloudwave.wp5.common.model.ProcedureExecutionMetric;
import eu.cloudwave.wp5.feedbackhandler.model.db.DbApplication;
import eu.cloudwave.wp5.feedbackhandler.model.db.DbProcedure;
import eu.cloudwave.wp5.feedbackhandler.model.db.DbProcedureExecution;
import eu.cloudwave.wp5.feedbackhandler.model.db.DbProcedureExecutionMetric;
import eu.cloudwave.wp5.feedbackhandler.model.db.impl.DbProcedureExecutionImpl;

/**
 * Provides methods to convert any objects implementing one of the common model interfaces (e.g. DTO's) into
 * MongoDB-specific model entities.
 */
public interface DbConverter {

  /**
   * Converts any {@link Procedure} to a {@link DbProcedure} assigned to the given {@link DbApplication}.
   * 
   * @param procedure
   *          the {@link Procedure} to be converted
   * @param application
   *          the {@link DbApplication}
   * @return the created {@link DbProcedure}.
   */
  public DbProcedure convert(final Procedure procedure, final DbApplication application);

  /**
   * Converts any {@link ProcedureExecution} to a {@link DbProcedureExecution} assigned to the given
   * {@link DbApplication}.
   * 
   * @param procedureExecution
   *          the {@link ProcedureExecution} to be converted
   * @param application
   *          the {@link DbApplication}
   * @return the created {@link DbProcedureExecution}
   */
  public DbProcedureExecution convert(final ProcedureExecution procedureExecution, final DbApplication application);

  /**
   * Converts any {@link ProcedureExecutionMetric} to a {@link DbProcedureExecutionMetric}.
   * 
   * @param procedureExecutionMetric
   *          the {@link ProcedureExecutionMetric} to be converted
   * @param procedureExecution
   *          the {@link DbProcedureExecutionImpl}
   * @param application
   *          the {@link DbApplication}
   * @return the created {@link DbProcedureExecutionMetric}
   */
  public DbProcedureExecutionMetric convert(final ProcedureExecutionMetric metric, final DbProcedureExecution procedureExecution, final DbApplication application);

}
