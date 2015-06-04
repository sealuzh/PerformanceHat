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
package eu.cloudwave.wp5.feedback.eclipse.tests.fixtures.base;

import org.eclipse.core.runtime.CoreException;
import org.osgi.service.prefs.BackingStoreException;

import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaProject;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.decorators.ProjectNatureProjectDecorator;
import eu.cloudwave.wp5.feedback.eclipse.performance.Ids;
import eu.cloudwave.wp5.feedback.eclipse.performance.resources.core.java.FeedbackJavaResourceFactoryImpl;

/**
 * An extension of {@link JavaProjectFixture} containing feedback plug-in specific functionality.
 */
public class FeedbackProjectFixture extends JavaProjectFixture {

  private FeedbackJavaProject feedbackProject;

  public FeedbackProjectFixture(final String name) throws CoreException {
    super(name);
    this.feedbackProject = new FeedbackJavaResourceFactoryImpl().create(getProject()).get();
  }

  public FeedbackJavaProject getFeedbackProject() {
    return feedbackProject;
  }

  /**
   * Adds the feedback nature to the project.
   */
  public void addFeedbackNature() throws CoreException {
    ProjectNatureProjectDecorator.of(getProject()).addNature(Ids.NATURE);
  }

  @Override
  public void dispose() throws CoreException {
    disposeProjectProperties();
    super.dispose();
  }

  private void disposeProjectProperties() {
    try {
      getFeedbackProject().getFeedbackProperties().removeNode();
    }
    catch (final BackingStoreException e) {
      e.printStackTrace();
    }
  }
}
