package eu.cloudwave.wp5.feedback.eclipse.base.ui.properties;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import eu.cloudwave.wp5.feedback.eclipse.base.ui.factories.ControlFactory;
import eu.cloudwave.wp5.feedback.eclipse.base.ui.factories.ControlFactoryImpl;

/**
 * Implementation of {@link PropertyPageField} for string type properties.
 */
public class PropertyPageStringField extends AbstractPropertyPageField implements PropertyPageField {

  private static final String EMPTY = ""; //$NON-NLS-1$

  /**
   * The width of a property text field.
   */
  private static final int TEXT_WIDTH = 200;

  private Text textField;
  private String key;
  private String labelText;

  private ControlFactory controlFactory;

  public PropertyPageStringField(final String key, final String labelText) {
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
    createTextField(parent);
  }

  private void createLabel(final Composite parent) {
    final GridData labelLayoutData = new GridData();
    labelLayoutData.grabExcessHorizontalSpace = true;
    labelLayoutData.horizontalAlignment = SWT.FILL;
    labelLayoutData.widthHint = LABELS_MIN_WIDTH;
    final Label label = controlFactory.createLabel(parent, labelText);
    label.setLayoutData(labelLayoutData);
  }

  public void createTextField(final Composite parent) {
    textField = new Text(parent, SWT.BORDER);
    final GridData textFieldLayoutData = new GridData();
    textFieldLayoutData.widthHint = TEXT_WIDTH;
    textField.setLayoutData(textFieldLayoutData);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void loadValue(final IEclipsePreferences propertyStore) {
    textField.setText(propertyStore.get(key, EMPTY));

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void storeValue(final IEclipsePreferences propertyStore) {
    propertyStore.put(key, textField.getText());
  }

}
