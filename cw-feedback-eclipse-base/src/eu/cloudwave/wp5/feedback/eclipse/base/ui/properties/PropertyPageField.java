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
