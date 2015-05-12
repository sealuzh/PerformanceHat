package eu.cloudwave.wp5.feedback.eclipse.base.resources.core;

import java.util.Set;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

import eu.cloudwave.wp5.feedback.eclipse.base.resources.base.AbstractResourceDecorator;

/**
 * Implementation of {@link FeedbackResource}. Acts as a decorator for the wrapped {@link IResource}.
 */
public class FeedbackResourceImpl extends AbstractResourceDecorator implements FeedbackResource {

  private final FeedbackResourceExtension feedbackResourceExtension;

  protected FeedbackResourceImpl(final IResource resource, final FeedbackResourceExtensionFactory extensionFactory) {
    super(resource);
    this.feedbackResourceExtension = extensionFactory.create(resource);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteMarkers() throws CoreException {
    feedbackResourceExtension.deleteMarkers();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<IMarker> findMarkers(final String id) {
    return feedbackResourceExtension.findMarkers(id);
  }

}
