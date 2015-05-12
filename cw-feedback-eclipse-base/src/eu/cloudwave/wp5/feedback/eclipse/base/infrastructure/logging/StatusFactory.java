package eu.cloudwave.wp5.feedback.eclipse.base.infrastructure.logging;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

/**
 * Factory to create {@link IStatus}.
 */
public interface StatusFactory {

  /**
   * Creates a new {@link IStatus}. This is a simplified alternative to the constructor offered by the {@link Status}
   * class which requires the severity as integer and additionally requires the plug-in ID.
   * 
   * @param message
   *          the message of the status
   * @return the created {@link IStatus}
   */
  public IStatus status(final Severity severity, final String pluginId, final String message);

  /**
   * Creates a new {@link IStatus} and appends meta-data (severity and plug-in name) to the message. The resulting
   * message has the format: [<SEVERITY>] <Plug-in name>: <Actual message>. This is useful to see the severity when
   * logging to the console in development.
   * 
   * @param severity
   *          the {@link Severity} of the status
   * @param message
   *          the message of the status
   * @return the created {@link IStatus}
   */
  public IStatus statusWithMetadataInMessage(final Severity severity, final String pluginId, final String message);

}
