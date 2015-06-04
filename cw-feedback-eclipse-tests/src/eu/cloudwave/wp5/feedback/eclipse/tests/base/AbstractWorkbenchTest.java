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
package eu.cloudwave.wp5.feedback.eclipse.tests.base;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.jobs.Job;
import org.junit.After;
import org.junit.Before;

import eu.cloudwave.wp5.feedback.eclipse.tests.fixtures.base.FeedbackProjectFixture;
import eu.cloudwave.wp5.feedback.eclipse.tests.fixtures.samples.SampleProject;

public class AbstractWorkbenchTest {

  protected FeedbackProjectFixture sampleProject;

  @Before
  public void setupProject() throws CoreException, UnsupportedEncodingException, IOException {
    sampleProject = new SampleProject();
  }

  @After
  public void destroyProject() throws CoreException {
    sampleProject.dispose();
  }

  protected void enableFeedbackNature() throws CoreException, OperationCanceledException, InterruptedException {
    sampleProject.addFeedbackNature();
    waitForAutoBuild();
  }

  /**
   * Waits for an auto build to be completed.
   */
  protected void waitForAutoBuild() throws OperationCanceledException, InterruptedException {
    Job.getJobManager().join(ResourcesPlugin.FAMILY_AUTO_BUILD, null);
  }

}
