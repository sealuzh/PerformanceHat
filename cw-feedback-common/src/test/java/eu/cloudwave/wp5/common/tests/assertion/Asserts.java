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
package eu.cloudwave.wp5.common.tests.assertion;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.fest.assertions.Assertions.assertThat;

import java.util.List;

/**
 * Provides common assert-methods.
 */
public class Asserts {

  private Asserts() {}

  /**
   * Can be used to test the equals- and hashCode-methods of a class.
   * 
   * @param equalObjects
   *          a list of objects that are expected to be equal (minimum size of 2 objects is required)
   * @param differentObjects
   *          a list of objects that are expected to be different from the equal objects
   */
  public static <T extends Object> void assertEquality(final List<T> equalObjects, final List<T> differentObjects) {
    // check preconditions
    checkNotNull(equalObjects);
    checkNotNull(differentObjects);
    checkArgument(equalObjects.size() >= 2);

    // check equal objects
    for (int i = 0; i < equalObjects.size(); i++) {
      for (int j = i + 1; j < equalObjects.size(); j++) {
        assertObjectsAreEqual(equalObjects.get(i), equalObjects.get(j));
      }
    }

    // check different objects
    for (int i = 0; i < differentObjects.size(); i++) {
      assertObjectsAreNotEqual(equalObjects.get(0), differentObjects.get(i));
    }
  }

  /**
   * Asserts that two object are equal.
   * 
   * @param one
   *          the first object
   * @param two
   *          the second object
   */
  public static void assertObjectsAreEqual(final Object one, final Object two) {
    assertThat(one).isEqualTo(two);
    assertThat(one.hashCode()).isEqualTo(two.hashCode());
  }

  /**
   * Asserts that two objects are not equal.
   * 
   * @param one
   *          the first object
   * @param two
   *          the second object
   */
  public static void assertObjectsAreNotEqual(final Object one, final Object two) {
    assertThat(one).isNotEqualTo(two);
  }

}
