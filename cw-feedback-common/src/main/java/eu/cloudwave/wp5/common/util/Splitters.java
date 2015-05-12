package eu.cloudwave.wp5.common.util;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

public class Splitters {

  private static final String COMMA = ",";

  public static Iterable<String> onComma(final String text) {
    return Splitter.on(COMMA).trimResults().omitEmptyStrings().split(text);
  }

  public static String[] arrayOnComma(final String text) {
    return Iterables.toArray(onComma(text), String.class);
  }

  private Splitters() {}

}
