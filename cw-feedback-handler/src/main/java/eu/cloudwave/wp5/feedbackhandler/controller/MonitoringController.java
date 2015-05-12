package eu.cloudwave.wp5.feedbackhandler.controller;

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
import eu.cloudwave.wp5.common.dto.model.MetricContainingProcedureExecutionDto;
import eu.cloudwave.wp5.common.error.RequestException;
import eu.cloudwave.wp5.feedbackhandler.model.db.DbApplication;
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

  private void checkApplicationId(final String applicationId) {
    if (applicationId.contains(SPACE)) {
      throw new RequestException(ERROR_MSG__APP_ID_CONTAINS_SPACES);
    }
    else if (applicationRepository.findOne(applicationId) != null) {
      throw new RequestException(ERROR_MSG__APP_ID_ALREADY_EXISTS);
    }
  }
}
