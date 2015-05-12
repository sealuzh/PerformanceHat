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
package eu.cloudwave.wp5.feedbackhandler.tests.assertion;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Provides common assert-methods.
 */
public class Asserts {

  /**
   * Asserts that the given {@link String}'s are set (i.e. not <code>null</code> and not empty).
   * 
   * @param strings
   *          the {@link String}'s to be checked
   */
  public static void assertSet(final String... strings) {
    for (final String string : strings) {
      assertThat(string).isNotNull();
      assertThat(string).isNotEmpty();
    }
  }

  private Asserts() {}

}
