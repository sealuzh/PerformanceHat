package eu.cloudwave.wp5.feedback.eclipse.performance.resources.core.java;

import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.FeedbackResourceExtensionFactory;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.FeedbackResourceFactory;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.AbstractFeedbackJavaResourceFactoryImpl;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaResourceFactory;
import eu.cloudwave.wp5.feedback.eclipse.performance.PerformancePluginActivator;

/**
 * Implementation of {@link FeedbackJavaResourceFactory}.
 */
public class FeedbackJavaResourceFactoryImpl extends AbstractFeedbackJavaResourceFactoryImpl implements FeedbackJavaResourceFactory {

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

  /**
   * {@inheritDoc}
   */
  @Override
  protected FeedbackJavaResourceFactory getFeedbackJavaResourceFactory() {
    return PerformancePluginActivator.instance(FeedbackJavaResourceFactory.class);
  }
}
