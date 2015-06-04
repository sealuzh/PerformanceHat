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
package eu.cloudwave.wp5.feedbackhandler.cw.internal;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;

/**
 * In order to tell the feedback handler that CW integration should be used, the
 * CW-Mode has to be activated. The mode is disabled per default, but can be
 * activated with the JVM parameter '-Dcwenabled'.
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
