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
package eu.cloudwave.wp5.feedback.eclipse.base.core.feedbackhandler;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.PreferencesUtil;

import com.google.common.collect.ImmutableList;

import eu.cloudwave.wp5.common.error.ErrorType;
import eu.cloudwave.wp5.common.error.RequestException;
import eu.cloudwave.wp5.feedback.eclipse.base.core.BaseIds;
import eu.cloudwave.wp5.feedback.eclipse.base.infrastructure.logging.Logger;
import eu.cloudwave.wp5.feedback.eclipse.base.infrastructure.messages.Messages;
import eu.cloudwave.wp5.feedback.eclipse.base.ui.dialogs.AbstractMessageDialog;

public class RequestExceptionHandler {

  public boolean handle(final IProject project, final RequestException exception) {
    return handle(project, exception, ErrorType.values());
  }

  public boolean handle(final IProject project, final RequestException exception, final ErrorType... handledErrorTypes) {
    final ErrorType type = exception.getType();
    if (ImmutableList.copyOf(handledErrorTypes).contains(type)) {
      switch (type) {

        case FEEDBACK_HANDLER_NOT_AVAILABLE:
          new AbstractMessageDialog() {
            @Override
            public void action(final Shell shell) {
              final PreferenceDialog preferencePage = PreferencesUtil.createPreferenceDialogOn(shell, BaseIds.PREFERENCE_PAGE, null, null);
              if (preferencePage != null) {
                preferencePage.open();
              }
            }
          }.display(type.getTitle(), Messages.MESSAGE__FEEDBACK_HANDLER_NOT_AVAILABLE, MessageDialog.ERROR, Messages.OPEN_PREFERENCES);
          return true;

        case INVALID_APPLICATION_ID:
          openPropertyPage(project, BaseIds.PROPERTIES_PAGE__FDD_MAIN, type.getTitle(), Messages.MESSAGE__INVALID_APPLICATION_ID);
          return true;

        case WRONG_ACCESS_TOKEN:
          openPropertyPage(project, BaseIds.PROPERTIES_PAGE__FDD_MAIN, type.getTitle(), Messages.MESSAGE__WRONG_ACCESS_TOKEN);
          return true;

        default:
          break;
      }
    }
    Logger.print(exception.getMessage());
    return false;
  }

  private void openPropertyPage(final IProject project, final String propertyPageId, final String title, final String message) {
    new AbstractMessageDialog() {
      @Override
      public void action(final Shell shell) {
        final PreferenceDialog propertyDialog = PreferencesUtil.createPropertyDialogOn(shell, project, propertyPageId, null, null);
        if (propertyDialog != null) {
          propertyDialog.open();
        }
      }
    }.display(title, message, MessageDialog.ERROR, Messages.OPEN_PROPERTIES);
  }
}
