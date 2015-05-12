package eu.cloudwave.wp5.feedback.eclipse.performance.resources.core;

import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.AbstractFeedbackResourceFactoryImpl;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.FeedbackResourceExtensionFactory;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.FeedbackResourceFactory;
import eu.cloudwave.wp5.feedback.eclipse.performance.PerformancePluginActivator;

/**
 * Implementation of {@link FeedbackResourceFactory}.
 */
public class FeedbackResourceFactoryImpl extends AbstractFeedbackResourceFactoryImpl implements FeedbackResourceFactory {

  public FeedbackResourceFactoryImpl() {
    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected FeedbackResourceExtensionFactory getFeedbackResourceExtensionFactory() {
    return PerformancePluginActivator.instance(FeedbackResourceExtensionFactory.class);
  };

  /**
   * {@inheritDoc}
   */
  @Override
  protected FeedbackResourceFactory getFeedbackResourceFactory() {
    return PerformancePluginActivator.instance(FeedbackResourceFactory.class);
  };
}