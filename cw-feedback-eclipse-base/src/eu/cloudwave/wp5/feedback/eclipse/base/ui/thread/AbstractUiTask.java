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
package eu.cloudwave.wp5.feedback.eclipse.base.ui.thread;

import org.eclipse.swt.widgets.Display;

/**
 * Base class for tasks that are executed asynchronously in the UI thread at the next reasonable opportunity.
 */
public abstract class AbstractUiTask {

  /**
   * The work that has to be done.
   */
  protected abstract void doWork();

  /**
   * Runs the current task in the UI thread.
   */
  public final void run() {
    Display.getDefault().asyncExec(new Runnable() {
      @Override
      public void run() {
        doWork();
      }
    });
  }

}
