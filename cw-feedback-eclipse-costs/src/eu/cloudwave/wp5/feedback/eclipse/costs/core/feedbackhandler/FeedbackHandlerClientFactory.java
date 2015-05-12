package eu.cloudwave.wp5.feedback.eclipse.costs.core.feedbackhandler;

/**
 * A factory for {@link FeedbackHandlerClient}'s.
 * 
 * The appropriate implementation is created by Guice.
 */
public interface FeedbackHandlerClientFactory {

  /**
   * Creates a {@link FeedbackHandlerClient}.
   * 
   * @param rootUrl
   *          the root URL of the feedback handler
   * @return the created {@link FeedbackHandlerClient}
   */
  public FeedbackHandlerClient create(String rootUrl);

}
