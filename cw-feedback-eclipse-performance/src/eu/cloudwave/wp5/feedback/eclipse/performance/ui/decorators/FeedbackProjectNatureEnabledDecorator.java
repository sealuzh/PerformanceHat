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
package eu.cloudwave.wp5.feedback.eclipse.performance.ui.decorators;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.eclipse.jface.viewers.LabelProvider;

import com.google.common.base.Optional;

import eu.cloudwave.wp5.feedback.eclipse.performance.Ids;
import eu.cloudwave.wp5.feedback.eclipse.performance.PerformancePluginActivator;

/**
 * Decorates all {@link IJavaProject}'s that have the nature of this plugin enabled with an appropriate icon.
 */
public class FeedbackProjectNatureEnabledDecorator extends LabelProvider implements ILightweightLabelDecorator {

  private static final String DECORATOR_ICON_PATH = "icons/project-decorator.png";

  /**
   * {@inheritDoc}
   */
  @Override
  public void decorate(final Object element, final IDecoration decoration) {
    final Optional<IProject> project = isProject(element);
    if (project.isPresent()) {
      try {
        if (project.get().isNatureEnabled(Ids.NATURE)) {
          decoration.addOverlay(PerformancePluginActivator.getImageDescriptor(DECORATOR_ICON_PATH), IDecoration.TOP_LEFT);
        }
      }
      catch (final CoreException e) {
        // do nothing, decoration is not applied
      }
    }
  }

  /**
   * Checks whether the given element is either an {@link IProject} or an {@link IJavaProject}. Both checks are required
   * because the 'Package Explorer' uses {@link IJavaProject} nodes in its tree while the 'Project Explorer' uses
   * {@link IProject} nodes.
   * 
   * @param element
   *          the element
   * @return the {@link IProject} if the given element is either an {@link IProject} or an {@link IJavaProject} or
   *         {@link Optional#absent()} otherwise
   */
  private Optional<IProject> isProject(final Object element) {
    if (element instanceof IProject) {
      return Optional.of((IProject) element);
    }
    else if (element instanceof IJavaProject) {
      return Optional.of(((IJavaProject) element).getProject());
    }
    return Optional.absent();
  }

}
