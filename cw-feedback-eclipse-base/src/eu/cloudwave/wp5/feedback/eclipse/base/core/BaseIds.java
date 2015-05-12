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
package eu.cloudwave.wp5.feedback.eclipse.base.core;

public class BaseIds {

  /**
   * The id of the plug-in.
   * 
   * IMPORTANT: Has to be in sync with the 'Bundle-SymbolicName' property of the MANIFEST.MF. Otherwise the
   * {@link BasePluginActivator#getImageDescriptor(String)} method does not work.
   */
  public static final String BASEPLUGIN = "eu.cloudwave.wp5.feedback.eclipse.base"; //$NON-NLS-1$

  /**
   * The Id used for external purposes (i.e. visible for users).
   */
  public static final String ID = "eu.cloudwave.wp5.feedback";

  private static final String DOT = "."; //$NON-NLS-1$

  private static final String PLUGIN_QUALIFIER = BASEPLUGIN + DOT;

  public static final String MARKER = PLUGIN_QUALIFIER + "markers.FeedbackMarker"; //$NON-NLS-1$

  /*
   * Property Page
   */

  private static final String UI_QUALIFIER = "eu.cloudwave.wp5.feedback.eclipse.performance.core.ui" + DOT; //$NON-NLS-1$

  private static final String PROPERTIES_PAGE_QUALIFIER = UI_QUALIFIER + "properties" + DOT; //$NON-NLS-1$

  public static final String PREFERENCE_PAGE = UI_QUALIFIER + "preferences.FeedbackMainPreferencePage"; //$NON-NLS-1$
  public static final String PROPERTIES_PAGE__FDD_MAIN = PROPERTIES_PAGE_QUALIFIER + "FeedbackMainPropertyPage"; //$NON-NLS-1$

  private BaseIds() {}
}
