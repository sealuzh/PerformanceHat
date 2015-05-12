package eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java;

import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResourceDelta;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.FeedbackResourceDeltaImpl;

/**
 * Implementation of {@link FeedbackJavaResourceDelta}. Acts as a decorator for the wrapped {@link IResourceDelta}.
 */
public class FeedbackJavaResourceDeltaImpl extends FeedbackResourceDeltaImpl implements FeedbackJavaResourceDelta, IResourceDelta {

  private final FeedbackJavaResourceFactory feedbackJavaResourceFactory;

  protected FeedbackJavaResourceDeltaImpl(final IResourceDelta delta, final FeedbackJavaResourceFactory feedbackJavaResourceFactory) {
    super(delta);
    this.feedbackJavaResourceFactory = feedbackJavaResourceFactory;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<FeedbackJavaFile> getChangedJavaFiles() {
    final Set<FeedbackJavaFile> javaFiles = Sets.newHashSet();
    for (final IFile file : getChangedFiles()) {
      final Optional<? extends FeedbackJavaFile> feedbackJavaFileOptional = feedbackJavaResourceFactory.create(file);
      if (feedbackJavaFileOptional.isPresent()) {
        javaFiles.add(feedbackJavaFileOptional.get());
      }
    }
    return ImmutableSet.copyOf(javaFiles);
  }

}
