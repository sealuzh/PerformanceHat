/*******************************************************************************
 * Copyright 2015 Software Evolution and Architecture Lab, University of Zurich
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
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
