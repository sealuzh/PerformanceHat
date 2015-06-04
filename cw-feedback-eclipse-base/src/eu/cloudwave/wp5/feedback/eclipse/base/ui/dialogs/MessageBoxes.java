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

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

/**
 * Provides methods to create and show {@link MessageBox}'s.
 */
public class MessageBoxes {

  private MessageBoxes() {} // prevents instantiation

  /**
   * Creates a {@link MessageBox}.
   * 
   * @param parent
   *          the parent {@link Shell} of the {@link MessageBox}
   * @param title
   *          the title
   * @param message
   *          the message
   * @return the created {@link MessageBox}.
   */
  public static MessageBox create(final Shell parent, final String title, final String message) {
    final MessageBox messageBox = new MessageBox(parent, SWT.ICON_ERROR | SWT.OK);
    messageBox.setText(title);
    messageBox.setMessage(message);
    return messageBox;
  }

  /**
   * Creates and displays a {@link MessageBox}.
   * 
   * @param parent
   *          the parent {@link Shell} of the {@link MessageBox}
   * @param title
   *          the title
   * @param message
   *          the message
   */
  public static void createAndShow(final Shell parent, final String title, final String message) {
    create(parent, title, message).open();
  }

}
