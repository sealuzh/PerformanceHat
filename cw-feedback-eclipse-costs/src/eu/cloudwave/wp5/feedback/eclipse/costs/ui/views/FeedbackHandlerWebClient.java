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
package eu.cloudwave.wp5.feedback.eclipse.costs.ui.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import eu.cloudwave.wp5.feedback.eclipse.base.core.preferences.FeedbackPreferences;

public class FeedbackHandlerWebClient extends ViewPart {

  private String feedbackHandlerUrl;

  private Browser browser;

  @Override
  public void createPartControl(Composite parent) {
    feedbackHandlerUrl = FeedbackPreferences.getString(FeedbackPreferences.FEEDBACK_HANDLER__URL);

    browser = new Browser(parent, SWT.NONE);
    browser.setJavascriptEnabled(true);
    browser.setUrl(feedbackHandlerUrl);
  }

  public void setApplication(String applicationId, String accessToken) {
    Browser.setCookie("applicationId=" + applicationId, feedbackHandlerUrl);
    Browser.setCookie("accessToken=" + accessToken, feedbackHandlerUrl);
    browser.refresh();
  }

  @Override
  public void setFocus() {}
}
