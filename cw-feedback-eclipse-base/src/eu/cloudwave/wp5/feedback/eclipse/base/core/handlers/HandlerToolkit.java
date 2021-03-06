/*******************************************************************************
 * Copyright 2015 Software Evolution and Architecture Lab, University of Zurich
 * 
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
package eu.cloudwave.wp5.feedback.eclipse.base.core.handlers;

import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

/**
 * Provides utility methods that can be used by handlers.
 */
public class HandlerToolkit {

  private HandlerToolkit() {} // prevents instantiation

  /**
   * Returns all projects that are currently selected.
   * 
   * @param event
   *          {@link ExecutionEvent}
   * @return a {@link Set} containing all currently selected {@link IProject}'s
   */
  public static Set<IProject> getSelectedProjects(final ExecutionEvent event) {
    final Set<IProject> selectedProjects = Sets.newHashSet();
    final ISelection selection = HandlerUtil.getCurrentSelection(event);
    if (selection instanceof IStructuredSelection) {
      final IStructuredSelection structuredSelection = (IStructuredSelection) selection;
      for (final Iterator<?> it = structuredSelection.iterator(); it.hasNext();) {
        final Object selectedElement = it.next();
        if (selectedElement instanceof IProject) {
          selectedProjects.add((IProject) selectedElement);
        }
        else if (selectedElement instanceof IAdaptable) {
          selectedProjects.add((IProject) ((IAdaptable) selectedElement).getAdapter(IProject.class));
        }
      }
    }
    return ImmutableSet.copyOf(selectedProjects);
  }

}
