package eu.cloudwave.wp5.feedback.eclipse.base.resources.core;

import java.util.Set;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

/**
 * This interface contains methods that are shared by all implementors of sub-interfaces of {@link FeedbackResource}.
 * The respective implementation is provided by {@link FeedbackResourceExtensionImpl}.
 * 
 * This is required, because implementors of sub-interfaces of {@link FeedbackResource} cannot inherit from a shared
 * implementor of {@link FeedbackResource}, because they inherit from other respective base implementation (and Java
 * does not support multiple inheritance).
 * 
 * Therefore these sub-interfaces of {@link FeedbackResource} can delegate the execution of the methods specified here
 * to an instance of {@link FeedbackResourceExtensionImpl}.
 */
public interface FeedbackResourceExtension {

  /**
   * Deletes all feedback markers on the current {@link IResource} and all its child {@link IResource}'s.
   * 
   * @throws CoreException
   *           if one or more markers could not be deleted
   */
  public void deleteMarkers() throws CoreException;

  /**
   * Returns all markers with the given id.
   * 
   * @param id
   *          the id of the markers to be found
   * @return a {@link Set} containing all markers with the given id
   * @throws CoreException
   *           if the resource does not exist or is not open
   */
  public Set<IMarker> findMarkers(String id);

}
