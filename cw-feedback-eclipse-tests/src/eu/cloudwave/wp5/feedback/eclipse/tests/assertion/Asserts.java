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
package eu.cloudwave.wp5.feedback.eclipse.tests.assertion;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Set;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.FeedbackProject;

/**
 * Provides common assert-methods.
 */
public class Asserts {

  /**
   * Asserts that a project has the given nature enabled.
   * 
   * @param project
   *          the project
   * @param natureId
   *          the id of the nature
   */
  public static void assertNatureEnabled(final IProject project, final String natureId) throws CoreException {
    assertThat(isNatureEnabled(project, natureId)).isTrue();
  }

  /**
   * Asserts that a project has the given nature disabled.
   * 
   * @param project
   *          the project
   * @param natureId
   *          the id of the nature
   */
  public static void assertNatureDisabled(final IProject project, final String natureId) throws CoreException {
    assertThat(isNatureEnabled(project, natureId)).isFalse();
  }

  private static boolean isNatureEnabled(final IProject project, final String natureId) throws CoreException {
    for (final String nId : project.getDescription().getNatureIds()) {
      if (nId.equals(natureId)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Asserts that a project has the given markers.
   * 
   * @param project
   *          the project
   * @param markerId
   *          the id of the expected markers
   * @param number
   *          the number of expected markers
   */
  public static void assertHasMarkers(final FeedbackProject project, final String markerId, final int number) {
    assertThat(project.findMarkers(markerId).size()).isEqualTo(number);
  }

  /**
   * Asserts that a project has no markers of a given type.
   * 
   * @param project
   *          the project
   * @param markerId
   *          the type of the unexpected markers
   */
  public static void assertHasNoMarkers(final FeedbackProject project, final String markerId) {
    assertThat(project.findMarkers(markerId).size()).isEqualTo(0);
  }

  /**
   * Asserts that the message attribute a set of markers is equal to the given message.
   * 
   * 
   * @param markers
   *          the set of {@link IMarker}'s to be checked
   * @param expectedMessage
   *          the expected message of the {@link IMarker}'s
   */
  public static void assertMarkerMessages(final Set<IMarker> markers, final String expectedMessage) throws CoreException {
    for (final IMarker marker : markers) {
      assertMarkerMessage(marker, expectedMessage);
    }
  }

  /**
   * Asserts that the message attribute of a marker is equal to the given message.
   * 
   * @param marker
   *          the {@link IMarker}
   * @param expectedMessage
   *          the expected message of the {@link IMarker}
   * @throws CoreException
   */
  public static void assertMarkerMessage(final IMarker marker, final String expectedMessage) throws CoreException {
    assertThat(marker.getAttribute(IMarker.MESSAGE)).isEqualTo(expectedMessage);
  }

}
