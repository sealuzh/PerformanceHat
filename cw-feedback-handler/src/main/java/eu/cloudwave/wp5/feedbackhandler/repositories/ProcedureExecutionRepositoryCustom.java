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

import java.util.List;

import com.google.common.base.Optional;

import eu.cloudwave.wp5.feedbackhandler.aggregations.AggregatedInvocation;
import eu.cloudwave.wp5.feedbackhandler.aggregations.ClientRequestCollector;
import eu.cloudwave.wp5.feedbackhandler.aggregations.IncomingRequestCollector;

/**
 * This class extends the default spring repository with custom methods. It has to be named according to the naming
 * convention (see http://www.javabeat.net/spring-data-custom-repository/).
 */
public interface ProcedureExecutionRepositoryCustom {

  /**
   * Returns all microservice client requests and aggregated timestamps of every callee/caller/method name group
   * 
   * The appropriate query is:
   * 
   * <pre>
   * db.procedure_executions.aggregate([
   *       { $match : { 
   *           'procedure.annotations.name': /.*MicroserviceClientRequest./
   *       }
   *   },
   *   { 
   *      $group: {
   *          _id : { 
   *            caller: "$procedure.annotations.members.caller", 
   *            callee: "$procedure.annotations.members.callee", 
   *            methodName: "$procedure.name" 
   *            },
   *          requests: { $push: "$startTime" }
   *      }
   *   }
   *   ]);
   * </pre>
   * 
   * @return the aggregated requests per caller, callee and method name
   */
  public List<ClientRequestCollector> getAllRequests(final Long timeRangeFrom, final Long timeRangeTo);

  /**
   * Returns all incoming requests with timestamp and grouped by service identifier and method
   * 
   * @param timeRangeFrom
   * @param timeRangeTo
   * @return
   */
  public List<IncomingRequestCollector> getAllIncomingRequests(final Long timeRangeFrom, final Long timeRangeTo);

  /**
   * Returns incoming requests to the given identifier, grouped by service identifier and method
   * 
   * @param identifier
   * @param timeRangeFrom
   * @param timeRangeTo
   * @param groupByMethod
   *          true means requests are getting grouped by the service method
   * @return
   */
  public List<IncomingRequestCollector> getIncomingByIdentifier(final String identifier, final Long timeRangeFrom, final Long timeRangeTo, boolean groupByMethod);

  /**
   * Returns all microservice client requests and aggregated timestamps of every callee/caller/method name group
   * filtered by callee
   * 
   * The appropriate query is:
   * 
   * <pre>
   * db.procedure_executions.aggregate([
   *       { $match : { 
   *           'procedure.annotations.name': /.*MicroserviceClientRequest./,
   *           "procedure.annotations.members.caller" : 'eu.cloudwave.samples.services.currency'
   *       }
   *   },
   *   { 
   *      $group: {
   *          _id : { 
   *            caller: "$procedure.annotations.members.caller", 
   *            callee: "$procedure.annotations.members.callee", 
   *            methodName: "$procedure.name" 
   *            },
   *          requests: { $push: "$startTime" }
   *      }
   *   }
   *   ]);
   * </pre>
   * 
   * @param caller
   *          the microservice identifier of the service that receives the request (config.properties)
   * @return the aggregated requests filtered by callee
   */
  public List<ClientRequestCollector> getRequestsByCallee(final String callee, final Long timeRangeFrom, final Long timeRangeTo);

  /**
   * Returns all microservice client requests and aggregated timestamps of every callee/caller/method name group
   * filtered by caller
   * 
   * The appropriate query is:
   * 
   * <pre>
   * db.procedure_executions.aggregate([
   *       { $match : { 
   *           'procedure.annotations.name': /.*MicroserviceClientRequest./,
   *           "procedure.annotations.members.caller" : 'eu.cloudwave.samples.services.currency'
   *       }
   *   },
   *   { 
   *      $group: {
   *          _id : { 
   *            caller: "$procedure.annotations.members.caller", 
   *            callee: "$procedure.annotations.members.callee", 
   *            methodName: "$procedure.name" 
   *            },
   *          requests: { $push: "$startTime" }
   *      }
   *   }
   *   ]);
   * </pre>
   * 
   * @param caller
   *          the microservice identifier of the service that sends the request (config.properties)
   * @return the aggregated requests filtered by caller
   */
  public List<ClientRequestCollector> getRequestsByCaller(String caller, final Long timeRangeFrom, final Long timeRangeTo);

  /**
   * Returns an aggregated invocation with a list of procedure execution callers
   * 
   * @param invokedClassName
   * @param invokedMethodName
   * @param callerClassName
   * @param callerMethodName
   * @return {@link AggregatedInvocation}
   */
  public Optional<AggregatedInvocation> getCallersOfInvokedMethod(String invokedClassName, String invokedMethodName, String callerClassName, String callerMethodName);

  /*
   * This query is just used to illustrate how to manually write mongo queries TODO: remove asap
   */
  public List<ClientRequestCollector> basicQuery();
}
