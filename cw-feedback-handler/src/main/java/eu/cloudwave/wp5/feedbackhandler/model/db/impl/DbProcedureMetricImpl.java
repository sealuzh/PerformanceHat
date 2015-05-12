package eu.cloudwave.wp5.feedbackhandler.model.db.impl;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import eu.cloudwave.wp5.common.model.Procedure;
import eu.cloudwave.wp5.common.model.impl.AbstractProcedureMetric;
import eu.cloudwave.wp5.common.model.impl.MetricTypeImpl;
import eu.cloudwave.wp5.feedbackhandler.constants.DbTableNames;
import eu.cloudwave.wp5.feedbackhandler.model.db.DbApplication;
import eu.cloudwave.wp5.feedbackhandler.model.db.DbProcedureMetric;

/**
 * Implementation of {@link DbProcedureMetric}.
 */
@Document(collection = DbTableNames.METRICS)
public class DbProcedureMetricImpl extends AbstractProcedureMetric implements DbProcedureMetric {

  @Id
  private ObjectId id;

  @DBRef
  private DbApplication application;

  private MetricTypeImpl type;

  public DbProcedureMetricImpl(final DbApplication application, final MetricTypeImpl type, final Procedure procedure, final String additionalQualifier, final Number value) {
    super(procedure, additionalQualifier, value);
    this.application = application;
    this.type = type;
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
  public MetricTypeImpl getType() {
    return type;
  }

}
