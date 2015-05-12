package eu.cloudwave.wp5.feedback.eclipse.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import eu.cloudwave.wp5.feedback.eclipse.core.builders.FeedbackBuilderTest;

/**
 * Running all non-SWTBot tests.
 */
@RunWith(Suite.class)
@SuiteClasses({//@formatter:off
  FeedbackBuilderTest.class,
})//@formatter:on
public class AllNonSwtBotTests {}
