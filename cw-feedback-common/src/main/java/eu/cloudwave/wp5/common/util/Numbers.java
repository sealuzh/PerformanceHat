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
