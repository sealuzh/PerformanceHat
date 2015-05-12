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
package eu.cloudwave.wp5.common.util;

/**
 * This class provides methods to compute good hash codes for combinations of primitive data types. The underlying
 * principle is adopted from the book "Effective Java" from Joshua Bloch (Item 9: Always override hashCode when you
 * override equals).
 */
public class HashCodes {

  private static final int INITIAL_RESULT = 17;
  private static final int PRIME = 31;

  private HashCodes() {}

  /**
   * Returns a combined has code of the given list of hash codes.
   * 
   * @param hashCodes
   *          the list of hash codes to combine
   * @return a combined has code of the given list of hash codes
   */
  public static int combinedHashCode(final Integer... hashCodes) {
    int result = INITIAL_RESULT;
    for (final Integer hashCode : hashCodes) {
      result = PRIME * result + hashCode;
    }
    return result;
  }

  /**
   * Returns a hash code for the given value.
   * 
   * @param value
   *          the value
   * @return a hash code for the given value
   */
  public static int hashCode(final boolean value) {
    return (value ? 1 : 0);
  }

  /**
   * Returns a hash code for the given value.
   * 
   * @param value
   *          the value
   * @return a hash code for the given value
   */
  public static int hashCode(final byte value) {
    return value;
  }

  /**
   * Returns a hash code for the given value.
   * 
   * @param value
   *          the value
   * @return a hash code for the given value
   */
  public static int hashCode(final char value) {
    return value;
  }

  /**
   * Returns a hash code for the given value.
   * 
   * @param value
   *          the value
   * @return a hash code for the given value
   */
  public static int hashCode(final short value) {
    return value;
  }

  /**
   * Returns a hash code for the given value.
   * 
   * @param value
   *          the value
   * @return a hash code for the given value
   */
  public static int hashCode(final int value) {
    return value;
  }

  /**
   * Returns a hash code for the given value.
   * 
   * @param value
   *          the value
   * @return a hash code for the given value
   */
  public static int hashCode(final long value) {
    return (int) (value ^ (value >>> 32));
  }

  /**
   * Returns a hash code for the given value.
   * 
   * @param value
   *          the value
   * @return a hash code for the given value
   */
  public static int hashCode(final float value) {
    return Float.floatToIntBits(value);
  }

  /**
   * Returns a hash code for the given value.
   * 
   * @param value
   *          the value
   * @return a hash code for the given value
   */
  public static int hashCode(final double value) {
    final long l = Double.doubleToLongBits(value);
    return hashCode(l);
  }

}
