package eu.cloudwave.wp5.feedback.eclipse.tests.base;

import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.junit.BeforeClass;

/**
 * Basic functionality for SWTBot tests.
 */
public abstract class AbstractSwtBotTest extends AbstractWorkbenchTest {

  private static final String EN_US = "EN_US";
  private static final String WELCOME = "Welcome";
  private static final String BUTTON__OK = "OK";

  protected static SWTWorkbenchBot bot;

  @BeforeClass
  public static void setupBot() {
    bot = new SWTWorkbenchBot();
    SWTBotPreferences.KEYBOARD_LAYOUT = EN_US;
    closeWelcomeView();
  }

  private static void closeWelcomeView() {
    for (final SWTBotView swtBotView : bot.views()) {
      if (swtBotView.getTitle().equals(WELCOME)) {
        swtBotView.close();
        return;
      }
    }
  }

  protected void pressOk() {
    bot.button(BUTTON__OK).click();
  }

}
