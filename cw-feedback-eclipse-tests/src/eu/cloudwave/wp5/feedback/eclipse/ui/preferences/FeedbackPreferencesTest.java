/*******************************************************************************
 * Copyright 2015 Software Evolution and Architecture Lab, University of Zurich
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
package eu.cloudwave.wp5.feedback.eclipse.ui.preferences;

import static org.fest.assertions.Assertions.assertThat;

import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.junit.Test;

import eu.cloudwave.wp5.feedback.eclipse.base.core.preferences.FeedbackPreferences;
import eu.cloudwave.wp5.feedback.eclipse.tests.base.AbstractSwtBotTest;

/**
 * Test cases for the Feedback Preference Page.
 */
public class FeedbackPreferencesTest extends AbstractSwtBotTest {

  private static final String MENU__WINDOW = "Window";
  private static final String MENU__PREFERENCES = "Preferences";
  private static final String SHELL__PREFERENCES = "Preferences";
  private static final String PREFERENCE_PAGE__FDD = "Feedback-Driven Development";
  private static final String LABELS__FEEDBACK_HANDLER_URL = "Feedback Handler URL";
  private static final String DEFAULT_VALUES__FEEDBACK_HANDLER_URL = "http://localhost:8080/";
  private static final String SAMPLE_FEEDBACK_HANDLER_URL = "http://any";

  /**
   * Checks whether the preferences are displayed correctly.
   */
  @Test
  public void testPreferences() {
    openFeedbackPreferencePage();

    final SWTBotText text = bot.textWithLabel(LABELS__FEEDBACK_HANDLER_URL);
    assertThat(text.getText()).isEqualTo(DEFAULT_VALUES__FEEDBACK_HANDLER_URL);

    text.setText(SAMPLE_FEEDBACK_HANDLER_URL);

    pressOk();

    final String feedbackHandlerUrl = FeedbackPreferences.getString(FeedbackPreferences.FEEDBACK_HANDLER__URL);
    assertThat(feedbackHandlerUrl).isEqualTo(SAMPLE_FEEDBACK_HANDLER_URL);
  }

  private void openFeedbackPreferencePage() {
    bot.menu(MENU__WINDOW).menu(MENU__PREFERENCES).click();
    bot.shell(SHELL__PREFERENCES).activate();
    bot.tree().getTreeItem(PREFERENCE_PAGE__FDD).select().click();
  }

}
