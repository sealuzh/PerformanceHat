package eu.cloudwave.wp5.feedbackhandler.cw.internal;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;

/**
 * In order to tell the feedback handler that CW integration should be used, the
 * CW-Mode has to be activated. The mode is activated per default, but can be
 * deactivated with the JVM parameter '-Dnocw'.
 * 
 * This class provides a method to check whether the CW-Mode is active or not.
 */
public class CwMode {

	private static boolean isSet = false;
	private static boolean cwmode = true;

	private static final String VM_PARAM = "-DCWENABLED";

	/**
	 * Returns a boolean indicating whether the CW-Mode is active or not.
	 * 
	 * @return <code>true</code> if the CW-Mode is active, <code>false</code>
	 *         otherwise
	 */
	public static boolean isActive() {
		if (!isSet) {
			set();
		}
		return cwmode;
	}

	/**
	 * Sets the CW-Mode to active if the parameter "-Dcwenabled" is passed to
	 * the JVM when the application is executed. If the CW-Mode has already been
	 * set, nothing is done.
	 */
	private static void set() {
		final RuntimeMXBean runtimeMxBean = ManagementFactory
				.getRuntimeMXBean();
		final List<String> arguments = runtimeMxBean.getInputArguments();
		for (final String argument : arguments) {
			if (argument.toUpperCase().equals(VM_PARAM)) {
				cwmode = true;
				isSet = true;
				return;
			}
		}
		cwmode = false;
		isSet = false;
	}

}
