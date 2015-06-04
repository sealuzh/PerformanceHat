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

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import eu.cloudwave.wp5.feedback.eclipse.base.ui.factories.ControlFactory;
import eu.cloudwave.wp5.feedback.eclipse.base.ui.factories.ControlFactoryImpl;

/**
 * Implementation of {@link PropertyPageField} for date type properties.
 */
public class PropertyPageToggleField extends AbstractPropertyPageField implements PropertyPageField {

  private static final String EMPTY = ""; //$NON-NLS-1$

  private Button toggleButton;
  private String key;
  private String labelText;

  private ControlFactory controlFactory;

  public PropertyPageToggleField(final String key, final String labelText) {
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
    createToggle(parent);
  }

  private void createLabel(final Composite parent) {
    final GridData labelLayoutData = new GridData();
    labelLayoutData.grabExcessHorizontalSpace = true;
    labelLayoutData.horizontalAlignment = SWT.FILL;
    labelLayoutData.widthHint = LABELS_MIN_WIDTH;
    final Label label = controlFactory.createLabel(parent, labelText);
    label.setLayoutData(labelLayoutData);
  }

  public void createToggle(final Composite parent) {
    toggleButton = new Button(parent, SWT.CHECK);
    toggleButton.setLayoutData(new GridData());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void loadValue(final IEclipsePreferences propertyStore) {
    String isActivated = propertyStore.get(key, EMPTY);
    if (isActivated != null && !isActivated.isEmpty()) {
      toggleButton.setSelection(Boolean.valueOf(isActivated));
    }
  }

  /**
   * Stores the current value of the date field in milliseconds
   */
  @Override
  public void storeValue(final IEclipsePreferences propertyStore) {
    propertyStore.put(key, String.valueOf(toggleButton.getSelection()));
  }

}
