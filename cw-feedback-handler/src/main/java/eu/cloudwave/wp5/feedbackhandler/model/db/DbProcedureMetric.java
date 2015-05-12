package eu.cloudwave.wp5.feedbackhandler.model.db;

import org.springframework.data.mongodb.core.mapping.Document;

import eu.cloudwave.wp5.common.model.ProcedureMetric;
import eu.cloudwave.wp5.feedbackhandler.constants.DbTableNames;

/**
 * MongoDB-specific extension of {@link ProcedureMetric}.
 */
@Document(collection = DbTableNames.METRICS)
public interface DbProcedureMetric extends ProcedureMetric, DbMetric {

}
