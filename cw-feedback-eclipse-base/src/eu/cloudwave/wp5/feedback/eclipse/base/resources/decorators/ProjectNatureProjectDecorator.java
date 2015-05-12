package eu.cloudwave.wp5.feedback.eclipse.base.resources.decorators;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;

import eu.cloudwave.wp5.feedback.eclipse.base.infrastructure.logging.Logger;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.base.AbstractProjectDecorator;

/**
 * A decorator for {@link IProject}'s that provides methods to deal with the project natures of the project.
 */
public class ProjectNatureProjectDecorator extends AbstractProjectDecorator {

  private static final IProgressMonitor NULL_PROGRESS_MONITOR = new NullProgressMonitor();
  private static final String LOG_MSG_PATTERN = "Project '%s': %s nature '%s'.";
  private static final String ADDED = "Added";
  private static final String REMOVED = "Removed";

  private ProjectNatureProjectDecorator(final IProject project) {
    super(project);
  }

  /**
   * Toggles a project nature on the given project (i.e. adds the nature if it is not yet specified on the project or
   * removes it otherwise).
   * 
   * @param natureId
   *          the id of the project nature
   * @throws CoreException
   *           if the project does not exist or is not open
   */
  public void toggleNature(final String natureId) throws CoreException {
    if (project.getNature(natureId) == null) {
      addNature(natureId);
    }
    else {
      removeNature(natureId);
    }
  }

  /**
   * Adds a project nature to the project.
   * 
   * @param natureId
   *          the id of the project nature
   * @throws CoreException
   *           if the project does not exist or is not open
   */
  public void addNature(final String natureId) throws CoreException {
    final IProjectDescription projectDescription = project.getDescription();
    final String[] currentNatures = projectDescription.getNatureIds();
    final String[] newNatures = new String[currentNatures.length + 1];
    System.arraycopy(currentNatures, 0, newNatures, 0, currentNatures.length);
    newNatures[currentNatures.length] = natureId;
    storeNatures(projectDescription, newNatures);
    Logger.print(String.format(LOG_MSG_PATTERN, projectDescription.getName(), ADDED, natureId));
  }

  /**
   * Removes a project nature from the project.
   * 
   * @param natureId
   *          the id of the project nature
   * @throws CoreException
   *           if the project does not exist or is not open
   */
  public void removeNature(final String natureId) throws CoreException {
    final IProjectDescription projectDescription = project.getDescription();
    final String[] currentNatures = projectDescription.getNatureIds();
    for (int i = 0; i < currentNatures.length; ++i) {
      if (currentNatures[i].equals(natureId)) {
        final String[] newNatures = new String[currentNatures.length - 1];
        System.arraycopy(currentNatures, 0, newNatures, 0, i);
        System.arraycopy(currentNatures, i + 1, newNatures, i, currentNatures.length - i - 1);
        storeNatures(projectDescription, newNatures);
        Logger.print(String.format(LOG_MSG_PATTERN, projectDescription.getName(), REMOVED, natureId));
        return;
      }
    }
  }

  private void storeNatures(final IProjectDescription projectDescription, final String[] nautureIds) throws CoreException {
    projectDescription.setNatureIds(nautureIds);
    project.setDescription(projectDescription, NULL_PROGRESS_MONITOR);
  }

  /**
   * Creates and returns a {@link ProjectNatureProjectDecorator}.
   * 
   * @param project
   *          the project
   * @return the created {@link ProjectNatureProjectDecorator}
   */
  public static ProjectNatureProjectDecorator of(final IProject project) {
    return new ProjectNatureProjectDecorator(project);
  }

}
