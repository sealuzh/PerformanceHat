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
