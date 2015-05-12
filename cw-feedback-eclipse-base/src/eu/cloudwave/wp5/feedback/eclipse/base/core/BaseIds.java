package eu.cloudwave.wp5.feedback.eclipse.base.core;

public class BaseIds {

  /**
   * The id of the plug-in.
   * 
   * IMPORTANT: Has to be in sync with the 'Bundle-SymbolicName' property of the MANIFEST.MF. Otherwise the
   * {@link BasePluginActivator#getImageDescriptor(String)} method does not work.
   */
  public static final String BASEPLUGIN = "eu.cloudwave.wp5.feedback.eclipse.base"; //$NON-NLS-1$

  /**
   * The Id used for external purposes (i.e. visible for users).
   */
  public static final String ID = "eu.cloudwave.wp5.feedback";

  private static final String DOT = "."; //$NON-NLS-1$

  private static final String PLUGIN_QUALIFIER = BASEPLUGIN + DOT;

  public static final String MARKER = PLUGIN_QUALIFIER + "markers.FeedbackMarker"; //$NON-NLS-1$

  /*
   * Property Page
   */

  private static final String UI_QUALIFIER = "eu.cloudwave.wp5.feedback.eclipse.performance.core.ui" + DOT; //$NON-NLS-1$

  private static final String PROPERTIES_PAGE_QUALIFIER = UI_QUALIFIER + "properties" + DOT; //$NON-NLS-1$

  public static final String PREFERENCE_PAGE = UI_QUALIFIER + "preferences.FeedbackMainPreferencePage"; //$NON-NLS-1$
  public static final String PROPERTIES_PAGE__FDD_MAIN = PROPERTIES_PAGE_QUALIFIER + "FeedbackMainPropertyPage"; //$NON-NLS-1$

  private BaseIds() {}
}
