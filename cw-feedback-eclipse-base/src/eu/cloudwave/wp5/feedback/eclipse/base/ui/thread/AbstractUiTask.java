package eu.cloudwave.wp5.feedback.eclipse.base.ui.thread;

import org.eclipse.swt.widgets.Display;

/**
 * Base class for tasks that are executed asynchronously in the UI thread at the next reasonable opportunity.
 */
public abstract class AbstractUiTask {

  /**
   * The work that has to be done.
   */
  protected abstract void doWork();

  /**
   * Runs the current task in the UI thread.
   */
  public final void run() {
    Display.getDefault().asyncExec(new Runnable() {
      @Override
      public void run() {
        doWork();
      }
    });
  }

}
