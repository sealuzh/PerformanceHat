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
package eu.cloudwave.wp5.feedback.eclipse.base.ui.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import eu.cloudwave.wp5.feedback.eclipse.base.core.BasePluginActivator;
import eu.cloudwave.wp5.feedback.eclipse.base.core.preferences.FeedbackPreferences;
import eu.cloudwave.wp5.feedback.eclipse.base.infrastructure.messages.Messages;

/**
 * Preference page of the plugin.
 */
public class FeedbackMainPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

  public FeedbackMainPreferencePage() {
    super(GRID);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void init(final IWorkbench workbench) {
    setPreferenceStore(BasePluginActivator.getDefault().getPreferenceStore());
    setDescription(Messages.PREFERENCE_PAGES__MAIN__DESCRIPTION);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void createFieldEditors() {
    addStringField(FeedbackPreferences.FEEDBACK_HANDLER__URL, Messages.PREFERENCES__FEEDBACK_HANDLER__URL);
    addStringField(FeedbackPreferences.CLOUDWAVE_DASHBOARD__URL, Messages.PREFERENCES__CLOUDWAVE_DASHBOARD__URL);
  }

  private void addStringField(final String name, final String labelText) {
    addField(new StringFieldEditor(name, labelText, getFieldEditorParent()));
  }

}
