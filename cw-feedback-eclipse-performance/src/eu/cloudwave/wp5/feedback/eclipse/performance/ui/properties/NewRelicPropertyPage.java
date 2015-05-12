package eu.cloudwave.wp5.feedback.eclipse.performance.ui.properties;

import eu.cloudwave.wp5.feedback.eclipse.base.ui.properties.AbstractFeedbackPropertyPage;
import eu.cloudwave.wp5.feedback.eclipse.base.ui.properties.PropertyPageStringField;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.properties.PerformanceFeedbackProperties;
import eu.cloudwave.wp5.feedback.eclipse.performance.infrastructure.messages.Messages;

/**
 * Property page for New Relic related properties.
 */
public class NewRelicPropertyPage extends AbstractFeedbackPropertyPage {

  /**
   * {@inheritDoc}
   */
  @Override
  protected String description() {
    return Messages.PROPERTY_PAGES__NEWRELIC__DESCRIPTION;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void addGroups() {
    String groupTitle = Messages.PROPERTIES__GROUP__NEWRELIC;

    PropertyPageStringField newRelicAPIKeyField = new PropertyPageStringField(PerformanceFeedbackProperties.NEW_RELIC__API_KEY, Messages.PROPERTIES__NEWRELIC_API_KEY__LABEL);
    PropertyPageStringField newRelicApplicationIdField = new PropertyPageStringField(PerformanceFeedbackProperties.NEW_RELIC__APPLICATION_ID, Messages.PROPERTIES__NEWRELIC_APPLICATION_ID__LABEL);

    addGroup(groupTitle, newRelicAPIKeyField, newRelicApplicationIdField);
  }
}
