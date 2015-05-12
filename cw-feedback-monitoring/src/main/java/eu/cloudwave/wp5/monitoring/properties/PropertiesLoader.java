package eu.cloudwave.wp5.monitoring.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.google.common.base.Optional;

/**
 * Loads properties from a property-file.
 */
public class PropertiesLoader {

  private static final String CONFIG = "config.properties";

  /**
   * Loads the value for the given key from the file 'config.properties' inside the root folder.
   * 
   * @param key
   *          the key of the property
   * @return the value for the given key from the file 'config.properties' inside the root folder
   */
  public Optional<String> get(final String key) {
    return get(CONFIG, key);
  }

  /**
   * Loads the value for the given key from the given file.
   * 
   * @param path
   *          path of the property-file
   * @param key
   *          key of the property
   * @return the value for the given key from the given file
   */
  public Optional<String> get(final String path, final String key) {
    final Properties properties = new Properties();

    // takes paths that don't start with a /, and always starts at the root of the classpath.
    // @see:
    // http://stackoverflow.com/questions/14739550/difference-between-getclass-getclassloader-getresource-and-getclass-getres
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
