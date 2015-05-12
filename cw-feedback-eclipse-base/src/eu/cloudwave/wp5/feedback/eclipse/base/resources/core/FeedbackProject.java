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
package eu.cloudwave.wp5.feedback.eclipse.base.resources.core;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;

/**
 * Extends an {@link IProject} with domain related functionality.
 */
public interface FeedbackProject extends FeedbackResource, IProject {

  /**
   * Get the project-specific properties of the Feedback plug-in.
   * 
   * @return the project-specific properties of the Feedback plug-in
   */
  public IEclipsePreferences getFeedbackProperties();

  /**
   * Gets the access token of the current project.
   * 
   * @return the access token of the Project or an empty String, if the access token is not specified
   */
  public String getAccessToken();

  /**
   * Gets the application ID of the current project.
   * 
   * @return the application ID of the current project or an empty String, if the application ID is not specified
   */
  public String getApplicationId();

}
