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
package eu.cloudwave.wp5.feedback.eclipse.base.ui.properties;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.swt.widgets.Composite;

/**
 * A field on a project property page.
 */
public interface PropertyPageField {

  /**
   * Loads the value of the respective property and fills the field
   * 
   * @param propertyStore
   *          the property store of the current project
   */
  public void loadValue(IEclipsePreferences propertyStore);

  /**
   * Stores the current value of the field to the respective property.
   * 
   * @param propertyStore
   *          the property store of the current project
   */
  public void storeValue(IEclipsePreferences propertyStore);

  /**
   * Adds the field to the given parent composite.
   * 
   * @param parent
   *          the parent composite of the field
   */
  public void display(final Composite parent);
}
