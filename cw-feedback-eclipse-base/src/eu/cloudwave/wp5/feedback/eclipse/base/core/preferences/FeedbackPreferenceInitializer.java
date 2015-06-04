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
