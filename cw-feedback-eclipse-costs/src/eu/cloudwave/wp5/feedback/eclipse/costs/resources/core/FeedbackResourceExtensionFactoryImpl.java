package eu.cloudwave.wp5.feedback.eclipse.costs.resources.core;

import org.eclipse.core.resources.IResource;

import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.FeedbackResourceExtension;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.FeedbackResourceExtensionFactory;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.FeedbackResourceExtensionImpl;
import eu.cloudwave.wp5.feedback.eclipse.costs.core.CostIds;

public class FeedbackResourceExtensionFactoryImpl implements FeedbackResourceExtensionFactory {

  /**
   * {@inheritDoc}
   */
  @Override
  public FeedbackResourceExtension create(IResource resource) {
    return new FeedbackResourceExtensionImpl(resource, CostIds.COST_MARKER);
  }
}
