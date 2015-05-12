package eu.cloudwave.wp5.feedbackhandler.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import eu.cloudwave.wp5.feedbackhandler.metricsources.NewRelicClientTest;
import eu.cloudwave.wp5.feedbackhandler.rest.JsonRestClientImplTest;

/**
 * All unit tests.
 */
@RunWith(Suite.class)
@SuiteClasses({//@formatter:off
  JsonRestClientImplTest.class,
  NewRelicClientTest.class,
})//@formatter:on
public class UnitTests {}
