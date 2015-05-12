package eu.cloudwave.wp5.feedback.eclipse.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import eu.cloudwave.wp5.feedback.eclipse.core.builders.FeedbackBuilderErrorHandlingTest;
import eu.cloudwave.wp5.feedback.eclipse.core.natures.FeedbackProjectNatureTest;
import eu.cloudwave.wp5.feedback.eclipse.ui.preferences.FeedbackPreferencesTest;
import eu.cloudwave.wp5.feedback.eclipse.ui.properties.FeedbackPropertiesTest;

/**
 * Running all SWTBot tests.
 */
@RunWith(Suite.class)
@SuiteClasses({//@formatter:off
  FeedbackBuilderErrorHandlingTest.class,
  FeedbackPreferencesTest.class,
  FeedbackPropertiesTest.class,
  FeedbackProjectNatureTest.class,
})//@formatter:on
public class AllSwtBotTests {}
