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
