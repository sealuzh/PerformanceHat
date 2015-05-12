package eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java;

import java.util.Set;

import org.eclipse.core.resources.IResourceDelta;

import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.FeedbackResourceDelta;

/**
 * Extends an {@link FeedbackResourceDelta} with Java related functionality.
 */
public interface FeedbackJavaResourceDelta extends FeedbackResourceDelta, IResourceDelta {

  /**
   * Returns all affected java files of the sub-tree of the current resource. Only source files are contained, binary
   * files (.source) are not included.
   * 
   * @return a {@link Set} containing all affected java files
   */
  public Set<FeedbackJavaFile> getChangedJavaFiles();

}
