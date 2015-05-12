package eu.cloudwave.wp5.feedbackhandler.model.db;

import org.springframework.data.mongodb.core.mapping.Document;

import eu.cloudwave.wp5.common.model.Procedure;
import eu.cloudwave.wp5.feedbackhandler.constants.DbTableNames;

/**
 * MongoDB-specific extension of {@link Procedure}.
 * 
 * Extends the {@link Procedure} class with an additional attribute specifying the application the procedure belongs to.
 * This is only used on the server and therefore not exposed in the shared interface.
 */
@Document(collection = DbTableNames.PROCEDURES)
public interface DbProcedure extends Procedure, DbEntity {

  public DbApplication getApplication();

}
