package eu.cloudwave.wp5.feedbackhandler.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import eu.cloudwave.wp5.common.dto.RestRequestErrorDto;
import eu.cloudwave.wp5.common.error.RequestException;

public abstract class AbstractBaseUiController extends AbstractBaseController {

  private static final String ERROR = "Error";
  private static final String MESSAGE = "message";

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
  public final ModelAndView handleRestRequestException(final RequestException exception, final HttpServletRequest request) {
    return htmlErrorPage(exception.getMessage());
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
  public final ModelAndView handleException(final Exception exception, final HttpServletRequest request) {
    LogManager.getLogger(getClass()).error(String.format(EXCEPTION_MESSAGE, request.getRequestURI()), exception);
    return htmlErrorPage(exception.getMessage());
  }

  private final ModelAndView htmlErrorPage(final String message) {
    return new ModelAndView(ERROR, MESSAGE, message);
  }
}
