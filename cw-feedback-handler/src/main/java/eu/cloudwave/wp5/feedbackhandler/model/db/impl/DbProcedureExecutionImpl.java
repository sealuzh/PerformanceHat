package eu.cloudwave.wp5.feedbackhandler.model.db.impl;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import eu.cloudwave.wp5.common.model.Procedure;
import eu.cloudwave.wp5.common.model.ProcedureExecution;
import eu.cloudwave.wp5.common.model.impl.AbstractProcedureExecution;
import eu.cloudwave.wp5.feedbackhandler.constants.DbTableNames;
import eu.cloudwave.wp5.feedbackhandler.model.db.DbApplication;
import eu.cloudwave.wp5.feedbackhandler.model.db.DbProcedureExecution;

/**
 * Implementation of {@link DbProcedureExecution}.
 */
@Document(collection = DbTableNames.PROCEDURE_EXECUTIONS)
public class DbProcedureExecutionImpl extends AbstractProcedureExecution implements DbProcedureExecution {

  @Id
  private ObjectId id;

  @DBRef
  private DbApplication application;

  @DBRef
  private ProcedureExecution caller;

  public DbProcedureExecutionImpl(final DbApplication application, final Procedure procedure, final long startTime) {
    super(procedure, startTime);
    this.application = application;
  }

  @Override
  public ObjectId getId() {
    return id;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DbApplication getApplication() {
    return application;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ProcedureExecution getCaller() {
    return caller;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setCaller(final ProcedureExecution caller) {
    this.caller = caller;
  }

}
