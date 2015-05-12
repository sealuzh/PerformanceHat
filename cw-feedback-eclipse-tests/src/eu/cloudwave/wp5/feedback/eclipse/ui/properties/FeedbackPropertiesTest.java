package eu.cloudwave.wp5.feedback.eclipse.ui.properties;

import static eu.cloudwave.wp5.feedback.eclipse.tests.util.SwtBotTestUtil.getProjectFromExplorer;
import static org.fest.assertions.Assertions.assertThat;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.Test;
import org.osgi.service.prefs.BackingStoreException;

import eu.cloudwave.wp5.feedback.eclipse.base.core.FeedbackProperties;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.properties.PerformanceFeedbackProperties;
import eu.cloudwave.wp5.feedback.eclipse.tests.base.AbstractSwtBotTest;

/**
 * Test cases for the Feedback Property Pages.
 */
public class FeedbackPropertiesTest extends AbstractSwtBotTest {

  private static final String MENU__PROPERTIES = "Properties";
  private static final String SHELL__PROPERTIES = "Properties for %s";
  private static final String PROPERTIES_PAGE__FDD = "Feedback-Driven Development";
  private static final String PROPERTIES_PAGE__NEW_RELIC = "New Relic";

  private static final String SAMPLE_APPLICATION_ID = "abcd";
  private static final String SAMPLE_ACCESS_TOKEN = "6789";
  private static final String SAMPLE_API_KEY = "1234";
  private static final String SAMPLE_HOTSPOT_THRESHOLD = "1000";
  private static final String SAMPLE_LOOP_THRESHOLD = "2000";

  private static final String EMPTY = "";
  private static final String DEFAULT = "<default>";

  @Test
  public void testProperties() throws OperationCanceledException, CoreException, InterruptedException {
    enableFeedbackNature();
    openFeedbackPropertyPage();

    bot.text(1).setText(SAMPLE_APPLICATION_ID);
    bot.text(2).setText(SAMPLE_ACCESS_TOKEN);
    bot.text(3).setText(SAMPLE_HOTSPOT_THRESHOLD);
    bot.text(4).setText(SAMPLE_LOOP_THRESHOLD);

    pressOk();

    assertProperty(FeedbackProperties.FEEDBACK_HANDLER__APPLICATION_ID, SAMPLE_APPLICATION_ID);
    assertProperty(FeedbackProperties.FEEDBACK_HANDLER__ACCESS_TOKEN, SAMPLE_ACCESS_TOKEN);
    assertProperty(PerformanceFeedbackProperties.TRESHOLD__HOTSPOTS, SAMPLE_HOTSPOT_THRESHOLD);
    assertProperty(PerformanceFeedbackProperties.TRESHOLD__LOOPS, SAMPLE_LOOP_THRESHOLD);

    openFeedbackPropertyPage();

    assertThat(bot.text(1).getText()).isEqualTo(SAMPLE_APPLICATION_ID);
    assertThat(bot.text(2).getText()).isEqualTo(SAMPLE_ACCESS_TOKEN);
    assertThat(bot.text(3).getText()).isEqualTo(SAMPLE_HOTSPOT_THRESHOLD);
    assertThat(bot.text(4).getText()).isEqualTo(SAMPLE_LOOP_THRESHOLD);

    pressOk();
  }

  @Test
  public void testNewRelicProperties() throws OperationCanceledException, CoreException, InterruptedException {
    enableFeedbackNature();
    openFeedbackSubPropertyPage(PROPERTIES_PAGE__NEW_RELIC);

    bot.text(1).setText(SAMPLE_API_KEY);
    bot.text(2).setText(SAMPLE_APPLICATION_ID);

    pressOk();

    assertProperty(PerformanceFeedbackProperties.NEW_RELIC__API_KEY, SAMPLE_API_KEY);
    assertProperty(PerformanceFeedbackProperties.NEW_RELIC__APPLICATION_ID, SAMPLE_APPLICATION_ID);

    openFeedbackSubPropertyPage(PROPERTIES_PAGE__NEW_RELIC);

    assertThat(bot.text(1).getText()).isEqualTo(SAMPLE_API_KEY);
    assertThat(bot.text(2).getText()).isEqualTo(SAMPLE_APPLICATION_ID);

    pressOk();
  }

  private SWTBotTreeItem openFeedbackPropertyPage() {
    final SWTBotTreeItem sampleProjectItem = getProjectFromExplorer(bot, sampleProject.getProject());
    sampleProjectItem.contextMenu(MENU__PROPERTIES).click();
    bot.shell(String.format(SHELL__PROPERTIES, sampleProject.getName())).activate();
    return bot.tree().getTreeItem(PROPERTIES_PAGE__FDD).select().expand().click();
  }

  private void openFeedbackSubPropertyPage(final String propertyPageName) {
    openFeedbackPropertyPage().getNode(propertyPageName).select().click();
  }

  /**
   * Asserts that the property with the given key has the given value.
   * 
   * @param key
   *          the key of the property
   * @param expectedValue
   *          the expected value of the property
   * @throws BackingStoreException
   */
  private void assertProperty(final String key, final String expectedValue) {
    final String defaultValue = expectedValue.equals(DEFAULT) ? EMPTY : DEFAULT;
    final String actualValue = sampleProject.getFeedbackProject().getFeedbackProperties().get(key, defaultValue);
    assertThat(actualValue).isEqualTo(expectedValue);
  }
}
