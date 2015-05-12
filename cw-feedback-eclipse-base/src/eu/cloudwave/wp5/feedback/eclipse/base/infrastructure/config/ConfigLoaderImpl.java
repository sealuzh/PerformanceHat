package eu.cloudwave.wp5.feedback.eclipse.base.infrastructure.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.google.common.base.Optional;

/**
 * Implementation of {@link ConfigLoader}.
 */
public class ConfigLoaderImpl implements ConfigLoader {

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<String> get(final String path, final String key) {
    final Properties properties = new Properties();
    final InputStream fileInputStream = getClass().getClassLoader().getResourceAsStream(path);
    try {
      properties.load(fileInputStream);
    }
    catch (final IOException e) {
      return Optional.absent();
    }
    return Optional.fromNullable(properties.getProperty(key));
  }

}
