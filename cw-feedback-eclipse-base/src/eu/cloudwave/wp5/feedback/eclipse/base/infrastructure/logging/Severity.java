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
