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

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import eu.cloudwave.wp5.common.dto.RestRequestErrorDto;
import eu.cloudwave.wp5.common.error.ErrorType;
import eu.cloudwave.wp5.common.error.RequestException;

/**
 * Base implementation for controllers.
 */
public class AbstractBaseRestController extends AbstractBaseController {

  protected Logger sampleLogger = LogManager.getLogger("SampleLogger");

  /**
   * Is called whenever an {@link RequestException} in a controller method is thrown.
   * 
   * @param exception
   *          the exception that arose
   * @param request
   *          the request that caused the exception
   * @return an {@link RestRequestErrorDto} for the respective error
   */
  @ExceptionHandler(RequestException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public final RestRequestErrorDto handleRestRequestException(final RequestException exception, final HttpServletRequest request) {
    return new RestRequestErrorDto(exception.getType(), exception.getMessage());
  }

  /**
   * Is called whenever an {@link Exception} in a controller method is thrown.
   * 
   * @param exception
   *          the exception that arose
   * @param request
   *          the request that caused the exception
   * @return an error message
   */
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  public final RestRequestErrorDto handleException(final Exception exception, final HttpServletRequest request) {
    LogManager.getLogger(getClass()).error(String.format(EXCEPTION_MESSAGE, request.getRequestURI()), exception);
    return new RestRequestErrorDto(ErrorType.GENERAL, exception.getMessage());
  }

}
