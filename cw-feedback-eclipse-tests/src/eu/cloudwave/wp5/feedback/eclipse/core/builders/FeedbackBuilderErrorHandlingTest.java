package eu.cloudwave.wp5.feedback.eclipse.core.builders;

import static eu.cloudwave.wp5.feedback.eclipse.tests.mocks.FeedbackHandlerEclipseClientMock.INVALID_VALUE;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.junit.Test;

import eu.cloudwave.wp5.feedback.eclipse.base.core.FeedbackProperties;
import eu.cloudwave.wp5.feedback.eclipse.base.core.preferences.FeedbackPreferences;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.properties.PerformanceFeedbackProperties;
import eu.cloudwave.wp5.feedback.eclipse.tests.base.AbstractSwtBotTest;

/**
 * Tests the error handling of the build process.
 */
public class FeedbackBuilderErrorHandlingTest extends AbstractSwtBotTest {

  private static final String SHELL__INVALID_URL = "Feedback Handler not available";
  private static final String SHELL__INVALID_APPLICATION_ID = "Invalid Application-ID";
  private static final String SHELL__WRONG_ACCESS_TOKEN = "Wrong Access-Token";
  private static final String SHELL__PREFERENCES = "Preferences";
  private static final String SHELL__PROPERTIES = "Properties for %s";
  private static final String BUTTON__OPEN_PREFERENCES = "Open Preferences";
  private static final String BUTTON__OPEN_PROPERTIES = "Open Properties";

  /**
   * Tests the error handling if the Feedback Handler URL is invalid.
   */
  @Test
  public void testInvalidFeedbackHandlerUrl() throws OperationCanceledException, CoreException, InterruptedException {
    final String originalFeedbackHandlerUrl = FeedbackPreferences.getString(FeedbackPreferences.FEEDBACK_HANDLER__URL);
    FeedbackPreferences.getPreferenceStore().setValue(FeedbackPreferences.FEEDBACK_HANDLER__URL, INVALID_VALUE);
    enableFeedbackNature();

    assertShellVisible(SHELL__INVALID_URL);
    bot.button(BUTTON__OPEN_PREFERENCES).click();
    assertCloudWavePreferencePageVisible();
    pressOk();

    // reset Feedback Handler URL
    FeedbackPreferences.getPreferenceStore().setValue(FeedbackPreferences.FEEDBACK_HANDLER__URL, originalFeedbackHandlerUrl);
  }

  /**
   * Tests the error handling if the Application-ID is invalid.
   */
  @Test
  public void testInvalidApplicationId() throws OperationCanceledException, CoreException, InterruptedException {
    getProjectProperties().put(FeedbackProperties.FEEDBACK_HANDLER__APPLICATION_ID, INVALID_VALUE);
    enableFeedbackNature();

    assertShellVisible(SHELL__INVALID_APPLICATION_ID);
    bot.button(BUTTON__OPEN_PROPERTIES).click();
    assertPropertyPageVisible();
    pressOk();

    // reset Application ID
    getProjectProperties().remove(PerformanceFeedbackProperties.NEW_RELIC__APPLICATION_ID);
  }

  /**
   * Tests the error handling if the Access-Token is wrong.
   */
  @Test
  public void testWrongAccessToken() throws OperationCanceledException, CoreException, InterruptedException {
    getProjectProperties().put(FeedbackProperties.FEEDBACK_HANDLER__ACCESS_TOKEN, INVALID_VALUE);
    enableFeedbackNature();

    assertShellVisible(SHELL__WRONG_ACCESS_TOKEN);
    bot.button(BUTTON__OPEN_PROPERTIES).click();
    assertPropertyPageVisible();
    pressOk();

    // reset API Key
    getProjectProperties().remove(PerformanceFeedbackProperties.NEW_RELIC__API_KEY);
  }

  private IEclipsePreferences getProjectProperties() {
    return sampleProject.getFeedbackProject().getFeedbackProperties();
  }

  private void assertCloudWavePreferencePageVisible() {
    assertShellVisible(SHELL__PREFERENCES);
    assertLabelVisible(eu.cloudwave.wp5.feedback.eclipse.base.infrastructure.messages.Messages.PREFERENCE_PAGES__MAIN__DESCRIPTION);
  }

  private void assertPropertyPageVisible() {
    assertProjectPropertiesVisible();
    assertLabelVisible(eu.cloudwave.wp5.feedback.eclipse.base.infrastructure.messages.Messages.PROPERTIES_PAGES__MAIN__DESCRIPTION);
  }

  private void assertProjectPropertiesVisible() {
    assertShellVisible(String.format(SHELL__PROPERTIES, sampleProject.getName()));
  }

  private void assertShellVisible(final String shellTitle) {
    bot.shell(shellTitle).activate();
  }

  private void assertLabelVisible(final String content) {
    bot.label(content);
  }

}
