package eu.cloudwave.wp5.feedback.eclipse.base.ui.hovers.contentprovider;

import java.util.Map;

import com.google.common.collect.Maps;

import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.FeedbackMarkerType;

/**
 * Implementation of {@link FeedbackInformationControlContentProviderRegistry}.
 */
public class FeedbackInformationControlContentProviderRegistryImpl implements FeedbackInformationControlContentProviderRegistry {

  private Map<FeedbackMarkerType, FeedbackInformationControlContentProvider> registry;

  public FeedbackInformationControlContentProviderRegistryImpl() {
    registry = Maps.newHashMap();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public FeedbackInformationControlContentProvider get(final FeedbackMarkerType type) {
    return registry.get(type);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void register(FeedbackMarkerType type, FeedbackInformationControlContentProvider contentProvider) {
    registry.put(type, contentProvider);
  }

}
