package eu.cloudwave.wp5.feedback.eclipse.base.resources.core;

import org.eclipse.core.resources.IResource;

/**
 * A factory for feedback resource extensions.
 */
public interface FeedbackResourceExtensionFactory {

  /**
   * Creates a {@link FeedbackResourceExtension}.
   * 
   * @param resource
   *          the {@link IResource}
   * @return the created {@link FeedbackResourceExtension}
   */
  public FeedbackResourceExtension create(IResource resource);
}
