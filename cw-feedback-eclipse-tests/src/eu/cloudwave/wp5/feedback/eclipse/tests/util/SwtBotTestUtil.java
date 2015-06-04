/*******************************************************************************
 * Copyright 2015 Software Evolution and Architecture Lab, University of Zurich
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
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
