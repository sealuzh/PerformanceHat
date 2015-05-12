package eu.cloudwave.wp5.feedbackhandler.controller;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class MainController extends AbstractBaseRestController {

  private Logger sampleLogger = LogManager.getLogger("SampleLogger");
  private static final String ROOT_CONTENT = "Server is running.";
  private static final String LOGGING_MSG = "Request to Root URL.";

  @RequestMapping("/")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public String root() {
    sampleLogger.info(LOGGING_MSG);
    return ROOT_CONTENT;
  }

}
