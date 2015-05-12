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
