package eu.cloudwave.wp5.feedbackhandler.model.db;

import org.springframework.data.mongodb.core.mapping.Document;

import eu.cloudwave.wp5.common.model.Metric;
import eu.cloudwave.wp5.common.model.impl.MetricTypeImpl;
import eu.cloudwave.wp5.feedbackhandler.constants.DbTableNames;

/**
 * MongoDB-specific extension of {@link Metric}.
 */
@Document(collection = DbTableNames.METRICS)
public interface DbMetric extends Metric, DbEntity {

  public DbApplication getApplication();

  /**
   * {@inheritDoc}
   */
  @Override
  public MetricTypeImpl getType();

}
