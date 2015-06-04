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
