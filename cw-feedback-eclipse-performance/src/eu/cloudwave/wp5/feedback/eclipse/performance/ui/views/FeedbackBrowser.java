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
package eu.cloudwave.wp5.feedback.eclipse.performance.ui.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

/**
 * A browser that can be opened inside eclipse to display information from the feedback plugin.
 */
public class FeedbackBrowser extends ViewPart {

  private static final String SPACE = " ";
  private static final String ACCESS_TOKEN = "Access-Token: ";
  private static final String APPLICATION_ID = "Application-ID: ";

  private Browser browser;

  /**
   * {@inheritDoc}
   */
  @Override
  public void createPartControl(final Composite parent) {
    browser = new Browser(parent, SWT.NONE);
    browser.setJavascriptEnabled(true);
  }

  public void setUrl(final String url, final String postData, final String accessToken, final String applicationId) {
    final String[] headers = new String[] { ACCESS_TOKEN + SPACE + accessToken, APPLICATION_ID + applicationId };
    setUrl(url, postData, headers);
  }

  public void setUrl(final String url, final String postData, final String[] headers) {
    browser.setUrl(url, postData, headers);
  }

  public void setUrl(final String url) {
    browser.setUrl(url);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setFocus() {
    browser.setFocus();
  }
}
