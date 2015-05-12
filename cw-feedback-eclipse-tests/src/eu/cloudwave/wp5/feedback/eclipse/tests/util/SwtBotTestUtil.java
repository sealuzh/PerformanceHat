package eu.cloudwave.wp5.feedback.eclipse.tests.util;

import org.eclipse.core.resources.IProject;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;

/**
 * Provides utility methods for SWTBot tests.
 */
public class SwtBotTestUtil {

  private static final String VIEW__PROJECT_EXPLORER = "Project Explorer";

  /**
   * Returns the project explorer.
   * 
   * @param bot
   *          the {@link SWTWorkbenchBot}
   * @return the project explorer
   */
  public static SWTBotView getProjectExplorer(final SWTWorkbenchBot bot) {
    return bot.viewByTitle(VIEW__PROJECT_EXPLORER);
  }

  /**
   * Returns the tree item of the given project in the project explorer.
   * 
   * @param bot
   *          the {@link SWTWorkbenchBot}
   * @param project
   *          the project
   * @return the tree item of the given project in the project explorer
   */
  public static SWTBotTreeItem getProjectFromExplorer(final SWTWorkbenchBot bot, final IProject project) {
    return getProjectExplorer(bot).bot().tree().getTreeItem(project.getName());
  }

}
