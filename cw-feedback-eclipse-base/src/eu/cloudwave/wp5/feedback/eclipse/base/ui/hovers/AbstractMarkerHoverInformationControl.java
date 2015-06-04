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
package eu.cloudwave.wp5.feedback.eclipse.base.ui.hovers;

import org.eclipse.core.resources.IMarker;
import org.eclipse.jface.text.AbstractInformationControl;
import org.eclipse.jface.text.IInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.IInformationControlExtension2;
import org.eclipse.swt.widgets.Shell;

/**
 * This class provides basic functionality for {@link IInformationControl}'s that are used to display information for
 * marker hovers.
 */
public abstract class AbstractMarkerHoverInformationControl extends AbstractInformationControl implements IInformationControlExtension2 {

  public AbstractMarkerHoverInformationControl(final Shell parentShell, final boolean resizable) {
    super(parentShell, resizable);
    create();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasContents() {
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void setInput(final Object input) {
    fillContent((IMarker) input);
  }

  /**
   * Fills the content by using information from the respective {@link IMarker}. Is triggered by
   * {@link #setInput(Object)}.
   * 
   * @param marker
   *          the {@link IMarker}
   */
  protected abstract void fillContent(final IMarker marker);

  /**
   * {@inheritDoc}
   */
  @Override
  public final IInformationControlCreator getInformationPresenterControlCreator() {
    return new IInformationControlCreator() {
      @Override
      public IInformationControl createInformationControl(final Shell parent) {
        return AbstractMarkerHoverInformationControl.this.createInformationControl(parent, true);
      }
    };
  }

  /**
   * Creates the {@link IInformationControl} that is shown when the hover is focused with the given {@link Shell} as
   * parent.
   * 
   * @param parent
   *          the parent {@link Shell}
   * @param indicates
   *          whether the control should be resizable
   * @return the created {@link IInformationControl}
   */
  protected abstract IInformationControl createInformationControl(final Shell parent, final boolean resizable);
}
