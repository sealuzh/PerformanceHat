/*******************************************************************************
 * Copyright 2015 Software Evolution and Architecture Lab, University of Zurich
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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

import eu.cloudwave.wp5.common.constants.Ids;
import eu.cloudwave.wp5.feedbackhandler.constants.DbTableNames;
import eu.cloudwave.wp5.feedbackhandler.repositories.aggregations.MicroserviceClientRequest;

public class ProcedureExecutionRepositoryImpl extends AbstractRepository implements ProcedureExecutionRepositoryCustom {

  @Autowired
  private MongoTemplate mongoTemplate;

  private <O> AggregationResults<O> aggregateProcedureExecution(final Aggregation aggregation, final Class<O> outputType) {
    return mongoTemplate.aggregate(aggregation, DbTableNames.PROCEDURE_EXECUTIONS, outputType);
  }

  /**
   * Creates a Microservice Request GroupOperation that groups by caller, callee and method name
   * 
   * @return {@link GroupOperation}
   */
  private final GroupOperation getRequestGroupOperation() {

    return group(
        Fields.from(Fields.field(Ids.MICROSERVICE_CLIENT_REQUEST_ANNOTATION_FROM_ATTRIBUTE, "$" + ANNOTATION_FROM_ATTRIBUTE))
            .and(Ids.MICROSERVICE_CLIENT_REQUEST_ANNOTATION_TO_ATTRIBUTE, "$" + ANNOTATION_TO_ATTRIBUTE).and(METHOD_PROJECTION, "$" + METHOD_ATTRIBUTE)).push(TIME_VALUE)
        .as(TIME_AGGREGATION_ATTRIBUTE);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<MicroserviceClientRequest> getAllRequests() {
    final MatchOperation matchOperation = match(new Criteria(ANNOTATION_NAME).is(Ids.MICROSERVICE_CLIENT_REQUEST_ANNOTATION_IDENTIFIER));
    final GroupOperation groupOperation = this.getRequestGroupOperation();
    final Aggregation microserviceClientAggregationSpec = newAggregation(matchOperation, groupOperation);

    return aggregateProcedureExecution(microserviceClientAggregationSpec, MicroserviceClientRequest.class).getMappedResults();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<MicroserviceClientRequest> getRequestsByCallee(String callee) {
    final MatchOperation matchOperation = match(new Criteria(ANNOTATION_NAME).is(Ids.MICROSERVICE_CLIENT_REQUEST_ANNOTATION_IDENTIFIER).and(ANNOTATION_TO_ATTRIBUTE).is(callee));
    final GroupOperation groupOperation = this.getRequestGroupOperation();
    final Aggregation microserviceClientAggregationSpec = newAggregation(matchOperation, groupOperation);

    return aggregateProcedureExecution(microserviceClientAggregationSpec, MicroserviceClientRequest.class).getMappedResults();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<MicroserviceClientRequest> getRequestsByCaller(String caller) {
    final MatchOperation matchOperation = match(new Criteria(ANNOTATION_NAME).is(Ids.MICROSERVICE_CLIENT_REQUEST_ANNOTATION_IDENTIFIER).and(ANNOTATION_FROM_ATTRIBUTE).is(caller));
    final GroupOperation groupOperation = this.getRequestGroupOperation();
    final Aggregation microserviceClientAggregationSpec = newAggregation(matchOperation, groupOperation);

    return aggregateProcedureExecution(microserviceClientAggregationSpec, MicroserviceClientRequest.class).getMappedResults();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<MicroserviceClientRequest> basicQuery() {
    List<MicroserviceClientRequest> result = mongoTemplate
        .find(
            new BasicQuery(
                "[{ '$match' : { 'procedure.annotations.name': { $regex: '.*MicroserviceClientRequest' }, 'procedure.annotations.members.caller' : 'eu.cloudwave.samples.services.currency'}},{ $project : {     'startTimeGrouping' : { '$subtract' : [ { $divide : ['$startTime', 3600 ]}, { $mod : [{ $divide : ['$startTime', 3600 ]},1] } ] },'caller': '$procedure.annotations.members.caller','callee': '$procedure.annotations.members.callee'} }]"),
            MicroserviceClientRequest.class);
    return result;
  }
}
