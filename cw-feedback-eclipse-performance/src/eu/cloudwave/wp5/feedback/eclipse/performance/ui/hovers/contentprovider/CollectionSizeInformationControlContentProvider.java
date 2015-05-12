package eu.cloudwave.wp5.feedback.eclipse.performance.ui.hovers.contentprovider;

import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaResourceFactory;
import eu.cloudwave.wp5.feedback.eclipse.base.ui.hovers.contentprovider.AbstractFeedbackInformationControlContentProvider;
import eu.cloudwave.wp5.feedback.eclipse.base.ui.hovers.contentprovider.FeedbackInformationControlContentProvider;
import eu.cloudwave.wp5.feedback.eclipse.performance.PerformancePluginActivator;

/**
 * Implementation of {@link FeedbackInformationControlContentProvider} for the hotspot marker.
 */
public class CollectionSizeInformationControlContentProvider extends AbstractFeedbackInformationControlContentProvider implements FeedbackInformationControlContentProvider {

  @Override
  protected void fillIndividualContent() {}

  @Override
  protected FeedbackJavaResourceFactory getFeedbackJavaResourceFactory() {
    return PerformancePluginActivator.instance(FeedbackJavaResourceFactory.class);
  }
}
