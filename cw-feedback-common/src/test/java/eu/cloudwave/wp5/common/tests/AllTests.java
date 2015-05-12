package eu.cloudwave.wp5.common.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import eu.cloudwave.wp5.common.model.impl.ProcedureTest;
import eu.cloudwave.wp5.common.model.impl.RestRequestErrorTest;
import eu.cloudwave.wp5.common.rest.BaseRestClientTest;

/**
 * All tests of the common project.
 */
@RunWith(Suite.class)
@SuiteClasses({//@formatter:off
  ProcedureTest.class,
  RestRequestErrorTest.class,
  BaseRestClientTest.class,
})//@formatter:on
public class AllTests {

}
