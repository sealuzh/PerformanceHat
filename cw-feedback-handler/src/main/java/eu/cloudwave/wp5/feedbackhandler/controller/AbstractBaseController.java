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

import org.springframework.beans.factory.annotation.Autowired;

import eu.cloudwave.wp5.common.error.ErrorType;
import eu.cloudwave.wp5.common.error.RequestException;
import eu.cloudwave.wp5.feedbackhandler.model.db.DbApplication;
import eu.cloudwave.wp5.feedbackhandler.model.db.DbCostApplication;
import eu.cloudwave.wp5.feedbackhandler.repositories.ApplicationRepository;

public abstract class AbstractBaseController {

  protected static final String EXCEPTION_MESSAGE = "Error executing request to '%s'";
  private static final String INVALID_APPLICATION_ID_ERROR_MSG = "Invalid Application-ID.";
  private static final String INVALID_ACCESS_TOKEN_ERROR_MSG = "Wrong Access-Token specified.";

  @Autowired
  protected ApplicationRepository applicationRepository;

  protected final DbApplication handleUnauthorized(final String applicationId, final String accessToken) {
    final DbApplication application = applicationRepository.findOne(applicationId);
    authorize(application, accessToken);
    return application;
  }

  protected final DbCostApplication handleUnauthorizedCostApplication(final String applicationId, final String accessToken) {
    final DbCostApplication application = applicationRepository.findOneCostApplication(applicationId);
    authorize(application, accessToken);
    return application;
  }

  /**
   * Helper that throws exception if authorization failed
   * 
   * @param application
   * @param accessToken
   */
  private void authorize(final DbApplication application, final String accessToken) {
    if (application == null) {
      throw new RequestException(ErrorType.INVALID_APPLICATION_ID, INVALID_APPLICATION_ID_ERROR_MSG);
    }
    else if (!application.getAccessToken().equals(accessToken)) {
      throw new RequestException(ErrorType.WRONG_ACCESS_TOKEN, INVALID_ACCESS_TOKEN_ERROR_MSG);
    }
  }
}
