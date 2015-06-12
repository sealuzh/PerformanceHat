package eu.cloudwave.wp5.feedback.eclipse.costs.core.builders;

import java.util.Set;

import org.eclipse.core.runtime.CoreException;

import eu.cloudwave.wp5.feedback.eclipse.base.core.builders.FeedbackCleaner;
import eu.cloudwave.wp5.feedback.eclipse.base.core.builders.FeedbackCleanerImpl;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaFile;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaProject;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaResourceDelta;
import eu.cloudwave.wp5.feedback.eclipse.costs.core.cache.ApplicationDtoCache;

/**
 * Implementation of {@link FeedbackCleaner}.
 */
public class CostFeedbackCleanerImpl extends FeedbackCleanerImpl {

  /**
   * {@inheritDoc}
   */
  @Override
  public void cleanAll(final FeedbackJavaProject project) throws CoreException {
    ApplicationDtoCache.clear();
    clean(project.getJavaSourceFiles());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void cleanDelta(final FeedbackJavaResourceDelta delta) throws CoreException {
    ApplicationDtoCache.clear();
    clean(delta.getChangedJavaFiles());
  }

  private void clean(final Set<FeedbackJavaFile> javaFiles) throws CoreException {
    for (final FeedbackJavaFile javaFile : javaFiles) {
      javaFile.deleteMarkers();
    }
  }
}