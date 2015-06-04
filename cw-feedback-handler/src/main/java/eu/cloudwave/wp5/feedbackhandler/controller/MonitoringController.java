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
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import eu.cloudwave.wp5.common.constants.Headers;
import eu.cloudwave.wp5.common.constants.Params;
import eu.cloudwave.wp5.common.constants.Urls;
import eu.cloudwave.wp5.common.dto.AccessTokenDto;
import eu.cloudwave.wp5.common.dto.ApplicationDto;
import eu.cloudwave.wp5.common.dto.model.MetricContainingProcedureExecutionDto;
import eu.cloudwave.wp5.common.error.RequestException;
import eu.cloudwave.wp5.feedbackhandler.model.db.DbApplication;
import eu.cloudwave.wp5.feedbackhandler.model.db.DbCostApplication;
import eu.cloudwave.wp5.feedbackhandler.model.factories.ApplicationFactory;
import eu.cloudwave.wp5.feedbackhandler.service.MonitoringDataService;

@RestController
public class MonitoringController extends AbstractBaseRestController {

  private static final String SPACE = " ";
  private static final String ERROR_MSG__APP_ID_CONTAINS_SPACES = "The application ID may not contain spaces.";
  private static final String ERROR_MSG__APP_ID_ALREADY_EXISTS = "The chosen application ID already exists. Please choose another one";

  @Autowired
  private ApplicationFactory applicationFactory;

  @Autowired
  private MonitoringDataService monitoringDataService;

  @RequestMapping(value = Urls.MONITORING__DATA, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public Boolean data(
      @RequestHeader(Headers.ACCESS_TOKEN) final String accessToken,
      @RequestHeader(Headers.APPLICATION_ID) final String applicationId,
      @RequestBody final MetricContainingProcedureExecutionDto procedureExecutionDto) {
    final DbApplication application = handleUnauthorized(applicationId, accessToken);

    // TODO: remove print
    // ProcedureExecutionPrinter.print(procedureExecutionDto, false);

    monitoringDataService.persist(application, procedureExecutionDto);

    return true;
  }

  @RequestMapping(Urls.MONITORING__REGISTER)
  @ResponseStatus(HttpStatus.OK)
  public AccessTokenDto register(@RequestParam(value = Params.APPLICATION_ID, required = true) final String applicationId) {
    checkApplicationId(applicationId);
    final DbApplication application = applicationFactory.create(applicationId);
    applicationRepository.save(application);
    return new AccessTokenDto(application.getAccessToken());
  }

  @RequestMapping(Urls.MONITORING__LOGIN)
  @ResponseStatus(HttpStatus.OK)
  public ApplicationDto login(@RequestHeader(Headers.ACCESS_TOKEN) final String accessToken, @RequestHeader(Headers.APPLICATION_ID) final String applicationId) {
    final DbCostApplication app = handleUnauthorizedCostApplication(applicationId, accessToken);
    return new ApplicationDto(app.getApplicationId(), app.getAccessToken(), app.getInstances(), app.getPricePerInstanceInUSD(), app.getMaxRequestsPerInstancePerSecond());
  }

  @RequestMapping(Urls.MONITORING__APPLICATION)
  @ResponseStatus(HttpStatus.OK)
  public ApplicationDto application(
      @RequestHeader(Headers.ACCESS_TOKEN) final String accessToken,
      @RequestHeader(Headers.APPLICATION_ID) final String applicationId,
      @RequestHeader(Headers.REQUESTED_APPLICATION_ID) final String requestedApplicationId) {
    handleUnauthorized(applicationId, accessToken);
    final DbCostApplication app = applicationRepository.findOneCostApplication(requestedApplicationId);
    return new ApplicationDto(app.getApplicationId(), app.getAccessToken(), app.getInstances(), app.getPricePerInstanceInUSD(), app.getMaxRequestsPerInstancePerSecond());
  }

  @RequestMapping(value = Urls.MONITORING__UPDATE, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public void data(@RequestBody final ApplicationDto updatedApplication) {
    handleUnauthorized(updatedApplication.getApplicationId(), updatedApplication.getAccessToken());

    final DbCostApplication application = applicationRepository.findOneCostApplication(updatedApplication.getApplicationId());
    application.setInstances(updatedApplication.getInstances());
    application.setMaxRequestsPerInstancePerSecond(updatedApplication.getMaxRequestsPerInstancePerSecond());
    application.setPricePerInstanceInUSD(updatedApplication.getPricePerInstanceInUSD());
    applicationRepository.save(application);
  }

  @RequestMapping(Urls.MONITORING__APPLICATIONS)
  @ResponseStatus(HttpStatus.OK)
  public ApplicationDto[] applications(@RequestHeader(Headers.ACCESS_TOKEN) final String accessToken, @RequestHeader(Headers.APPLICATION_ID) final String applicationId) {
    // Keep in mind: any registered application is able to get a list of all other registered applications
    // This only makes sense in the context of prototyping and demo applications
    handleUnauthorized(applicationId, accessToken);

    List<? extends DbCostApplication> allApplications = applicationRepository.findAllCostApplications();

    // create DTO and return as array
    return allApplications.stream()
        .map(app -> new ApplicationDto(app.getApplicationId(), app.getAccessToken(), app.getInstances(), app.getPricePerInstanceInUSD(), app.getMaxRequestsPerInstancePerSecond()))
        .toArray(size -> new ApplicationDto[size]);
  }

  private void checkApplicationId(final String applicationId) {
    if (applicationId.contains(SPACE)) {
      throw new RequestException(ERROR_MSG__APP_ID_CONTAINS_SPACES);
    }
    else if (applicationRepository.findOne(applicationId) != null) {
      throw new RequestException(ERROR_MSG__APP_ID_ALREADY_EXISTS);
    }
  }
}
