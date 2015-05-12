package eu.cloudwave.wp5.feedback.eclipse;

import com.google.inject.Singleton;

import eu.cloudwave.wp5.feedback.eclipse.performance.ProductionModule;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.feedbackhandler.FeedbackHandlerEclipseClient;
import eu.cloudwave.wp5.feedback.eclipse.tests.mocks.FeedbackHandlerEclipseClientMock;

public class TestModule extends ProductionModule {

  @Override
  protected void overridableConfiguration() {
    bind(FeedbackHandlerEclipseClient.class).to(FeedbackHandlerEclipseClientMock.class).in(Singleton.class);
  }

}
