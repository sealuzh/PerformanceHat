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
package eu.cloudwave.wp5.feedbackhandler.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import eu.cloudwave.wp5.common.constants.Headers;
import eu.cloudwave.wp5.common.constants.Urls;
import eu.cloudwave.wp5.common.dto.costs.AggregatedIncomingRequestsDto;
import eu.cloudwave.wp5.common.dto.costs.AggregatedMicroserviceRequestsDto;
import eu.cloudwave.wp5.common.util.AggregationIntervalConverter;
import eu.cloudwave.wp5.feedbackhandler.advices.CostDataProvider;
import eu.cloudwave.wp5.feedbackhandler.advices.IncomingRequestsDataProvider;
import eu.cloudwave.wp5.feedbackhandler.advices.InvocationDataProvider;
import eu.cloudwave.wp5.feedbackhandler.model.db.DbApplication;
import eu.cloudwave.wp5.feedbackhandler.repositories.ProcedureExecutionRepository;

@RestController
public class CostController extends AbstractBaseRestController {

  @Autowired
  private ProcedureExecutionRepository repository;

  @Autowired
  private CostDataProvider costDataProvider;

  @Autowired
  private IncomingRequestsDataProvider incomingRequestsDataProvider;

  @Autowired
  private InvocationDataProvider invocationDataProvider;

  @RequestMapping(Urls.COST__ALL)
  @ResponseStatus(HttpStatus.OK)
  public List<AggregatedMicroserviceRequestsDto> all(
      @RequestHeader(Headers.ACCESS_TOKEN) final String accessToken,
      @RequestHeader(Headers.APPLICATION_ID) final String applicationId,
      @RequestHeader(Headers.AGGREGATION_INTERVAL) final String aggregationInterval,
      @RequestHeader(value = Headers.TIME_RANGE_FROM, required = false) final Long timeRangeFrom,
      @RequestHeader(value = Headers.TIME_RANGE_TO, required = false) final Long timeRangeTo) {
    sampleLogger.info("Request to " + Urls.COST__ALL);
    return costDataProvider.getAllAggregatedRequests(AggregationIntervalConverter.fromString(aggregationInterval), timeRangeFrom, timeRangeTo);
  }

  @RequestMapping(Urls.COST__FILTER__CALLEE)
  @ResponseStatus(HttpStatus.OK)
  public List<AggregatedMicroserviceRequestsDto> byCallee(
      @RequestHeader(Headers.ACCESS_TOKEN) final String accessToken,
      @RequestHeader(Headers.APPLICATION_ID) final String applicationId,
      @RequestHeader(Headers.AGGREGATION_INTERVAL) final String aggregationInterval,
      @RequestHeader(value = Headers.TIME_RANGE_FROM, required = false) final Long timeRangeFrom,
      @RequestHeader(value = Headers.TIME_RANGE_TO, required = false) final Long timeRangeTo) {
    sampleLogger.info("Request to " + Urls.COST__FILTER__CALLEE);
    final DbApplication application = handleUnauthorized(applicationId, accessToken);
    return costDataProvider.getAggregatedRequestsByCallee(application, AggregationIntervalConverter.fromString(aggregationInterval), timeRangeFrom, timeRangeTo);
  }

  @RequestMapping(Urls.COST__FILTER__CALLEE__OVERALL)
  @ResponseStatus(HttpStatus.OK)
  public AggregatedMicroserviceRequestsDto overallByCallee(
      @RequestHeader(Headers.ACCESS_TOKEN) final String accessToken,
      @RequestHeader(Headers.APPLICATION_ID) final String applicationId,
      @RequestHeader(Headers.AGGREGATION_INTERVAL) final String aggregationInterval,
      @RequestHeader(value = Headers.TIME_RANGE_FROM, required = false) final Long timeRangeFrom,
      @RequestHeader(value = Headers.TIME_RANGE_TO, required = false) final Long timeRangeTo) {
    sampleLogger.info("Request to " + Urls.COST__FILTER__CALLEE__OVERALL);
    final DbApplication application = handleUnauthorized(applicationId, accessToken);
    return costDataProvider.getOverallAggregatedRequestsByCallee(application, AggregationIntervalConverter.fromString(aggregationInterval), timeRangeFrom, timeRangeTo);
  }

  @RequestMapping(Urls.COST__FILTER__CALLER)
  @ResponseStatus(HttpStatus.OK)
  public List<AggregatedMicroserviceRequestsDto> byCaller(
      @RequestHeader(Headers.ACCESS_TOKEN) final String accessToken,
      @RequestHeader(Headers.APPLICATION_ID) final String applicationId,
      @RequestHeader(Headers.AGGREGATION_INTERVAL) final String aggregationInterval,
      @RequestHeader(value = Headers.TIME_RANGE_FROM, required = false) final Long timeRangeFrom,
      @RequestHeader(value = Headers.TIME_RANGE_TO, required = false) final Long timeRangeTo) {
    sampleLogger.info("Request to " + Urls.COST__FILTER__CALLER);
    final DbApplication application = handleUnauthorized(applicationId, accessToken);
    return costDataProvider.getAggregatedRequestsByCaller(application, AggregationIntervalConverter.fromString(aggregationInterval), timeRangeFrom, timeRangeTo);
  }

  @RequestMapping(Urls.COST__INCOMING__ALL)
  @ResponseStatus(HttpStatus.OK)
  public List<AggregatedIncomingRequestsDto> allIncoming(
      @RequestHeader(Headers.ACCESS_TOKEN) final String accessToken,
      @RequestHeader(Headers.APPLICATION_ID) final String applicationId,
      @RequestHeader(Headers.AGGREGATION_INTERVAL) final String aggregationInterval,
      @RequestHeader(value = Headers.TIME_RANGE_FROM, required = false) final Long timeRangeFrom,
      @RequestHeader(value = Headers.TIME_RANGE_TO, required = false) final Long timeRangeTo) {
    sampleLogger.info("Request to " + Urls.COST__INCOMING__ALL);
    return incomingRequestsDataProvider.getAllIncomingRequests(AggregationIntervalConverter.fromString(aggregationInterval), timeRangeFrom, timeRangeTo);
  }

  @RequestMapping(Urls.COST__INCOMING__FILTER__IDENTIFIER)
  @ResponseStatus(HttpStatus.OK)
  public List<AggregatedIncomingRequestsDto> incomingByIdentifier(
      @RequestHeader(Headers.ACCESS_TOKEN) final String accessToken,
      @RequestHeader(Headers.APPLICATION_ID) final String applicationId,
      @RequestHeader(Headers.AGGREGATION_INTERVAL) final String aggregationInterval,
      @RequestHeader(value = Headers.REQUESTED_APPLICATION_ID, required = false) final String requestedApplicationId,
      @RequestHeader(value = Headers.TIME_RANGE_FROM, required = false) final Long timeRangeFrom,
      @RequestHeader(value = Headers.TIME_RANGE_TO, required = false) final Long timeRangeTo) {
    sampleLogger.info("Request to " + Urls.COST__INCOMING__FILTER__IDENTIFIER);
    DbApplication application;
    if (requestedApplicationId != null) {
      // only use access token and associated application id for authorization
      handleUnauthorized(applicationId, accessToken);

      // use the requested application id for db queries
      application = applicationRepository.findOne(requestedApplicationId);

      // does the requested application exist?
      if (application == null)
        return null;
    }
    else {
      application = handleUnauthorized(applicationId, accessToken);
    }
    return incomingRequestsDataProvider.getIncomingRequestsByIdentifier(application, AggregationIntervalConverter.fromString(aggregationInterval), timeRangeFrom, timeRangeTo);
  }

  @RequestMapping(Urls.COST__INCOMING__FILTER__IDENTIFIER__OVERALL)
  @ResponseStatus(HttpStatus.OK)
  public AggregatedIncomingRequestsDto incomingByIdentifierOverall(
      @RequestHeader(Headers.ACCESS_TOKEN) final String accessToken,
      @RequestHeader(Headers.APPLICATION_ID) final String applicationId,
      @RequestHeader(Headers.AGGREGATION_INTERVAL) final String aggregationInterval,
      @RequestHeader(value = Headers.REQUESTED_APPLICATION_ID, required = false) final String requestedApplicationId,
      @RequestHeader(value = Headers.TIME_RANGE_FROM, required = false) final Long timeRangeFrom,
      @RequestHeader(value = Headers.TIME_RANGE_TO, required = false) final Long timeRangeTo) {
    sampleLogger.info("Request to " + Urls.COST__INCOMING__FILTER__IDENTIFIER__OVERALL);
    DbApplication application;
    if (requestedApplicationId == null) {
      application = handleUnauthorized(applicationId, accessToken);
    }
    else {
      // only use access token and associated application id for authorization
      handleUnauthorized(applicationId, accessToken);

      // use the requested application id for db queries
      application = applicationRepository.findOne(requestedApplicationId);

      // does the requested application exist?
      if (application == null)
        return null;
    }
    return incomingRequestsDataProvider.getOverallIncomingRequestsByIdentifier(application, AggregationIntervalConverter.fromString(aggregationInterval), timeRangeFrom, timeRangeTo);
  }

  @RequestMapping(Urls.COST__INVOCATION__CHECK)
  @ResponseStatus(HttpStatus.OK)
  public Boolean isNewlyInvoked(
      @RequestHeader(Headers.ACCESS_TOKEN) final String accessToken,
      @RequestHeader(Headers.APPLICATION_ID) final String applicationId,
      @RequestHeader(Headers.INVOKED_CLASS) final String invokedClassName,
      @RequestHeader(Headers.INVOKED_METHOD) final String invokedMethodName,
      @RequestHeader(Headers.CALLER_CLASS) final String callerClassName,
      @RequestHeader(Headers.CALLER_METHOD) final String callerMethodName) {
    sampleLogger.info("Request to " + Urls.COST__INVOCATION__CHECK);
    return invocationDataProvider.isNewlyInvoked(invokedClassName, invokedMethodName, callerClassName, callerMethodName);
  }
}
