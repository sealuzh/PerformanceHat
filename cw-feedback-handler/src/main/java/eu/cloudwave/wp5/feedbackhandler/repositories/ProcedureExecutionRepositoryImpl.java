/*******************************************************************************
 * Copyright 2015 Software Evolution and Architecture Lab, University of Zurich
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 ******************************************************************************/
package eu.cloudwave.wp5.feedbackhandler.repositories;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;

import com.google.common.base.Optional;

import eu.cloudwave.wp5.common.constants.Ids;
import eu.cloudwave.wp5.feedbackhandler.aggregations.AggregatedInvocation;
import eu.cloudwave.wp5.feedbackhandler.aggregations.ClientRequestCollector;
import eu.cloudwave.wp5.feedbackhandler.aggregations.IncomingRequestCollector;
import eu.cloudwave.wp5.feedbackhandler.constants.DbTableNames;

public class ProcedureExecutionRepositoryImpl extends AbstractRepository implements ProcedureExecutionRepositoryCustom {

  @Autowired
  private MongoTemplate mongoTemplate;

  /**
   * {@inheritDoc}
   */
  @Override
  public List<ClientRequestCollector> getAllRequests(final Long timeRangeFrom, final Long timeRangeTo) {
    Criteria matchCriteria = getClientRequestCriteria();
    matchCriteria = addTimeRangeCriteria(matchCriteria, timeRangeFrom, timeRangeTo);

    return getRequestsWithCriteria(matchCriteria);
  }

  /**
   * {@inheritDoc}
   */
  public List<IncomingRequestCollector> getAllIncomingRequests(final Long timeRangeFrom, final Long timeRangeTo) {
    Criteria matchCriteria = getMicroserviceDeclarationCriteria();
    matchCriteria = addTimeRangeCriteria(matchCriteria, timeRangeFrom, timeRangeTo);

    return getIncomingRequestsWithCriteria(matchCriteria, true);
  }

  /**
   * {@inheritDoc}
   */
  public List<IncomingRequestCollector> getIncomingByIdentifier(final String identifier, final Long timeRangeFrom, final Long timeRangeTo, boolean groupByMethod) {
    Criteria matchCriteria = getMicroserviceDeclarationCriteria();
    matchCriteria = matchCriteria.and(ANNOTATION_IDENTIFIER_ATTRIBUTE).is(identifier);
    matchCriteria = addTimeRangeCriteria(matchCriteria, timeRangeFrom, timeRangeTo);

    return getIncomingRequestsWithCriteria(matchCriteria, groupByMethod);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<ClientRequestCollector> getRequestsByCallee(final String callee, final Long timeRangeFrom, final Long timeRangeTo) {
    Criteria matchCriteria = getClientRequestCriteria();
    matchCriteria = matchCriteria.and(ANNOTATION_TO_ATTRIBUTE).is(callee);
    matchCriteria = addTimeRangeCriteria(matchCriteria, timeRangeFrom, timeRangeTo);

    return getRequestsWithCriteria(matchCriteria);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<ClientRequestCollector> getRequestsByCaller(String caller, final Long timeRangeFrom, final Long timeRangeTo) {
    Criteria matchCriteria = getClientRequestCriteria();
    matchCriteria = matchCriteria.and(ANNOTATION_FROM_ATTRIBUTE).is(caller);
    matchCriteria = addTimeRangeCriteria(matchCriteria, timeRangeFrom, timeRangeTo);

    return getRequestsWithCriteria(matchCriteria);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<AggregatedInvocation> getCallersOfInvokedMethod(String invokedClassName, String invokedMethodName, String callerClassName, String callerMethodName) {
    // objects have to have the same method name + class name
    final MatchOperation matchOperation = match(new Criteria(PROC__NAME).is(invokedMethodName).and(PROC__CLASS_NAME).is(invokedClassName));

    /*
     * add all distinct callers of the given invoked method to set
     * 
     * Fields.from(Fields.field(<NEW ATTRIBUTE NAME>, <ORIGINAL ATTRIBUTE NAME>))
     */
    final GroupOperation groupOperation = group(Fields.from(Fields.field(INVOKED_METHOD_NAME_PROJECTION, PROC__NAME)).and(INVOKED_CLASS_NAME_PROJECTION, PROC__CLASS_NAME)).addToSet("$" + CALLER).as(
        CALLER_AGGREGATION_ATTRIBUTE);

    List<AggregatedInvocation> aggregationResults = aggregateProcedureExecution(newAggregation(matchOperation, groupOperation), AggregatedInvocation.class).getMappedResults();
    if (!aggregationResults.isEmpty()) {
      return Optional.of(aggregationResults.get(0));
    }
    return Optional.absent();
  }

  /**
   * Criteria that filters requests with @MicroserviceClientMethodDeclaration annotation
   * 
   * @return {@link Criteria}
   */
  private Criteria getClientRequestCriteria() {
    return new Criteria(ANNOTATION_NAME).is(Ids.MICROSERVICE_CLIENT_REQUEST_ANNOTATION_IDENTIFIER);
  }

  /**
   * Criteria that filters requests with @MicroserviceMethodDeclaration annotation.
   * 
   * @return {@link Criteria}
   */
  private Criteria getMicroserviceDeclarationCriteria() {
    return new Criteria(ANNOTATION_NAME).is(Ids.MICROSERVICE_ENDPOINT_ANNOTATION_IDENTIFIER);
  }

  /**
   * Helper that builds and runs the query. It creates a Match Operation, Group Operation and the Aggregation. The
   * GroupOperation groups by caller, callee, calleeMethod and method name.
   * 
   * @param matchCriteria
   *          {@link Criteria} which specifies the annotation and other filters
   * @return {@link ClientRequestCollector} a list of matching requests
   */
  private List<ClientRequestCollector> getRequestsWithCriteria(Criteria matchCriteria) {

    // create match operation based on input
    final MatchOperation matchOperation = match(matchCriteria);

    // 1. group by caller
    Fields fields = Fields.from(Fields.field(Ids.MICROSERVICE_CLIENT_REQUEST_ANNOTATION_FROM_ATTRIBUTE, "$" + ANNOTATION_FROM_ATTRIBUTE));

    // 2. group by callee
    fields = fields.and(Ids.MICROSERVICE_CLIENT_REQUEST_ANNOTATION_TO_ATTRIBUTE, "$" + ANNOTATION_TO_ATTRIBUTE);

    // 3. group by callee method
    fields = fields.and(Ids.MICROSERVICE_CLIENT_REQUEST_ANNOTATION_TO_METHOD_ATTRIBUTE, "$" + ANNOTATION_TO_METHOD_ATTRIBUTE);

    // 4. group by name of method that contains the invocation
    fields = fields.and(METHOD_PROJECTION, "$" + METHOD_ATTRIBUTE);

    // create group operation
    final GroupOperation groupOperation = group(fields).push("$" + TIME_FIELD).as(TIME_AGGREGATION_ATTRIBUTE);
    final Aggregation microserviceClientAggregationSpec = newAggregation(matchOperation, groupOperation);

    return aggregateProcedureExecution(microserviceClientAggregationSpec, ClientRequestCollector.class).getMappedResults();
  }

  /**
   * Helper that builds and runs the query. It creates a Match Operation, Group Operation and the Aggregation. The
   * GroupOperation groups by caller, callee, calleeMethod and method name.
   * 
   * @param matchCriteria
   *          {@link Criteria} which specifies the annotation and other filters
   * @return {@link IncomingRequestCollector} a list of matching requests
   */
  private List<IncomingRequestCollector> getIncomingRequestsWithCriteria(Criteria matchCriteria, boolean groupByMethod) {
    // create match operation based on input
    final MatchOperation matchOperation = match(matchCriteria);

    // 1. group by service identifier
    Fields fields = Fields.from(Fields.field(Ids.MICROSERVICE_ENDPOINT_ANNOTATION_IDENTIFIER_ATTRIBUTE, "$" + ANNOTATION_IDENTIFIER_ATTRIBUTE));

    // 2. group by service method
    if (groupByMethod) {
      fields = fields.and(Ids.MICROSERVICE_DECLARATION_ANNOTATION_METHOD_ATTRIBUTE, "$" + ANNOTATION_METHOD_ATTRIBUTE);
    }

    // create group operation and push all timestamps into a list
    final GroupOperation groupOperation = group(fields).push("$" + TIME_FIELD).as(TIME_AGGREGATION_ATTRIBUTE);
    final Aggregation microserviceClientAggregationSpec = newAggregation(matchOperation, groupOperation);

    return aggregateProcedureExecution(microserviceClientAggregationSpec, IncomingRequestCollector.class).getMappedResults();
  }

  /**
   * Helper method that adds time criteria to given match criteria
   * 
   * @param criteria
   * @param timeRangeFrom
   * @param timeRangeTo
   * @return criteria
   */
  private Criteria addTimeRangeCriteria(Criteria criteria, Long timeRangeFrom, Long timeRangeTo) {
    if (timeRangeFrom == null && timeRangeTo == null) {
      return criteria;
    }
    else if (timeRangeFrom != null && timeRangeTo == null) {
      // greater than or equal
      return criteria.and(TIME_FIELD).gte(timeRangeFrom);
    }
    else if (timeRangeFrom == null && timeRangeTo != null) {
      // less than or equal
      return criteria.and(TIME_FIELD).lte(timeRangeTo);
    }
    else {
      // We have to use the andOperator instead of and() due to limitations of the com.mongodb.BasicDBObject when it
      // comes to multiple criteria on the same field (TIME_VALUE)
      return criteria.andOperator(Criteria.where(TIME_FIELD).gte(timeRangeFrom), Criteria.where(TIME_FIELD).lte(timeRangeTo));
    }
  }

  /**
   * Helper that aggregates ProcedureExecutions
   * 
   * @param aggregation
   * @param outputType
   * @return
   */
  private <O> AggregationResults<O> aggregateProcedureExecution(final Aggregation aggregation, final Class<O> outputType) {
    return mongoTemplate.aggregate(aggregation, DbTableNames.PROCEDURE_EXECUTIONS, outputType);
  }

  /**
   * {@inheritDoc} TODO: remove when not needed anymore
   */
  @Override
  public List<ClientRequestCollector> basicQuery() {
    List<ClientRequestCollector> result = mongoTemplate
        .find(
            new BasicQuery(
                "[{ '$match' : { 'procedure.annotations.name': { $regex: '.*MicroserviceClientRequest' }, 'procedure.annotations.members.caller' : 'eu.cloudwave.samples.services.currency'}},{ $project : {     'startTimeGrouping' : { '$subtract' : [ { $divide : ['$startTime', 3600 ]}, { $mod : [{ $divide : ['$startTime', 3600 ]},1] } ] },'caller': '$procedure.annotations.members.caller','callee': '$procedure.annotations.members.callee'} }]"),
            ClientRequestCollector.class);
    return result;
  }
}
