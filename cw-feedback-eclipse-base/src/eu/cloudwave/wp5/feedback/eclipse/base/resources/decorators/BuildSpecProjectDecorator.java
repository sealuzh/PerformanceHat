package eu.cloudwave.wp5.feedback.eclipse.base.resources.decorators;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;

import eu.cloudwave.wp5.feedback.eclipse.base.resources.base.AbstractProjectDecorator;

/**
 * A decorator for {@link IProject}'s that provides methods to deal with the build specification of the project.
 */
public class BuildSpecProjectDecorator extends AbstractProjectDecorator {

  private static final IProgressMonitor NULL_PROGRESS_MONITOR = new NullProgressMonitor();

  private BuildSpecProjectDecorator(final IProject project) {
    super(project);
  }

  /**
   * Adds a builder to the build specification of the project (i.e. the buildSpec-Section in the .project-file).
   * 
   * @param builderId
   *          the id of the builder
   * @throws CoreException
   *           if the project does not exist or is not open
   */
  public void addBuilder(final String builderId) throws CoreException {
    final IProjectDescription projectDescription = project.getDescription();
    final ICommand[] currentCommands = projectDescription.getBuildSpec();

    for (int i = 0; i < currentCommands.length; ++i) {
      if (currentCommands[i].getBuilderName().equals(builderId)) {
        return;
      }
    }

    final ICommand newCommand = projectDescription.newCommand();
    newCommand.setBuilderName(builderId);
    final ICommand[] newCommands = new ICommand[currentCommands.length + 1];
    System.arraycopy(currentCommands, 0, newCommands, 0, currentCommands.length);
    newCommands[newCommands.length - 1] = newCommand;
    storeBuildSpecs(projectDescription, newCommands);
  }

  /**
   * Removes a builder from the build specification of the project (i.e. the buildSpec-Section in the .project-file).
   * 
   * @param builderId
   *          the id of the builder
   * @throws CoreException
   *           if the project does not exist or is not open
   */
  public void removeBuilder(final String builderId) throws CoreException {
    final IProjectDescription projectDescription = project.getDescription();
    final ICommand[] currentCommands = projectDescription.getBuildSpec();
    for (int i = 0; i < currentCommands.length; ++i) {
      if (currentCommands[i].getBuilderName().equals(builderId)) {
        final ICommand[] newCommands = new ICommand[currentCommands.length - 1];
        System.arraycopy(currentCommands, 0, newCommands, 0, i);
        System.arraycopy(currentCommands, i + 1, newCommands, i, currentCommands.length - i - 1);
        storeBuildSpecs(projectDescription, newCommands);
        return;
      }
    }
  }

  private void storeBuildSpecs(final IProjectDescription projectDescription, final ICommand[] commands) throws CoreException {
    projectDescription.setBuildSpec(commands);
    project.setDescription(projectDescription, NULL_PROGRESS_MONITOR);
  }

  /**
   * Creates and returns a {@link BuildSpecProjectDecorator}.
   * 
   * @param project
   *          the project
   * @return the created {@link BuildSpecProjectDecorator}
   */
  public static BuildSpecProjectDecorator of(final IProject project) {
    return new BuildSpecProjectDecorator(project);
  }

}
