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
package eu.cloudwave.wp5.feedback.eclipse.costs.ui.hovers;

import org.eclipse.jface.text.IInformationControl;
import org.eclipse.swt.widgets.Shell;

import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.FeedbackResourceFactory;
import eu.cloudwave.wp5.feedback.eclipse.base.ui.hovers.AbstractJavaEditorMarkerHover;
import eu.cloudwave.wp5.feedback.eclipse.base.ui.hovers.FeedbackInformationControl;
import eu.cloudwave.wp5.feedback.eclipse.costs.core.CostIds;
import eu.cloudwave.wp5.feedback.eclipse.costs.core.CostPluginActivator;

/**
 * This class connects the the feedback marker with the corresponding hover.It is therefore registered in the
 * plugin.xml.
 * 
 * The actual content is created in the {@link FeedbackInformationControl} returned by the method
 * {@link #createInformationControl(Shell, boolean)}.
 * 
 */
public class CostMarkerHover extends AbstractJavaEditorMarkerHover {

  /**
   * {@inheritDoc}
   */
  @Override
  protected String getMarkerId() {
    return CostIds.COST_MARKER;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected final IInformationControl createInformationControl(final Shell parent, final boolean resizable) {
    return new FeedbackInformationControl(parent, resizable);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected FeedbackResourceFactory getFeedbackResourceFactory() {
    return CostPluginActivator.instance(FeedbackResourceFactory.class);
  }
}
