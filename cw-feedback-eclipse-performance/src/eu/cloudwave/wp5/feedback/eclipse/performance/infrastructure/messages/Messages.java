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
package eu.cloudwave.wp5.feedback.eclipse.performance.infrastructure.messages;

import org.eclipse.osgi.util.NLS;

import eu.cloudwave.wp5.feedback.eclipse.performance.Ids;

/**
 * Provides messages.
 */
public class Messages extends NLS {
  private static final String BUNDLE_NAME = Ids.PLUGIN + ".infrastructure.messages.messages"; //$NON-NLS-1$

  // Properties: Performance Hat Thresholds
  public static String PROPERTIES__GROUP__THRESHOLDS;
  public static String PROPERTY_PAGES__PERFORMANCE__DESCRIPTION;
  public static String PROPERTIES__THRESHOLDS__HOTSPOTS;
  public static String PROPERTIES__THRESHOLDS__LOOPS;

  // Properties: New Relic Tab
  public static String PROPERTIES__GROUP__NEWRELIC;
  public static String PROPERTY_PAGES__NEWRELIC__DESCRIPTION;
  public static String PROPERTIES__NEWRELIC_API_KEY__LABEL;
  public static String PROPERTIES__NEWRELIC_APPLICATION_ID__LABEL;

  // Hotspots Hover
  public static String BUTTON_CW_DASHBOARD;
  public static String BUTTON_FURTHER_INFO;

  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {}
}
