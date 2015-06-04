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
package eu.cloudwave.wp5.feedback.eclipse.performance.infrastructure.config;

/**
 * Contains paths to configuration files and keys of configurations.
 */
public class PerformanceConfigs {

  // configuration files
  public static final String BUNDLE_PROPERTIES = "OSGI-INF/l10n/bundle.properties";

  // default thresholds
  public static final Double DEFAULT_THRESHOLD_HOTSPOTS = 2000.0;
  public static final Double DEFAULT_THRESHOLD_LOOPS = 5000.0;
}
