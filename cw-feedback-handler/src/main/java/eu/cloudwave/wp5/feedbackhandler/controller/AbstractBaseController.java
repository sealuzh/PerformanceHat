package eu.cloudwave.wp5.feedbackhandler.controller;

import org.springframework.beans.factory.annotation.Autowired;

import eu.cloudwave.wp5.common.error.ErrorType;
import eu.cloudwave.wp5.common.error.RequestException;
import eu.cloudwave.wp5.feedbackhandler.model.db.DbApplication;
import eu.cloudwave.wp5.feedbackhandler.repositories.ApplicationRepository;

public abstract class AbstractBaseController {

  protected static final String EXCEPTION_MESSAGE = "Error executing request to '%s'";
  private static final String INVALID_APPLICATION_ID_ERROR_MSG = "Invalid Application-ID.";
  private static final String INVALID_ACCESS_TOKEN_ERROR_MSG = "Wrong Access-Token specified.";

  @Autowired
  protected ApplicationRepository applicationRepository;

  protected final DbApplication handleUnauthorized(final String applicationId, final String accessToken) {
    final DbApplication application = applicationRepository.findOne(applicationId);
    if (application == null) {
      throw new RequestException(ErrorType.INVALID_APPLICATION_ID, INVALID_APPLICATION_ID_ERROR_MSG);
    }
    else if (!application.getAccessToken().equals(accessToken)) {
      throw new RequestException(ErrorType.WRONG_ACCESS_TOKEN, INVALID_ACCESS_TOKEN_ERROR_MSG);
    }
    return application;
  }
}
