package eu.cloudwave.wp5.feedback.eclipse.costs.core.natures;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

import eu.cloudwave.wp5.feedback.eclipse.base.core.handlers.HandlerToolkit;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.decorators.ProjectNatureProjectDecorator;
import eu.cloudwave.wp5.feedback.eclipse.costs.core.CostIds;

/**
 * Toggles the project nature for this plug-in.
 */
public class CostNatureHandler extends AbstractHandler {

  private static final String FAILED_TO_TOGGLE_PROJECT_NATURE = "Failed to toggle project nature.";

  @Override
  public Object execute(ExecutionEvent event) throws ExecutionException {
    for (final IProject project : HandlerToolkit.getSelectedProjects(event)) {
      try {
        toggleNature(project);
      }
      catch (final CoreException e) {
        throw new ExecutionException(FAILED_TO_TOGGLE_PROJECT_NATURE, e);
      }
    }
    return null;
  }

  private void toggleNature(final IProject project) throws CoreException {
    ProjectNatureProjectDecorator.of(project).toggleNature(CostIds.NATURE);
  }
}
