package eu.cloudwave.wp5.feedbackhandler.repositories;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;

import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.query.Criteria;

import eu.cloudwave.wp5.common.constants.Ids;
import eu.cloudwave.wp5.feedbackhandler.model.db.DbApplication;

/*
 * Abstract repository used in concrete implementations to share helper methods and constants
 */
public abstract class AbstractRepository {

  protected static final String DOT = ".";
  protected static final String TYPE = "type";
  protected static final String VALUE = "value";
  protected static final String APPLICATION = "application";
  protected static final String PROCEDURE = "procedure";
  protected static final String ADDITIONAL_QUALIFIER = "additionalQualifier";
  protected static final String PROC__CLASS_NAME = PROCEDURE + DOT + "className";
  protected static final String PROC__NAME = PROCEDURE + DOT + "name";
  protected static final String PROC__ARGUMENTS = PROCEDURE + DOT + "arguments";
  protected static final String TIMESTAMP = "timestamp";
  protected static final String APPLICATION__ID = APPLICATION + DOT + "$id";
  protected static final String AVERAGE_VALUE = "averageValue";

  protected static final String ANNOTATIONS = PROCEDURE + DOT + "annotations";
  protected static final String ANNOTATION_NAME = ANNOTATIONS + DOT + "name";
  protected static final String ANNOTATION_FROM_ATTRIBUTE = ANNOTATIONS + DOT + "members" + DOT + Ids.MICROSERVICE_CLIENT_REQUEST_ANNOTATION_FROM_ATTRIBUTE; // caller
  protected static final String ANNOTATION_TO_ATTRIBUTE = ANNOTATIONS + DOT + "members" + DOT + Ids.MICROSERVICE_CLIENT_REQUEST_ANNOTATION_TO_ATTRIBUTE; // callee

  protected static final String METHOD_ATTRIBUTE = PROCEDURE + DOT + "name";
  protected static final String METHOD_PROJECTION = "methodName";

  protected static final String TIME_VALUE = "$startTime";
  protected static final String TIME_PROJECTION = "timestamp";
  protected static final String TIME_AGGREGATION_ATTRIBUTE = "reqTimestamps";

  protected Criteria appCriteria(final DbApplication application) {
    // Hint: Fields of DBRefs cannot be accessed directly in a query:
    // http://stackoverflow.com/questions/17973321/querying-mongodb-dbref-inner-field
    return new Criteria(APPLICATION__ID).is(application.getId());
  }

  protected GroupOperation groupOperation(final String... fields) {
    return group(fields).avg(VALUE).as(AVERAGE_VALUE);
  }
}
