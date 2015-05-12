package eu.cloudwave.wp5.feedbackhandler.tests.base;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import com.google.common.io.CharStreams;

/**
 * Basic functionality for test classes.
 */
public abstract class AbstractTest {

  private static final String UTF_8 = "UTF-8";

  protected String getResourceAsString(final String path) throws UnsupportedEncodingException, IOException {
    final InputStream is = getClass().getResourceAsStream(path);
    return CharStreams.toString(new InputStreamReader(is, UTF_8));
  }

}
