package eu.cloudwave.wp5.common.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Utility classes for dates and times.
 */
public class DateTimes {

  private static final String DFLT_DATE_PATTERN = "dd.MM.yyyy";
  private static final String DFLT_TIME_PATTERN = "HH:mm:ss";
  private static final String DFLT_DATE_TIME_PATTERN = DFLT_DATE_PATTERN + " " + DFLT_TIME_PATTERN;

  /**
   * Creates a {@link DateTime} for the given milliseconds.
   * 
   * @param millis
   *          milliseconds
   * @return the created {@link DateTime}
   */
  public static DateTime fromMilliSeconds(final long millis) {
    return new DateTime(millis);
  }

  /**
   * Returns the given {@link DateTime} formatted according to the pattern {@link #DFLT_DATE_TIME_PATTERN}.
   * 
   * @param dateTime
   *          the {@link DateTime}
   * @return the formatted {@link DateTime} as {@link String}
   */
  public static String format(final DateTime dateTime) {
    return format(dateTime, DFLT_DATE_TIME_PATTERN);
  }

  /**
   * Returns the given {@link DateTime} formatted according to the given pattern.
   * 
   * @param dateTime
   *          the {@link DateTime}
   * @param pattern
   *          the formatting pattern
   * @return the formatted {@link DateTime} as {@link String}
   */
  public static String format(final DateTime dateTime, final String pattern) {
    final DateTimeFormatter formatter = DateTimeFormat.forPattern(pattern);
    return dateTime.toString(formatter);
  }

  private DateTimes() {}

}
