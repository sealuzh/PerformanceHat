package eu.cloudwave.wp5.feedbackhandler.controller;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({//@formatter:off
  MainControllerIntegrationTest.class,
  NewRelicControllerIntegrationTest.class,
})//@formatter:on
public class ControllerIntegrationTests {}
