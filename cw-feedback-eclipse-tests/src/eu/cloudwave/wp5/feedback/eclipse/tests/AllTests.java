package eu.cloudwave.wp5.feedback.eclipse.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Running all tests.
 */
@RunWith(Suite.class)
@SuiteClasses({//@formatter:off
  AllNonSwtBotTests.class,
  AllSwtBotTests.class,
})//@formatter:on
public class AllTests {}
