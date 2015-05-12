package eu.cloudwave.wp5.feedback.eclipse.base.resources.core;

import java.util.Set;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

import com.google.common.collect.ImmutableSet;

/**
 * Tis class provides functionality
 * 
 * Implementation of {@link FeedbackResourceExtension}. Can be added to {@link IResource}'s via composition to overcome
 * the fact that Java doesn't support multiple inheritance.
 */
public class FeedbackResourceExtensionImpl implements FeedbackResourceExtension {

  private final IResource resource;

  private final String type;

  public FeedbackResourceExtensionImpl(final IResource resource, String type) {
    this.resource = resource;
    this.type = type;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteMarkers() throws CoreException {
    resource.deleteMarkers(this.type, true, IResource.DEPTH_INFINITE);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<IMarker> findMarkers(final String id) {
    try {
      return ImmutableSet.copyOf(resource.findMarkers(id, true, IResource.DEPTH_INFINITE));
    }
    catch (final CoreException e) {
      return ImmutableSet.of();
    }
  }
}
