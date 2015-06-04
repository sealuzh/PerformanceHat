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
package eu.cloudwave.wp5.feedback.eclipse.performance.core.natures;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

import com.google.common.base.Optional;

import eu.cloudwave.wp5.feedback.eclipse.base.core.builders.FeedbackBuilder;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.FeedbackProject;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.FeedbackResourceFactory;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.decorators.BuildSpecProjectDecorator;
import eu.cloudwave.wp5.feedback.eclipse.performance.Ids;
import eu.cloudwave.wp5.feedback.eclipse.performance.PerformancePluginActivator;

/**
 * Constitute the {@link IProjectNature} of the feedback plug-in. Activates the {@link FeedbackBuilder}.
 */
public class PerformanceProjectNature implements IProjectNature {

  private FeedbackResourceFactory feedbackResourceFactory;

  private IProject project;

  public PerformanceProjectNature() {
    feedbackResourceFactory = PerformancePluginActivator.instance(FeedbackResourceFactory.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void configure() throws CoreException {
    addBuilder();
  }

  private void addBuilder() throws CoreException {
    BuildSpecProjectDecorator.of(getProject()).addBuilder(Ids.BUILDER);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deconfigure() throws CoreException {
    removeBuilder();
    deleteMarkers();
  }

  private void removeBuilder() throws CoreException {
    BuildSpecProjectDecorator.of(getProject()).removeBuilder(Ids.BUILDER);
  }

  private void deleteMarkers() throws CoreException {
    final Optional<? extends FeedbackProject> feedbackProjectOptional = feedbackResourceFactory.create(getProject());
    if (feedbackProjectOptional.isPresent()) {
      feedbackProjectOptional.get().deleteMarkers();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IProject getProject() {
    return project;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setProject(final IProject project) {
    this.project = project;
  }

}
