package eu.cloudwave.wp5.feedback.eclipse.base.core.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.google.common.base.Optional;

import eu.cloudwave.wp5.feedback.eclipse.base.core.BasePluginActivator;
import eu.cloudwave.wp5.feedback.eclipse.base.infrastructure.config.ConfigLoader;
import eu.cloudwave.wp5.feedback.eclipse.base.infrastructure.config.FeedbackConfigs;

/**
 * Sets default values for preferences.
 */
public class FeedbackPreferenceInitializer extends AbstractPreferenceInitializer {

  private ConfigLoader configLoader;

  public FeedbackPreferenceInitializer() {
    this.configLoader = BasePluginActivator.instance(ConfigLoader.class);
  }

  @Override
  public void initializeDefaultPreferences() {
    final IPreferenceStore preferenceStore = BasePluginActivator.getDefault().getPreferenceStore();
    final Optional<String> defaultServerUrl = configLoader.get(FeedbackConfigs.BUNDLE_PROPERTIES, FeedbackConfigs.DEFAULT_FEEDBACK_HANDLER_URL);
    if (defaultServerUrl.isPresent()) {
      preferenceStore.setDefault(FeedbackPreferences.FEEDBACK_HANDLER__URL, defaultServerUrl.get());
    }
    final Optional<String> defaultCloudWaveDashboardUrl = configLoader.get(FeedbackConfigs.BUNDLE_PROPERTIES, FeedbackConfigs.DEFAULT_CLOUDWAVE_DASHBOARD_URL);
    if (defaultCloudWaveDashboardUrl.isPresent()) {
      preferenceStore.setDefault(FeedbackPreferences.CLOUDWAVE_DASHBOARD__URL, defaultCloudWaveDashboardUrl.get());
    }
  }
}
