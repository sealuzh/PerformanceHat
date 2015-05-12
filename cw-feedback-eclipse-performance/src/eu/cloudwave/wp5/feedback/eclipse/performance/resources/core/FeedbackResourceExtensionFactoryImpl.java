package eu.cloudwave.wp5.feedback.eclipse.performance.resources.core;

import org.eclipse.core.resources.IResource;

import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.FeedbackResourceExtension;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.FeedbackResourceExtensionFactory;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.FeedbackResourceExtensionImpl;
import eu.cloudwave.wp5.feedback.eclipse.performance.Ids;

public class FeedbackResourceExtensionFactoryImpl implements FeedbackResourceExtensionFactory {

  /**
   * {@inheritDoc}
   */
  @Override
  public FeedbackResourceExtension create(IResource resource) {
    return new FeedbackResourceExtensionImpl(resource, Ids.PERFORMANCE_MARKER);
  }
}
