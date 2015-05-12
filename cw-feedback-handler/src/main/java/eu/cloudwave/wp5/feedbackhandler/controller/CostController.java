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
package eu.cloudwave.wp5.feedbackhandler.controller;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import eu.cloudwave.wp5.common.constants.Headers;
import eu.cloudwave.wp5.common.constants.Urls;
import eu.cloudwave.wp5.common.dto.AggregatedMicroserviceRequestsDto;
import eu.cloudwave.wp5.feedbackhandler.advices.CostDataProvider;
import eu.cloudwave.wp5.feedbackhandler.model.db.DbApplication;
import eu.cloudwave.wp5.feedbackhandler.repositories.ProcedureExecutionRepository;

@RestController
public class CostController extends AbstractBaseRestController {

  @Autowired
  private ProcedureExecutionRepository repository;

  @Autowired
  private CostDataProvider costDataProvider;

  @RequestMapping(Urls.COST__ALL)
  @ResponseStatus(HttpStatus.OK)
  public List<AggregatedMicroserviceRequestsDto> all(
      @RequestHeader(Headers.ACCESS_TOKEN) final String accessToken,
      @RequestHeader(Headers.APPLICATION_ID) final String applicationId,
      @RequestHeader(Headers.AGGREGATION_INTERVAL) final String aggregationInterval) {
    final int aggregationIntervalCalendarInt = getAggregationIntervalCalendarInt(aggregationInterval);
    return costDataProvider.getAllAggregatedRequests(aggregationIntervalCalendarInt);
  }

  @RequestMapping(Urls.COST__FILTER__CALLEE)
  @ResponseStatus(HttpStatus.OK)
  public List<AggregatedMicroserviceRequestsDto> byCallee(
      @RequestHeader(Headers.ACCESS_TOKEN) final String accessToken,
      @RequestHeader(Headers.APPLICATION_ID) final String applicationId,
      @RequestHeader(Headers.AGGREGATION_INTERVAL) final String aggregationInterval) {
    handleUnauthorized(applicationId, accessToken);
    final DbApplication application = handleUnauthorized(applicationId, accessToken);
    final int aggregationIntervalCalendarInt = getAggregationIntervalCalendarInt(aggregationInterval);
    return costDataProvider.getAggregatedRequestsByCallee(application, aggregationIntervalCalendarInt);
  }

  @RequestMapping(Urls.COST__FILTER__CALLER)
  @ResponseStatus(HttpStatus.OK)
  public List<AggregatedMicroserviceRequestsDto> byCaller(
      @RequestHeader(Headers.ACCESS_TOKEN) final String accessToken,
      @RequestHeader(Headers.APPLICATION_ID) final String applicationId,
      @RequestHeader(Headers.AGGREGATION_INTERVAL) final String aggregationInterval) {
    handleUnauthorized(applicationId, accessToken);
    final DbApplication application = handleUnauthorized(applicationId, accessToken);
    final int aggregationIntervalCalendarInt = getAggregationIntervalCalendarInt(aggregationInterval);
    return costDataProvider.getAggregatedRequestsByCaller(application, aggregationIntervalCalendarInt);
  }

  /*
   * Helper function that takes the aggregation time interval string and returns a valid calendar-specific int from
   * {@link java.util.Calendar}
   */
  private final int getAggregationIntervalCalendarInt(String aggregationIntervalHeaderString) {
    switch (aggregationIntervalHeaderString) {
      case "month":
        return Calendar.MONTH;
      case "hour":
        return Calendar.HOUR;
      case "minute":
        return Calendar.MINUTE;
      case "second":
        return Calendar.SECOND;
      default:
        return Calendar.HOUR;
    }
  }
}
