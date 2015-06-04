/*******************************************************************************
 * Copyright 2015 Software Evolution and Architecture Lab, University of Zurich
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 ******************************************************************************/
package eu.cloudwave.wp5.feedback.eclipse.base.ui.properties;

import java.util.Calendar;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;

public class PropertyPageDateEndOfDayField extends PropertyPageDateField {

  public PropertyPageDateEndOfDayField(String key, String labelText) {
    super(key, labelText);
  }

  /**
   * Stores the current value of the date field in milliseconds. Compared to its parent we do not use the time 00:00 but
   * 23:59:59
   */
  @Override
  public void storeValue(final IEclipsePreferences propertyStore) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(0); // set to zero epoch
    calendar.set(Calendar.YEAR, dateField.getYear());
    calendar.set(Calendar.MONTH, dateField.getMonth());
    calendar.set(Calendar.DAY_OF_MONTH, dateField.getDay());
    calendar.set(Calendar.AM_PM, Calendar.PM);
    calendar.set(Calendar.HOUR, 11);
    calendar.set(Calendar.MINUTE, 59);
    calendar.set(Calendar.SECOND, 59);
    calendar.set(Calendar.MILLISECOND, 999);
    propertyStore.put(key, String.valueOf(calendar.getTimeInMillis()));
  }
}
