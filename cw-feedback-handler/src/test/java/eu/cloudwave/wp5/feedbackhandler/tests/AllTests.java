package eu.cloudwave.wp5.feedbackhandler.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import eu.cloudwave.wp5.feedbackhandler.controller.ControllerIntegrationTests;

/**
 * All feedback handler tests.
 */
@RunWith(Suite.class)
@SuiteClasses({//@formatter:off
  UnitTests.class,
  ControllerIntegrationTests.class,
})//@formatter:on
public class AllTests {}
