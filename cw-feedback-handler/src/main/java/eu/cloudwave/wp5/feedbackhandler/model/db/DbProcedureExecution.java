package eu.cloudwave.wp5.feedbackhandler.model.db;

import org.springframework.data.mongodb.core.mapping.Document;

import eu.cloudwave.wp5.common.model.ProcedureExecution;
import eu.cloudwave.wp5.feedbackhandler.constants.DbTableNames;

/**
 * MongoDB-specific extension of {@link ProcedureExecution}.
 * 
 * Extends the {@link ProcedureExecution} class with an additional attribute specifying the application the procedure
 * belongs to. This is only used on the server and therefore not exposed in the shared interface.
 */
@Document(collection = DbTableNames.PROCEDURE_EXECUTIONS)
public interface DbProcedureExecution extends ProcedureExecution, DbEntity {

  public DbApplication getApplication();

}
