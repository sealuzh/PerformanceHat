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
package eu.cloudwave.wp5.feedback.eclipse.base.ui.factories;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;

import com.google.common.base.Optional;

/**
 * Implementation of {@link ControlFactory}.
 */
public class ControlFactoryImpl implements ControlFactory {

  private static final String BROWSER_FALLBACK_LABEL_MESSAGE = "<the browser control could not be initialized>";

  /**
   * {@inheritDoc}
   */
  @Override
  public Composite createComposite(final Composite parent, final Layout layout) {
    final Composite composite = new Composite(parent, SWT.NONE);
    composite.setLayout(layout);
    return composite;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Composite createComposite(final Composite parent, final Layout layout, final Object layoutData) {
    final Composite composite = createComposite(parent, layout);
    composite.setLayoutData(layoutData);
    return composite;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Label createLabel(final Composite parent, final String text) {
    final Label label = new Label(parent, SWT.NONE);
    label.setText(text);
    return label;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Button createButton(final Composite parent, final String text, final Image icon, final SelectionListener action) {
    final Button button = createButton(parent, action);
    button.setText(text);
    button.setImage(icon);
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Button createButton(final Composite parent, final String text, final SelectionListener action) {
    final Button button = createButton(parent, action);
    button.setText(text);
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Button createButton(final Composite parent, final Image icon, final SelectionListener action) {
    final Button button = createButton(parent, action);
    button.setImage(icon);
    return null;
  }

  private Button createButton(final Composite parent, final SelectionListener action) {
    final Button button = new Button(parent, SWT.PUSH);
    button.addSelectionListener(action);
    return button;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Browser createBrowser(final Composite parent, final Object layoutData) throws SWTError {
    final Browser browser = new Browser(parent, SWT.NONE);
    browser.setLayoutData(layoutData);
    return browser;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<Browser> createBrowserOrFallback(final Composite parent, final Object layoutData) {
    try {
      final Browser browser = createBrowser(parent, layoutData);
      return Optional.of(browser);
    }
    catch (final SWTError e) {
      final Label fallbackLabel = new Label(parent, SWT.NONE);
      fallbackLabel.setLayoutData(layoutData);
      fallbackLabel.setAlignment(SWT.CENTER);
      fallbackLabel.setText(BROWSER_FALLBACK_LABEL_MESSAGE);
      return Optional.absent();
    }
  }

}
