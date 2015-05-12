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
package eu.cloudwave.wp5.feedback.eclipse.performance.ui.properties;

import eu.cloudwave.wp5.feedback.eclipse.base.ui.properties.AbstractFeedbackPropertyPage;
import eu.cloudwave.wp5.feedback.eclipse.base.ui.properties.PropertyPageStringField;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.properties.PerformanceFeedbackProperties;
import eu.cloudwave.wp5.feedback.eclipse.performance.infrastructure.messages.Messages;

/**
 * Property page for Performance Hat related properties.
 */
public class PerformancePropertyPage extends AbstractFeedbackPropertyPage {

  /**
   * {@inheritDoc}
   */
  @Override
  protected String description() {
    return Messages.PROPERTY_PAGES__PERFORMANCE__DESCRIPTION;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void addGroups() {
    String groupTitle = Messages.PROPERTIES__GROUP__THRESHOLDS;

    PropertyPageStringField thresholdHotspotsField = new PropertyPageStringField(PerformanceFeedbackProperties.TRESHOLD__HOTSPOTS, Messages.PROPERTIES__THRESHOLDS__HOTSPOTS);
    PropertyPageStringField thresholdLoopsField = new PropertyPageStringField(PerformanceFeedbackProperties.TRESHOLD__LOOPS, Messages.PROPERTIES__THRESHOLDS__LOOPS);

    addGroup(groupTitle, thresholdHotspotsField, thresholdLoopsField);
  }
}
