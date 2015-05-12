package eu.cloudwave.wp5.feedback.eclipse.costs.ui.properties;

import eu.cloudwave.wp5.feedback.eclipse.base.ui.properties.AbstractFeedbackPropertyPage;
import eu.cloudwave.wp5.feedback.eclipse.base.ui.properties.PropertyPageStringField;
import eu.cloudwave.wp5.feedback.eclipse.costs.ui.Messages;

public class CostPropertyPage extends AbstractFeedbackPropertyPage {

  @Override
  protected String description() {
    // TODO Auto-generated method stub
    return Messages.PROPERTY_PAGES__COSTS__DESCRIPTION;
  }

  @Override
  protected void addGroups() {
    String groupTitle = eu.cloudwave.wp5.feedback.eclipse.base.infrastructure.messages.Messages.PROPERTIES__GROUP__AUTHENTICATION;
    // String microserviceGroupTitle = Messages.PROPERTIES__GROUP__MICROSERVICE;
    PropertyPageStringField microserviceIdentifierField = new PropertyPageStringField(Messages.PROPERTIES__MICROSERVICE_IDENTIFIER__KEY, Messages.PROPERTIES__MICROSERVICE_IDENTIFIER__LABEL);

    addGroup(groupTitle, microserviceIdentifierField);
  }
}
