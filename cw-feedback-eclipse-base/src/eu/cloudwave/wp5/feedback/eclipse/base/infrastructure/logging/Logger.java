/*******************************************************************************
 * Copyright 2015 Software Evolution and Architecture Lab, University of Zurich
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

import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;

/**
 * Provides static methods to log messages.
 */
public class Logger {

  private Logger() {} // prevents instantiation

  /**
   * Print to console
   * 
   * @param message
   *          content of the logged message
   */
  public static void print(final String message) {
    System.out.println(message);
  }

  /**
   * Log an okay message.
   * 
   * @param message
   *          content of the logged message
   */
  public static void ok(final AbstractUIPlugin activator, final String message) {
    log(activator, Severity.OK, message);
  }

  /**
   * Log an informative message.
   * 
   * @param message
   *          content of the logged message
   */
  public static void info(final AbstractUIPlugin activator, final String message) {
    log(activator, Severity.INFO, message);

  }

  /**
   * Log a warning message.
   * 
   * @param message
   *          content of the logged message
   */
  public static void warning(final AbstractUIPlugin activator, final String message) {
    log(activator, Severity.WARNING, message);
  }

  /**
   * Log an error message.
   * 
   * @param message
   *          content of the logged message
   */
  public static void error(final AbstractUIPlugin activator, final String message) {
    log(activator, Severity.ERROR, message);
  }

  /**
   * Log a cancellation message.
   * 
   * @param message
   *          content of the logged message
   */
  public static void cancel(final AbstractUIPlugin activator, final String message) {
    log(activator, Severity.CANCEL, message);
  }

  private static void log(final AbstractUIPlugin activator, final Severity severity, final String message) {
    final Bundle bundle = activator.getBundle();
    final StatusFactory statusFactory = new StatusFactoryImpl();
    Platform.getLog(bundle).log(statusFactory.statusWithMetadataInMessage(severity, activator.getBundle().getSymbolicName(), message));
  }

}
