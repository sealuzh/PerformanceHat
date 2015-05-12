/*******************************************************************************
 * Copyright 2015 Software Evolution and Architecture Lab, University of Zurich
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package eu.cloudwave.wp5.feedback.eclipse.performance.core.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.PlatformUI;

import eu.cloudwave.wp5.feedback.eclipse.base.core.handlers.HandlerToolkit;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.decorators.ProjectNatureProjectDecorator;
import eu.cloudwave.wp5.feedback.eclipse.base.ui.thread.AbstractUiTask;
import eu.cloudwave.wp5.feedback.eclipse.performance.Ids;

/**
 * Toggles the project nature for this plug-in.
 */
public class ToggleProjectNatureHandler extends AbstractHandler {

  private static final String FAILED_TO_TOGGLE_PROJECT_NATURE = "Failed to toggle project nature.";

  /**
   * {@inheritDoc}
   */
  @Override
  public Object execute(final ExecutionEvent event) throws ExecutionException {
    for (final IProject project : HandlerToolkit.getSelectedProjects(event)) {
      try {
        toggleNature(project);
        updateProjectDecorators();
      }
      catch (final CoreException e) {
        throw new ExecutionException(FAILED_TO_TOGGLE_PROJECT_NATURE, e);
      }
    }
    return null;
  }

  private void toggleNature(final IProject project) throws CoreException {
    ProjectNatureProjectDecorator.of(project).toggleNature(Ids.NATURE);

  }

  private void updateProjectDecorators() {
    new AbstractUiTask() {
      @Override
      public void doWork() {
        PlatformUI.getWorkbench().getDecoratorManager().update(Ids.PROJECT_NATURE_ENABLED_DECORATOR);
      }
    }.run();
  }
}
