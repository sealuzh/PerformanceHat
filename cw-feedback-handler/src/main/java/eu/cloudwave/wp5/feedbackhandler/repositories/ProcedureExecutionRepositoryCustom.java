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

import java.util.List;

import eu.cloudwave.wp5.feedbackhandler.repositories.aggregations.MicroserviceClientRequest;

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
  public List<MicroserviceClientRequest> getAllRequests();

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
  public List<MicroserviceClientRequest> getRequestsByCallee(String callee);

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
  public List<MicroserviceClientRequest> getRequestsByCaller(String caller);

  /*
   * This query is just used to illustrate how to manually write mongo queries TODO: remove asap
   */
  public List<MicroserviceClientRequest> basicQuery();
}
