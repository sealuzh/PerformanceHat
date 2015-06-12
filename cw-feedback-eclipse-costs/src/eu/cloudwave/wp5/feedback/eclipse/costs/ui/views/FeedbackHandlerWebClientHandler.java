package eu.cloudwave.wp5.feedback.eclipse.costs.ui.views;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

import com.google.common.base.Optional;

import eu.cloudwave.wp5.feedback.eclipse.base.core.handlers.HandlerToolkit;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.FeedbackProject;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.FeedbackResourceFactory;
import eu.cloudwave.wp5.feedback.eclipse.costs.core.CostPluginActivator;

public class FeedbackHandlerWebClientHandler extends AbstractHandler {

  private static final String FEEDBACK_HANDLER_WEB_CLIENT_ID = "eu.cloudwave.wp5.feedback.eclipse.costs.ui.views.FeedbackHandlerWebClient";

  private FeedbackResourceFactory feedbackResourceFactory;

  public FeedbackHandlerWebClientHandler() {
    super();
    feedbackResourceFactory = CostPluginActivator.instance(FeedbackResourceFactory.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object execute(ExecutionEvent event) throws ExecutionException {
    try {
      FeedbackHandlerWebClient view = (FeedbackHandlerWebClient) HandlerUtil.getActiveWorkbenchWindow(event).getActivePage().showView(FEEDBACK_HANDLER_WEB_CLIENT_ID);

      String accessToken;
      String applicationId;

      for (final IProject project : HandlerToolkit.getSelectedProjects(event)) {

        Optional<? extends FeedbackProject> decoratedProject;
        try {
          decoratedProject = feedbackResourceFactory.create(project);
          applicationId = decoratedProject.get().getApplicationId();
          accessToken = decoratedProject.get().getAccessToken();

          if (applicationId != null && accessToken != null) {

            // set application in order to be able to login
            view.setApplication(applicationId, accessToken);

            // one project is enough to login
            break;
          }
          else {
            continue;
          }
        }
        catch (Exception e) {
          // lets try with the next project
          continue;
        }
      }
    }
    catch (PartInitException e) {
      e.printStackTrace();
    }
    return null;
  }
}
