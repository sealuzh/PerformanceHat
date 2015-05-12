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

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.dialogs.PropertyPage;
import org.osgi.service.prefs.BackingStoreException;

import com.google.common.collect.Lists;

import eu.cloudwave.wp5.feedback.eclipse.base.core.BaseIds;
import eu.cloudwave.wp5.feedback.eclipse.base.ui.factories.ControlFactory;
import eu.cloudwave.wp5.feedback.eclipse.base.ui.factories.ControlFactoryImpl;
import eu.cloudwave.wp5.feedback.eclipse.base.ui.factories.LayoutFactory;
import eu.cloudwave.wp5.feedback.eclipse.base.ui.factories.LayoutFactoryImpl;

/**
 * Basic functionality/layout for property pages. While preferences/preferences pages are used on workspace level,
 * properties and property pages are used on project level.
 */
public abstract class AbstractFeedbackPropertyPage extends PropertyPage {

  private static final int MAIN_SPACING = 20;
  private static final int GROUP_MARGIN = 5;
  private static final int GROUP_SPACING = 10;
  private static final int NUM_OF_COLUMNS = 2;

  private LayoutFactory layoutFactory;
  private ControlFactory controlFactory;

  private List<PropertyPageField> fields;

  private Composite main;

  public AbstractFeedbackPropertyPage() {
    this.layoutFactory = new LayoutFactoryImpl();
    this.controlFactory = new ControlFactoryImpl();
    this.fields = Lists.newArrayList();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected final Control createContents(final Composite parent) {
    getShell().setBackgroundMode(SWT.INHERIT_FORCE); // background color is inherited from parent control

    // create controls
    createMain(parent);
    createDescription();

    // add fields
    addGroups();

    // load values
    loadValues();

    return this.main;
  }

  private final void createMain(final Composite parent) {
    this.main = new Composite(parent, SWT.NULL);
    final RowLayout mainLayout = new RowLayout();
    mainLayout.spacing = MAIN_SPACING;
    mainLayout.type = SWT.VERTICAL;
    this.main.setLayout(mainLayout);
  }

  private final void createDescription() {
    controlFactory.createLabel(this.main, description());
  }

  /**
   * The description that is displayed on top of the property page.
   * 
   * @return the description of the property page
   */
  protected abstract String description();

  /**
   * Adds the required fields to the property page.
   */
  protected abstract void addGroups();

  protected final void addGroup(final String title, final PropertyPageField... fields) {
    final Group group = new Group(main, SWT.SHADOW_ETCHED_IN);
    group.setText(title);
    final GridLayout groupLayout = layoutFactory.createGridLayout(GROUP_MARGIN, GROUP_SPACING);
    groupLayout.numColumns = NUM_OF_COLUMNS;
    group.setLayout(groupLayout);

    for (final PropertyPageField field : fields) {
      this.fields.add(field);
      field.display(group);
    }
  }

  private final void loadValues() {
    final IEclipsePreferences projectPreferences = getProjectPreferences();
    for (final PropertyPageField field : fields) {
      field.loadValue(projectPreferences);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean performOk() {
    final IEclipsePreferences projectPreferences = getProjectPreferences();
    for (final PropertyPageField field : fields) {
      field.storeValue(projectPreferences);
    }
    try {
      projectPreferences.flush();
    }
    catch (final BackingStoreException e) {
      // settings could not be saved.
      e.printStackTrace();
    }
    return super.performOk();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected final void performApply() {
    super.performApply();
  }

  private final IEclipsePreferences getProjectPreferences() {
    final IAdaptable element = getElement();
    IProject project = null;
    if (element instanceof IJavaProject) {
      project = ((IJavaProject) element).getProject();
    }
    else if (element instanceof IProject) {
      project = (IProject) element;
    }

    if (project != null) {
      final IScopeContext context = new ProjectScope(project);
      return context.getNode(BaseIds.ID);
    }
    return null;
  }
}
