package eu.cloudwave.wp5.feedback.eclipse.performance.infrastructure.messages;

import org.eclipse.osgi.util.NLS;

import eu.cloudwave.wp5.feedback.eclipse.performance.Ids;

/**
 * Provides messages.
 */
public class Messages extends NLS {
  private static final String BUNDLE_NAME = Ids.PLUGIN + ".infrastructure.messages.messages"; //$NON-NLS-1$

  // Properties: Performance Hat Thresholds
  public static String PROPERTIES__GROUP__THRESHOLDS;
  public static String PROPERTY_PAGES__PERFORMANCE__DESCRIPTION;
  public static String PROPERTIES__THRESHOLDS__HOTSPOTS;
  public static String PROPERTIES__THRESHOLDS__LOOPS;

  // Properties: New Relic Tab
  public static String PROPERTIES__GROUP__NEWRELIC;
  public static String PROPERTY_PAGES__NEWRELIC__DESCRIPTION;
  public static String PROPERTIES__NEWRELIC_API_KEY__LABEL;
  public static String PROPERTIES__NEWRELIC_APPLICATION_ID__LABEL;

  // Hotspots Hover
  public static String BUTTON_CW_DASHBOARD;
  public static String BUTTON_FURTHER_INFO;

  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {}
}
