package eu.cloudwave.wp5.feedbackhandler.repositories;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.google.common.base.Optional;

import eu.cloudwave.wp5.common.model.MetricType;
import eu.cloudwave.wp5.common.model.Procedure;
import eu.cloudwave.wp5.common.model.ProcedureExecutionMetric;
import eu.cloudwave.wp5.common.model.impl.MetricTypeImpl;
import eu.cloudwave.wp5.feedbackhandler.constants.DbTableNames;
import eu.cloudwave.wp5.feedbackhandler.model.db.DbApplication;
import eu.cloudwave.wp5.feedbackhandler.model.db.impl.DbProcedureExecutionMetricImpl;
import eu.cloudwave.wp5.feedbackhandler.repositories.aggregations.AggregatedAverage;
import eu.cloudwave.wp5.feedbackhandler.repositories.aggregations.ProcedureMetricAggregation;

/**
 * Implementation of {@link MetricRepositoryCustom}. It is named according to the naming conventions:
 * <ul>
 * <li>http://www.javabeat.net/spring-data-custom-repository/</li>
 * <li>http://stackoverflow.com/a/20789040/3721915</li>
 * </ul>
 */
public class MetricRepositoryImpl extends AbstractRepository implements MetricRepositoryCustom {

  @Autowired
  private MongoTemplate mongoTemplate;

  /**
   * {@inheritDoc}
   */
  @Override
  public List<? extends ProcedureExecutionMetric> find(final DbApplication application, final Procedure procedure) {
    return find(application, procedure.getClassName(), procedure.getName(), procedure.getArguments().toArray(new String[procedure.getArguments().size()]));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<? extends ProcedureExecutionMetric> find(final DbApplication application, final String className, final String procedureName, final String[] procedureArguments) {
    final Criteria criteria = new Criteria(APPLICATION).is(application).and(PROC__CLASS_NAME).is(className).and(PROC__NAME).is(procedureName).and(PROC__ARGUMENTS).is(procedureArguments);
    final Sort sort = new Sort(Sort.Direction.ASC, TIMESTAMP);
    final Query query = new Query(criteria).with(sort);
    return mongoTemplate.find(query, DbProcedureExecutionMetricImpl.class, DbTableNames.METRICS);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AggregationResults<ProcedureMetricAggregation> aggregateProcedureMetrics(final DbApplication application, final MetricType type) {
    final MatchOperation matchOperation = match(appCriteria(application).and(TYPE).is(type.toString()));
    final GroupOperation groupOperation = groupOperation(PROCEDURE);
    final SortOperation sortOperation = sort(Sort.Direction.DESC, AVERAGE_VALUE);
    final Aggregation executionTimeAggregationSpec = newAggregation(matchOperation, groupOperation, sortOperation);
    return aggregate(executionTimeAggregationSpec);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<Double> aggregateExecutionTime(final DbApplication application, final String className, final String name, final String[] arguments) {
    final String type = MetricTypeImpl.EXECUTION_TIME.toString();
    final List<AggregatedAverage> aggregationResults = aggregatedAverage(application, className, name, arguments, type).getMappedResults();
    if (!aggregationResults.isEmpty()) {
      return Optional.of(aggregationResults.get(0).getAverageValue());
    }
    return Optional.absent();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AggregationResults<AggregatedAverage> aggregateCollectionSizes(final DbApplication application, final String className, final String name, final String[] arguments) {
    final String type = MetricTypeImpl.COLLECTION_SIZE.toString();
    return aggregatedAverage(application, className, name, arguments, type);
  }

  private AggregationResults<AggregatedAverage> aggregatedAverage(final DbApplication application, final String className, final String name, final String[] arguments, final String type) {
    final MatchOperation matchOperation = match(appCriteria(application).and(TYPE).is(type).and(PROC__CLASS_NAME).is(className).and(PROC__NAME).is(name).and(PROC__ARGUMENTS).is(arguments));
    // for some reason PROCEDURE is also required in the grouping
    // (otherwise the aggregation is done correctly but the 'additionalQualifier' in the output-type is null)
    final GroupOperation groupOperation = groupOperation(PROCEDURE, ADDITIONAL_QUALIFIER);
    final Aggregation collectionSizeAggregationSpec = newAggregation(matchOperation, groupOperation);
    return aggregate(collectionSizeAggregationSpec, AggregatedAverage.class);
  }

  private AggregationResults<ProcedureMetricAggregation> aggregate(final Aggregation aggregation) {
    return aggregate(aggregation, ProcedureMetricAggregation.class);
  }

  private <O> AggregationResults<O> aggregate(final Aggregation aggregation, final Class<O> outputType) {
    return mongoTemplate.aggregate(aggregation, DbTableNames.METRICS, outputType);
  }
}
