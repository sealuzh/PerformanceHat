/*******************************************************************************
 * Copyright 2015 Software Evolution and Architecture Lab, University of Zurich
 * 
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
