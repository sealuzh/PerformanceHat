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
package eu.cloudwave.wp5.feedback.eclipse.base.ui.hovers.contentprovider;

import org.eclipse.core.resources.IMarker;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Composite;

import com.google.common.base.Optional;

import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaProject;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaResourceFactory;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.MarkerAttributes;
import eu.cloudwave.wp5.feedback.eclipse.base.ui.dialogs.MessageBoxes;
import eu.cloudwave.wp5.feedback.eclipse.base.ui.factories.ColorFactory;
import eu.cloudwave.wp5.feedback.eclipse.base.ui.factories.ColorFactoryImpl;
import eu.cloudwave.wp5.feedback.eclipse.base.ui.factories.ControlFactory;
import eu.cloudwave.wp5.feedback.eclipse.base.ui.factories.ControlFactoryImpl;
import eu.cloudwave.wp5.feedback.eclipse.base.ui.factories.LayoutFactory;
import eu.cloudwave.wp5.feedback.eclipse.base.ui.factories.LayoutFactoryImpl;

public abstract class AbstractFeedbackInformationControlContentProvider implements FeedbackInformationControlContentProvider {

  private static final String MARKER_DESCRIPTION_NOT_AVAILABLE = "<The description of the marker is not available>"; //$NON-NLS-1$

  private Composite parentControl;
  private Optional<Browser> mainControlOptional;
  private Composite bottomControl;

  private IMarker marker;
  private FeedbackJavaProject project;

  protected final LayoutFactory layoutFactory;
  protected final ColorFactory colorFactory;
  protected final ControlFactory controlFactory;

  public AbstractFeedbackInformationControlContentProvider() {
    this.layoutFactory = new LayoutFactoryImpl();
    this.colorFactory = new ColorFactoryImpl();
    this.controlFactory = new ControlFactoryImpl();
  }

  abstract protected FeedbackJavaResourceFactory getFeedbackJavaResourceFactory();

  /**
   * Fills the common content and calls the template method {@link #fillIndividualContent()} to let the subclasses fill
   * the individual content. {@inheritDoc}
   */
  @Override
  public final void fillContent(final Composite parent, final Optional<Browser> mainControlOptional, final Composite bottomControl, final IMarker marker) {
    this.parentControl = parent;
    this.mainControlOptional = mainControlOptional;
    this.bottomControl = bottomControl;
    this.marker = marker;
    this.project = getFeedbackJavaResourceFactory().create(marker.getResource().getProject()).get();
    fillCommonContent();
    fillIndividualContent();
  }

  private void fillCommonContent() {
    final String description = getMarker().getAttribute(MarkerAttributes.DESCRIPTION, MARKER_DESCRIPTION_NOT_AVAILABLE);
    if (getMainControlOptional().isPresent()) {
      getMainControlOptional().get().setText(description);
    }
  }

  /**
   * Template method that has to be overridden by subclasses in order to fill the content individually.
   */
  protected abstract void fillIndividualContent();

  public Optional<Browser> getMainControlOptional() {
    return mainControlOptional;
  }

  protected Composite getBottomControl() {
    return bottomControl;
  }

  protected IMarker getMarker() {
    return marker;
  }

  public final FeedbackJavaProject getProject() {
    return project;
  }

  public final void closeHover() {
    parentControl.getShell().close();
  }

  protected void showErrorMessageBox(final String title, final String message) {
    MessageBoxes.createAndShow(parentControl.getShell(), title, message);
  }

}
