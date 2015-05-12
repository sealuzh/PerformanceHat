package eu.cloudwave.wp5.feedback.eclipse.base.ui.hovers.contentprovider;

import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.FeedbackMarkerType;

/**
 * This class binds each marker type to the respective {@link FeedbackInformationControlContentProvider}.
 */
public interface FeedbackInformationControlContentProviderRegistry {

  public static FeedbackInformationControlContentProviderRegistry INSTANCE = new FeedbackInformationControlContentProviderRegistryImpl();

  /**
   * Returns the corresponding {@link FeedbackInformationControlContentProvider} for the given
   * {@link PerformanceMarkerType}
   * 
   * @param type
   *          the type of the marker
   * @return the corresponding {@link FeedbackInformationControlContentProvider} for the given
   *         {@link PerformanceMarkerType}
   */
  public FeedbackInformationControlContentProvider get(FeedbackMarkerType type);

  /**
   * Register a new mapping between {@link PerformanceMarkerType} and {@link FeedbackInformationControlContentProvider}.
   * Supposed to be called in the concrete plugins.
   * 
   * @param type
   *          the type of the marker
   * @param contentProvider
   *          the corresponding {@link FeedbackInformationControlContentProvider} for the given
   *          {@link PerformanceMarkerType}
   */
  public void register(FeedbackMarkerType type, FeedbackInformationControlContentProvider contentProvider);

}
