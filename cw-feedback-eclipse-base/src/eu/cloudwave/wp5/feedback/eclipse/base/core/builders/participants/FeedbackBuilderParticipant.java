package eu.cloudwave.wp5.feedback.eclipse.base.core.builders.participants;

import java.util.Set;

import org.eclipse.core.runtime.CoreException;

import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaFile;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaProject;

/**
 * A {@link FeedbackBuilderParticipant} is a participant that acts as a part of the whole feedback builder. As soon as
 * it is registered in the feedback builder it is executed each time the feedback builder is triggered.
 */
public interface FeedbackBuilderParticipant {

  /**
   * Builds the given files of the given project
   * 
   * @param project
   *          the project
   * @param files
   *          the Java files to be built
   * @throws CoreException
   *           if build process could not be successfully finished
   */
  public void build(final FeedbackJavaProject project, final Set<FeedbackJavaFile> files) throws CoreException;
}
