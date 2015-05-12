package eu.cloudwave.wp5.feedback.eclipse.base.core.builders;

import java.util.Set;

import org.eclipse.core.runtime.CoreException;

import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaFile;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaProject;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaResourceDelta;

/**
 * Implementation of {@link FeedbackCleaner}.
 */
public class FeedbackCleanerImpl implements FeedbackCleaner {

  /**
   * {@inheritDoc}
   */
  @Override
  public void cleanAll(final FeedbackJavaProject project) throws CoreException {
    clean(project.getJavaSourceFiles());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void cleanDelta(final FeedbackJavaResourceDelta delta) throws CoreException {
    clean(delta.getChangedJavaFiles());
  }

  private void clean(final Set<FeedbackJavaFile> javaFiles) throws CoreException {
    for (final FeedbackJavaFile javaFile : javaFiles) {
      javaFile.deleteMarkers();
    }
  }
}
