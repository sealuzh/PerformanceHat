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
package eu.cloudwave.wp5.common.util;

/**
 * This class provides utility methods to deal with time values.
 */
public class TimeValues {

  private static final int FACTOR = 1000;
  private static final String SECONDS = "s";
  private static final String MILLISECONDS = "ms";

  public static String toText(final double milliseconds) {
    return toText(milliseconds, -1);
  }

  public static String toText(final double milliseconds, final int decimalPlaces) {
    final boolean isSeconds = milliseconds >= FACTOR;
    double value = isSeconds ? milliseconds / FACTOR : milliseconds;
    final String unit = isSeconds ? SECONDS : MILLISECONDS;
    if (decimalPlaces > 0) {
      value = Numbers.round(value, decimalPlaces);
    }
    return value + unit;
  }

  private TimeValues() {}

}
