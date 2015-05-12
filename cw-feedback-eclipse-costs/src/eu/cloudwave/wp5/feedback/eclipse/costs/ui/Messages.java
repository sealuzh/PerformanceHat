package eu.cloudwave.wp5.feedback.eclipse.costs.ui;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

  private static final String BUNDLE_NAME = "eu.cloudwave.wp5.feedback.eclipse.costs.ui.messages"; //$NON-NLS-1$

  public static String PROPERTIES__GROUP__MICROSERVICE;
  public static String PROPERTY_PAGES__COSTS__DESCRIPTION;
  public static String PROPERTIES__MICROSERVICE_IDENTIFIER__KEY;
  public static String PROPERTIES__MICROSERVICE_IDENTIFIER__LABEL;

  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {}
}
