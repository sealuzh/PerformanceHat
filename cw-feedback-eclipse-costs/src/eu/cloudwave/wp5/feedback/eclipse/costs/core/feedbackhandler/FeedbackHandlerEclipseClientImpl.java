package eu.cloudwave.wp5.feedback.eclipse.costs.core.feedbackhandler;

import com.google.inject.Inject;

import eu.cloudwave.wp5.common.dto.AggregatedMicroserviceRequestsDto;
import eu.cloudwave.wp5.feedback.eclipse.base.core.preferences.FeedbackPreferences;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.FeedbackProject;

/**
 * Implementation of {@link FeedbackHandlerEclipseClient}.
 */
public class FeedbackHandlerEclipseClientImpl implements FeedbackHandlerEclipseClient {

  @Inject
  private FeedbackHandlerClientFactory feedbackHandlerClientFactory;

  private FeedbackHandlerClient feedbackHandlerClient;

  /**
   * {@inheritDoc}
   */
  @Override
  public AggregatedMicroserviceRequestsDto[] all(final FeedbackProject project) {
    return feedbackHandlerClient().allRequests(project.getAccessToken(), project.getApplicationId());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AggregatedMicroserviceRequestsDto[] requestsByCallee(FeedbackProject project) {
    return feedbackHandlerClient().requestsByCallee(project.getAccessToken(), project.getApplicationId());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AggregatedMicroserviceRequestsDto[] requestsByCaller(FeedbackProject project) {
    return feedbackHandlerClient().requestsByCaller(project.getAccessToken(), project.getApplicationId());
  }

  private FeedbackHandlerClient feedbackHandlerClient() {
    if (feedbackHandlerClient == null) {
      final String rootUrl = FeedbackPreferences.getString(FeedbackPreferences.FEEDBACK_HANDLER__URL);
      feedbackHandlerClient = feedbackHandlerClientFactory.create(rootUrl);
    }
    return feedbackHandlerClient;
  }
}
