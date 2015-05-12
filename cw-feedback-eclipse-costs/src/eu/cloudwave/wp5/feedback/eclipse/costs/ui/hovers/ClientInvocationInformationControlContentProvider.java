package eu.cloudwave.wp5.feedback.eclipse.costs.ui.hovers;

import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaResourceFactory;
import eu.cloudwave.wp5.feedback.eclipse.base.ui.hovers.contentprovider.AbstractFeedbackInformationControlContentProvider;
import eu.cloudwave.wp5.feedback.eclipse.base.ui.hovers.contentprovider.FeedbackInformationControlContentProvider;
import eu.cloudwave.wp5.feedback.eclipse.costs.core.CostPluginActivator;

/**
 * Implementation of {@link FeedbackInformationControlContentProvider}
 */
public class ClientInvocationInformationControlContentProvider extends AbstractFeedbackInformationControlContentProvider implements FeedbackInformationControlContentProvider {

  @Override
  protected void fillIndividualContent() {

  }

  @Override
  protected FeedbackJavaResourceFactory getFeedbackJavaResourceFactory() {
    return CostPluginActivator.instance(FeedbackJavaResourceFactory.class);
  }
}
