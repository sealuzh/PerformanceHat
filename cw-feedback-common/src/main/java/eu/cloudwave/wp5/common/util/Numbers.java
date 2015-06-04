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

import static com.google.common.base.Preconditions.checkArgument;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Utility methods for dealing with numerical values.
 */
public class Numbers {

  /**
   * Rounds the given value according to the given number of decimal places.
   * 
   * @param value
   *          the value to be rounded
   * @param decimalPlaces
   *          the number of decimal places of the rounded value
   * @return the rounded value
   */
  public static double round(final double value, final int decimalPlaces) {
    checkArgument(decimalPlaces >= 0);
    return new BigDecimal(value).setScale(decimalPlaces, RoundingMode.HALF_UP).doubleValue();
  }

  private Numbers() {}

}
