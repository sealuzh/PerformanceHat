package eu.cloudwave.wp5.feedback.eclipse.core.natures;

import static eu.cloudwave.wp5.feedback.eclipse.tests.assertion.Asserts.assertNatureDisabled;
import static eu.cloudwave.wp5.feedback.eclipse.tests.assertion.Asserts.assertNatureEnabled;
import static eu.cloudwave.wp5.feedback.eclipse.tests.util.SwtBotTestUtil.getProjectFromExplorer;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.Test;

import eu.cloudwave.wp5.feedback.eclipse.performance.Ids;
import eu.cloudwave.wp5.feedback.eclipse.tests.base.AbstractSwtBotTest;

/**
 * Test cases for the Feedback Project Nature.
 */
public class FeedbackProjectNatureTest extends AbstractSwtBotTest {

  private static final String MENU__CONFIGURE = "Configure";
  private static final String MENU__ENABLE_FEEDBACK_PROJECT_NATURE = "Enable Feedback Project Nature";
  private static final String MENU__DISABLE_FEEDBACK_PROJECT_NATURE = "Disable Feedback Project Nature";

  /**
   * Tests enabling and disabling the feedback project nature using the project context menu.
   */
  @Test
  public void testToggleProjectNature() throws CoreException, OperationCanceledException, InterruptedException {
    assertNatureDisabled(sampleProject.getProject(), Ids.NATURE);

    final SWTBotTreeItem sampleProjectItem = getProjectFromExplorer(bot, sampleProject.getProject());
    sampleProjectItem.contextMenu(MENU__CONFIGURE).menu(MENU__ENABLE_FEEDBACK_PROJECT_NATURE).click();
    waitForAutoBuild();

    assertNatureEnabled(sampleProject.getProject(), Ids.NATURE);

    sampleProjectItem.contextMenu(MENU__CONFIGURE).menu(MENU__DISABLE_FEEDBACK_PROJECT_NATURE).click();
    waitForAutoBuild();

    assertNatureDisabled(sampleProject.getProject(), Ids.NATURE);
  }

}
