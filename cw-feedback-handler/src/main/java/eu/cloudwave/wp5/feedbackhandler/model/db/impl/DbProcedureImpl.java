package eu.cloudwave.wp5.feedbackhandler.model.db.impl;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import eu.cloudwave.wp5.common.model.Annotation;
import eu.cloudwave.wp5.common.model.ProcedureKind;
import eu.cloudwave.wp5.common.model.impl.ProcedureImpl;
import eu.cloudwave.wp5.feedbackhandler.constants.DbTableNames;
import eu.cloudwave.wp5.feedbackhandler.model.db.DbApplication;
import eu.cloudwave.wp5.feedbackhandler.model.db.DbProcedure;

/**
 * Implementation of {@link DbProcedure}.
 */
@Document(collection = DbTableNames.PROCEDURES)
public class DbProcedureImpl extends ProcedureImpl implements DbProcedure {

  @Id
  private ObjectId id;

  @DBRef
  private DbApplication application;

  public DbProcedureImpl(final DbApplication application, final String className, final String name, final ProcedureKind kind, final List<String> arguments, final List<Annotation> annotations) {
    super(className, name, kind, arguments, annotations);
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

}
