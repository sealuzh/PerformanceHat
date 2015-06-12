/*******************************************************************************
 * Copyright 2015 Software Evolution and Architecture Lab, University of Zurich
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 ******************************************************************************/
package eu.cloudwave.wp5.feedback.eclipse.costs.ui.properties;

import eu.cloudwave.wp5.common.constants.AggregationInterval;
import eu.cloudwave.wp5.feedback.eclipse.base.ui.properties.AbstractFeedbackPropertyPage;
import eu.cloudwave.wp5.feedback.eclipse.base.ui.properties.PropertyPageComboField;
import eu.cloudwave.wp5.feedback.eclipse.base.ui.properties.PropertyPageDateEndOfDayField;
import eu.cloudwave.wp5.feedback.eclipse.base.ui.properties.PropertyPageDateField;
import eu.cloudwave.wp5.feedback.eclipse.base.ui.properties.PropertyPageStringField;
import eu.cloudwave.wp5.feedback.eclipse.base.ui.properties.PropertyPageToggleField;
import eu.cloudwave.wp5.feedback.eclipse.costs.ui.Messages;

public class CostPropertyPage extends AbstractFeedbackPropertyPage {

  /**
   * {@inheritDoc}
   */
  @Override
  protected String description() {
    return Messages.PROPERTY_PAGES__COSTS__DESCRIPTION;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void addGroups() {
    addIdentifierGroup();
    addFeaturesGroup();
    addTimeParameterGroup();
    addFormattingGroup();
  }

  private void addIdentifierGroup() {
    String microserviceGroupTitle = Messages.PROPERTIES__GROUP__MICROSERVICE;
    PropertyPageStringField microserviceIdentifierField = new PropertyPageStringField(Messages.PROPERTIES__MICROSERVICE_IDENTIFIER__KEY, Messages.PROPERTIES__MICROSERVICE_IDENTIFIER__LABEL);
    addGroup(microserviceGroupTitle, microserviceIdentifierField);
  }

  private void addFeaturesGroup() {
    String featureGroupTitle = Messages.PROPERTIES__GROUP__FEATURES;

    PropertyPageToggleField existingInvocationsAreActivated = new PropertyPageToggleField(Messages.PROPERTIES__FEATURE__HOVER_EXISTING_INVOCATIONS__ISACTIVATED__KEY,
        Messages.PROPERTIES__FEATURE__HOVER_EXISTING_INVOCATIONS__ISACTIVATED__LABEL);
    PropertyPageToggleField newInvocationsAreActivated = new PropertyPageToggleField(Messages.PROPERTIES__FEATURE__HOVER_NEW_INVOCATIONS__ISACTIVATED__KEY,
        Messages.PROPERTIES__FEATURE__HOVER_NEW_INVOCATIONS__ISACTIVATED__LABEL);
    PropertyPageToggleField methodDeclarationsAreActivated = new PropertyPageToggleField(Messages.PROPERTIES__FEATURE__HOVER_METHOD_DECLARATION__ISACTIVATED__KEY,
        Messages.PROPERTIES__FEATURE__HOVER_METHOD_DECLARATION__ISACTIVATED__LABEL);

    addGroup(featureGroupTitle, existingInvocationsAreActivated, newInvocationsAreActivated, methodDeclarationsAreActivated);
  }

  private void addTimeParameterGroup() {
    String timeGroupTitle = Messages.PROPERTIES__GROUP__TIME;

    PropertyPageComboField aggregationInterval = new PropertyPageComboField(Messages.PROPERTIES__TIME__AGGREGATION__INTERVAL__KEY, Messages.PROPERTIES__TIME__AGGREGATION__INTERVAL__LABEL,
        AggregationInterval.POSSIBLE_VALUES);

    PropertyPageToggleField fromDateIsActivated = new PropertyPageToggleField(Messages.PROPERTIES__TIME__FROM__ISACTIVATED__KEY, Messages.PROPERTIES__TIME__FROM__ISACTIVATED__LABEL);
    PropertyPageDateField fromDate = new PropertyPageDateField(Messages.PROPERTIES__TIME__FROM__KEY, Messages.PROPERTIES__TIME__FROM__LABEL);

    PropertyPageToggleField toDateIsActivated = new PropertyPageToggleField(Messages.PROPERTIES__TIME__TO__ISACTIVATED__KEY, Messages.PROPERTIES__TIME__TO__ISACTIVATED__LABEL);
    PropertyPageDateField toDate = new PropertyPageDateEndOfDayField(Messages.PROPERTIES__TIME__TO__KEY, Messages.PROPERTIES__TIME__TO__LABEL);

    addGroup(timeGroupTitle, aggregationInterval, fromDateIsActivated, fromDate, toDateIsActivated, toDate);
  }

  private void addFormattingGroup() {
    String formattingGroupTitle = Messages.PROPERTIES__GROUP__FORMATTING;
    PropertyPageStringField annotationIndentField = new PropertyPageStringField(Messages.PROPERTIES__FORMATTING__ANNOTATION_INDENT__KEY, Messages.PROPERTIES__FORMATTING__ANNOTATION_INDENT__LABEL);
    addGroup(formattingGroupTitle, annotationIndentField);
  }
}
