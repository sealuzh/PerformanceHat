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
package eu.cloudwave.wp5.feedback.eclipse.base.ui.dialogs;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import eu.cloudwave.wp5.feedback.eclipse.base.ui.thread.AbstractUiTask;

/**
 * An abstract implementation of a {@link MessageDialog} that provides two Buttons, one to cancel (i.e. do nothing) and
 * one for a custom action. The custom action has to be implemented by subclasses.
 */
public abstract class AbstractMessageDialog {

  /**
   * Displays the current {@link MessageDialog}.
   * 
   * @param title
   *          the title of the {@link MessageDialog}
   * @param message
   *          the message of the {@link MessageDialog}
   * @param dialogImageType
   *          one of the following values:
   *          <ul>
   *          <li>MessageDialog.NONE for a dialog with no image</li>
   *          <li>MessageDialog.ERROR for a dialog with an error image</li>
   *          <li>MessageDialog.INFORMATION for a dialog with an information image</li>
   *          <li>MessageDialog.QUESTION for a dialog with a question image</li>
   *          <li>MessageDialog.WARNING for a dialog with a warning image</li>
   *          </ul>
   * @param buttonText
   *          the text of the button
   */
  public final void display(final String title, final String message, final int dialogImageType, final String buttonText) {
    new AbstractUiTask() {
      @Override
      public void doWork() {
        final Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
        final String[] buttons = new String[] { "Cancel", buttonText };
        final MessageDialog messageDialog = new MessageDialog(shell, title, null, message, MessageDialog.ERROR, buttons, 0);
        final int action = messageDialog.open();
        if (action == 1) {
          action(shell);
        }
      }
    }.run();
  }

  /**
   * The action that should be executed.
   */
  protected abstract void action(Shell shell);

}
