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

/**
 * {@link FeedbackInformationControlContentProvider}'s are responsible to create the content for the hovers in the Java
 * editor. Each implementation of this interface creates the content for one of the different marker types.
 */
public interface FeedbackInformationControlContentProvider {

  /**
   * Fills the content of the feedback hover.
   * 
   * @param the
   *          parent {@link Composite}
   * @param mainControlOptional
   *          the main control
   * @param bottomControl
   *          the the bottom control
   * @param marker
   *          {@link IMarker} the hover is attached to
   */
  public void fillContent(Composite parent, Optional<Browser> mainControlOptional, Composite bottomControl, final IMarker marker);

}
