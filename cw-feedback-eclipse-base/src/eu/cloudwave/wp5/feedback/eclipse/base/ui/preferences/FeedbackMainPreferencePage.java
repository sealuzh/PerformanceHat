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
