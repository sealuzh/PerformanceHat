package eu.cloudwave.wp5.monitoring.debug;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;

/**
 * Allows to set a debug mode.
 */
public class DebugMode {

  private static boolean isSet = false;
  private static boolean active = false;

  private static final String DEBUG = "-DEBUG";

  /**
   * Returns a boolean indicating whether the debug mode is active or not.
   * 
   * @return <code>true</code> if the debug mode is active, <code>false</code> otherwise
   */
  public static boolean isActive() {
    if (!isSet) {
      set();
    }
    return active;
  }

  /**
   * Sets the debug mode to active if the parameter "-DEBUG" is passed to the JVM when the application is executed. If
   * the debug mode has already been set, nothing is done.
   */
  private static void set() {
    final RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
    final List<String> arguments = runtimeMxBean.getInputArguments();
    for (final String argument : arguments) {
      if (argument.toUpperCase().equals(DEBUG)) {
        active = true;
      }
    }
    isSet = true;
  }
}
