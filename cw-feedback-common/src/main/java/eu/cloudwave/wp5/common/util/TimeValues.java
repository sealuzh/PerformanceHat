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
