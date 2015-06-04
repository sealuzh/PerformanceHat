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
