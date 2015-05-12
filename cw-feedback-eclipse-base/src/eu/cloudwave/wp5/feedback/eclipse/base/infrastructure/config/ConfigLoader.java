package eu.cloudwave.wp5.feedback.eclipse.base.infrastructure.config;

import com.google.common.base.Optional;

/**
 * Provides methods to access properties (key/value pairs) from a property file.
 */
public interface ConfigLoader {

  /**
   * Loads the value for the given key from the property file.
   * 
   * @param path
   *          the path to the property file
   * @param key
   *          key of the property
   * @return value of the property or {@link Optional#absent()} if the property could not be loaded
   */
  public Optional<String> get(String path, final String key);

}
