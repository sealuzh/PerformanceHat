package eu.cloudwave.wp5.feedback.eclipse.base.resources.core;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;

import com.google.common.base.Optional;

/**
 * A factory for feedback resources.
 */
public interface FeedbackResourceFactory {

  /**
   * Creates a {@link FeedbackResource}.
   * 
   * @param resource
   *          the {@link IResource}
   * @return the created {@link FeedbackResource} or {@link Optional#absent()}, if the given {@link IResource} is
   *         <code>null</code>
   */
  public Optional<? extends FeedbackResource> create(IResource resource);

  /**
   * Creates a {@link FeedbackProject}.
   * 
   * @param project
   *          the {@link IProject}
   * @return the created {@link FeedbackProject} or {@link Optional#absent()}, if the given {@link IProject} is
   *         <code>null</code>
   */
  public Optional<? extends FeedbackProject> create(IProject project);

  /**
   * Creates a {@link FeedbackFile}.
   * 
   * @param file
   *          the {@link IFile}
   * @return the created {@link FeedbackFile} or {@link Optional#absent()}, if the given {@link IFile} is
   *         <code>null</code>
   */
  public Optional<? extends FeedbackFile> create(IFile file);

  /**
   * Creates a {@link FeedbackResourceDelta}.
   * 
   * @param resourceDelta
   *          the {@link IResourceDelta}
   * @return the created {@link FeedbackResourceDelta} or {@link Optional#absent()}, if the given {@link IResourceDelta}
   *         is <code>null</code>
   */
  public Optional<? extends FeedbackResourceDelta> create(IResourceDelta resourceDelta);
}
