package eu.cloudwave.wp5.feedback.eclipse.base.core.builders;

import org.eclipse.core.runtime.CoreException;

import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaProject;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaResourceDelta;

public interface FeedbackCleaner {

  /**
   * Cleans all markers.
   * 
   * @throws CoreException
   *           if the markers could not be deleted
   */
  public void cleanAll(final FeedbackJavaProject project) throws CoreException;

  /**
   * Cleans all markers from the given delta.
   * 
   * @param delta
   *          the delta
   * @throws CoreException
   */
  public void cleanDelta(FeedbackJavaResourceDelta delta) throws CoreException;

}
