package eu.cloudwave.wp5.feedback.eclipse.base.ui.properties;

import eu.cloudwave.wp5.feedback.eclipse.base.core.FeedbackProperties;
import eu.cloudwave.wp5.feedback.eclipse.base.infrastructure.messages.Messages;

/**
 * Main property page of the Feedback plug-in.
 */
public class FeedbackMainPropertyPage extends AbstractFeedbackPropertyPage {

  /**
   * {@inheritDoc}
   */
  @Override
  protected String description() {
    return Messages.PROPERTIES_PAGES__MAIN__DESCRIPTION;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void addGroups() {
    String groupTitle = Messages.PROPERTIES__GROUP__AUTHENTICATION;

    PropertyPageStringField applicationIdField = new PropertyPageStringField(FeedbackProperties.FEEDBACK_HANDLER__APPLICATION_ID, Messages.PROPERTIES__AUTHENTICATION__APPLICATION_ID);
    PropertyPageStringField accessTokenField = new PropertyPageStringField(FeedbackProperties.FEEDBACK_HANDLER__ACCESS_TOKEN, Messages.PROPERTIES__AUTHENTICATION__ACCESS_TOKEN);

    addGroup(groupTitle, applicationIdField, accessTokenField);
  }
}
