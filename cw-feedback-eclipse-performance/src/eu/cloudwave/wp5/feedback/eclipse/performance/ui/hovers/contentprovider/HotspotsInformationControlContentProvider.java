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
package eu.cloudwave.wp5.feedback.eclipse.performance.ui.hovers.contentprovider;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import eu.cloudwave.wp5.common.constants.Params;
import eu.cloudwave.wp5.common.constants.Urls;
import eu.cloudwave.wp5.feedback.eclipse.base.core.preferences.FeedbackPreferences;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaResourceFactory;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.MarkerAttributes;
import eu.cloudwave.wp5.feedback.eclipse.base.ui.hovers.contentprovider.AbstractFeedbackInformationControlContentProvider;
import eu.cloudwave.wp5.feedback.eclipse.base.ui.hovers.contentprovider.FeedbackInformationControlContentProvider;
import eu.cloudwave.wp5.feedback.eclipse.performance.Ids;
import eu.cloudwave.wp5.feedback.eclipse.performance.PerformancePluginActivator;
import eu.cloudwave.wp5.feedback.eclipse.performance.infrastructure.messages.Messages;
import eu.cloudwave.wp5.feedback.eclipse.performance.ui.views.FeedbackBrowser;

/**
 * Implementation of {@link FeedbackInformationControlContentProvider} for the hotspot marker.
 */
public class HotspotsInformationControlContentProvider extends AbstractFeedbackInformationControlContentProvider implements FeedbackInformationControlContentProvider {

  private static final String Q_MARK = "?"; //$NON-NLS-1$
  private static final String EQ = "="; //$NON-NLS-1$
  private static final String AND = "&"; //$NON-NLS-1$

  private static final String ICON_CHART = "icons/chart.png"; //$NON-NLS-1$
  private static final String ICON_CLOUDWAVE = "icons/cloudwave.png"; //$NON-NLS-1$

  @Override
  protected void fillIndividualContent() {
    controlFactory.createButton(getBottomControl(), Messages.BUTTON_CW_DASHBOARD, PerformancePluginActivator.getImageDescriptor(ICON_CLOUDWAVE).createImage(), new SelectionAdapter() {
      @Override
      public void widgetSelected(final SelectionEvent e) {
        try {
          final String dashboardUrl = FeedbackPreferences.getString(FeedbackPreferences.CLOUDWAVE_DASHBOARD__URL);
          PlatformUI.getWorkbench().getBrowserSupport().getExternalBrowser().openURL(new URL(dashboardUrl));
          closeHover();
        }
        catch (final PartInitException | MalformedURLException e1) {
          e1.printStackTrace();
        }
      }
    });
    controlFactory.createButton(getBottomControl(), Messages.BUTTON_FURTHER_INFO, PerformancePluginActivator.getImageDescriptor(ICON_CHART).createImage(), new SelectionAdapter() {
      @Override
      public void widgetSelected(final SelectionEvent e) {
        try {
          final FeedbackBrowser view = (FeedbackBrowser) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(Ids.VIEWS__FEEDBACK_BROWSER);
          view.setUrl(createUrl(), null, getProject().getAccessToken(), getProject().getApplicationId());
          closeHover();
        }
        catch (final PartInitException e1) {
          e1.printStackTrace();
        }
      }
    });
  }

  private String createUrl() {
    try {
      final String className = (String) getMarker().getAttribute(MarkerAttributes.CLASS_NAME);
      final String procedureName = (String) getMarker().getAttribute(MarkerAttributes.PROCEDURE_NAME);
      final String arguments = (String) getMarker().getAttribute(MarkerAttributes.ARGUMENTS);
      return createUrl(className, procedureName, arguments);
    }
    catch (final CoreException e1) {
      e1.printStackTrace();
    }
    return null;
  }

  private String createUrl(final String className, final String procedureName, final String arguments) {
    final String rootUrl = FeedbackPreferences.getString(FeedbackPreferences.FEEDBACK_HANDLER__URL);
    final String url = Urls.concatenate(rootUrl, Urls.PLOTS_PROCEDURE);
    return url + Q_MARK + Params.CLASS_NAME + EQ + className + AND + Params.PROCEDURE_NAME + EQ + procedureName + AND + Params.ARGUMENTS + EQ + arguments;
  }

  @Override
  protected FeedbackJavaResourceFactory getFeedbackJavaResourceFactory() {
    return PerformancePluginActivator.instance(FeedbackJavaResourceFactory.class);
  }
}
