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
package eu.cloudwave.wp5.feedback.eclipse.base.infrastructure.messages;

import org.eclipse.osgi.util.NLS;

/**
 * Provides messages.
 */
public class Messages extends NLS {
  private static final String BUNDLE_NAME = "eu.cloudwave.wp5.feedback.eclipse.base.infrastructure.messages.messages"; //$NON-NLS-1$

  public static String MESSAGE__FEEDBACK_HANDLER_NOT_AVAILABLE;
  public static String MESSAGE__INVALID_APPLICATION_ID;
  public static String MESSAGE__WRONG_ACCESS_TOKEN;

  public static String OPEN_PREFERENCES;
  public static String OPEN_PROPERTIES;

  // new
  public static String PREFERENCE_PAGES__MAIN__DESCRIPTION;
  public static String PREFERENCES__CLOUDWAVE_DASHBOARD__URL;
  public static String PREFERENCES__FEEDBACK_HANDLER__URL;

  public static String PROPERTIES_PAGES__MAIN__DESCRIPTION;
  public static String PROPERTIES__GROUP__AUTHENTICATION;
  public static String PROPERTIES__AUTHENTICATION__APPLICATION_ID;
  public static String PROPERTIES__AUTHENTICATION__ACCESS_TOKEN;

  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {}
}
