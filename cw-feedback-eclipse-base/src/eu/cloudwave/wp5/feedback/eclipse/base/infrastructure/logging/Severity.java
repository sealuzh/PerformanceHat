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

/**
 * Represents the Severity of an {@link IStatus}. This is a nicer alternative to the integer constants used in
 * {@link IStatus}.
 */
public enum Severity {

  OK("OK", IStatus.OK), INFO("INFO", IStatus.INFO), WARNING("WARNING", IStatus.WARNING), ERROR("ERROR", IStatus.ERROR), CANCEL("CANCEL", IStatus.CANCEL);

  private final String name;
  private final int level;

  private Severity(final String name, final int level) {
    this.name = name;
    this.level = level;
  }

  @Override
  public String toString() {
    return name;
  }

  /**
   * Returns the appropriate integer value of the severity (see also {@link IStatus}).
   * 
   * @return integer value of the severity
   */
  public int getLevel() {
    return level;
  }

  /**
   * Returns the respective {@link Severity} for a given integer value (see also {@link IStatus}).
   * 
   * @param level
   *          log severity level
   * @return the respective {@link Severity}
   */
  public static Severity get(final int level) {
    for (final Severity logSeverity : Severity.values()) {
      if (logSeverity.getLevel() == level) {
        return logSeverity;
      }
    }
    return Severity.INFO;
  }
}
