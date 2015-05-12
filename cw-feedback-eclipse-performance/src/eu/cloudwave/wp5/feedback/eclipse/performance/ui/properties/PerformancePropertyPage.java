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