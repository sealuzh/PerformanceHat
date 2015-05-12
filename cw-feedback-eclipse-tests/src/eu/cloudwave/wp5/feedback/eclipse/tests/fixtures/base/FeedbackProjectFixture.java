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
