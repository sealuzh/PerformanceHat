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
