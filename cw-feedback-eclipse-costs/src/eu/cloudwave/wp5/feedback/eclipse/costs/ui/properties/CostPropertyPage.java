/*******************************************************************************
 * Copyright 2015 Software Evolution and Architecture Lab, University of Zurich
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
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
