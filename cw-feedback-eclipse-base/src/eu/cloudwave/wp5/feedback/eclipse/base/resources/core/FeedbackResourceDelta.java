package eu.cloudwave.wp5.feedback.eclipse.base.resources.core;

import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResourceDelta;

/**
 * Extends an {@link IResourceDelta} with domain related functionality.
 */
public interface FeedbackResourceDelta extends IResourceDelta {

  /**
   * Returns all changed files of the sub-tree of the current resource.
   * 
   * @return a {@link Set} containing all affected files
   */
  public Set<IFile> getChangedFiles();

}
