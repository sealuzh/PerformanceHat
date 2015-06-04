/*******************************************************************************
 * Copyright 2015 Software Evolution and Architecture Lab, University of Zurich
 * 
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

import java.util.Calendar;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;

import eu.cloudwave.wp5.feedback.eclipse.base.ui.factories.ControlFactory;
import eu.cloudwave.wp5.feedback.eclipse.base.ui.factories.ControlFactoryImpl;

/**
 * Implementation of {@link PropertyPageField} for date type properties.
 */
public class PropertyPageDateField extends AbstractPropertyPageField implements PropertyPageField {

  private static final String EMPTY = ""; //$NON-NLS-1$

  protected DateTime dateField;
  protected String key;
  protected String labelText;

  private ControlFactory controlFactory;

  public PropertyPageDateField(final String key, final String labelText) {
    this.controlFactory = new ControlFactoryImpl();
    this.key = key;
    this.labelText = labelText;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void display(final Composite parent) {
    createLabel(parent);
    createDateField(parent);
  }

  private void createLabel(final Composite parent) {
    final GridData labelLayoutData = new GridData();
    labelLayoutData.grabExcessHorizontalSpace = true;
    labelLayoutData.horizontalAlignment = SWT.FILL;
    labelLayoutData.widthHint = LABELS_MIN_WIDTH;
    final Label label = controlFactory.createLabel(parent, labelText);
    label.setLayoutData(labelLayoutData);
  }

  public void createDateField(final Composite parent) {
    final GridData grid = new GridData();
    dateField = new DateTime(parent, SWT.DATE | SWT.DROP_DOWN | SWT.BORDER);
    dateField.setLayoutData(grid);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void loadValue(final IEclipsePreferences propertyStore) {
    String timeInMilliseconds = propertyStore.get(key, EMPTY);

    if (timeInMilliseconds != null && !timeInMilliseconds.isEmpty()) {
      Calendar calendar = Calendar.getInstance();
      calendar.setTimeInMillis(Long.parseLong(timeInMilliseconds));
      dateField.setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }
  }

  /**
   * Stores the current value of the date field in milliseconds
   */
  @Override
  public void storeValue(final IEclipsePreferences propertyStore) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(0); // set to zero epoch
    calendar.set(Calendar.YEAR, dateField.getYear());
    calendar.set(Calendar.MONTH, dateField.getMonth());
    calendar.set(Calendar.DAY_OF_MONTH, dateField.getDay());
    calendar.set(Calendar.AM_PM, Calendar.AM);
    calendar.set(Calendar.HOUR, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    propertyStore.put(key, String.valueOf(calendar.getTimeInMillis()));
  }
}
