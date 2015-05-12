package eu.cloudwave.wp5.feedback.eclipse.base.infrastructure.logging;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

/**
 * Implementation of {@link StatusFactory}.
 */
public class StatusFactoryImpl implements StatusFactory {

  private static final String METADATA_MSG_PATTERN = "[%s] %s: %s";

  /**
   * {@inheritDoc}
   */
  @Override
  public IStatus status(final Severity severity, final String pluginId, final String message) {
    return new Status(severity.getLevel(), pluginId, message);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IStatus statusWithMetadataInMessage(final Severity severity, final String pluginId, final String message) {
    return status(severity, pluginId, createMetaDataMessage(severity, message));
  }

  private String createMetaDataMessage(final Severity severity, final String message) {
    return String.format(METADATA_MSG_PATTERN, severity.toString(), "Feedback Plug-in", message);
  }

}
