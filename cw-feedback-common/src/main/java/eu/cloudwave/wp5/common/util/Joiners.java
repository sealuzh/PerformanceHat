package eu.cloudwave.wp5.common.util;

import com.google.common.base.Joiner;

public class Joiners {

  private static final String COMMA = ",";

  public static String onComma(final String[] strings) {
    return Joiner.on(COMMA).join(strings);
  }

  public static String onComma(final Object[] objects) {
    return Joiner.on(COMMA).join(objects);
  }

  private Joiners() {}

}
