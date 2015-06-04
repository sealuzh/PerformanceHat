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
package eu.cloudwave.wp5.feedback.eclipse.base.ui.factories;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;

/**
 * Implementation of {@link LayoutFactory}.
 */
public class LayoutFactoryImpl implements LayoutFactory {

  /**
   * {@inheritDoc}
   */
  @Override
  public GridLayout createGridLayout(final int margin, final int spacing) {
    final GridLayout gridLayout = new GridLayout();
    gridLayout.marginHeight = margin;
    gridLayout.marginWidth = margin;
    gridLayout.horizontalSpacing = margin;
    gridLayout.verticalSpacing = spacing;
    gridLayout.horizontalSpacing = spacing;
    return gridLayout;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public GridLayout createGridLayout(final int marginAndSpacing) {
    return createGridLayout(marginAndSpacing, marginAndSpacing);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public RowLayout createRowLayout(final int type, final int margin, final int spacing) {
    final RowLayout rowLayout = new RowLayout();
    rowLayout.type = type;
    rowLayout.marginTop = margin;
    rowLayout.marginBottom = margin;
    rowLayout.marginLeft = margin;
    rowLayout.marginRight = margin;
    rowLayout.spacing = spacing;
    return rowLayout;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public RowLayout createRowLayout(final int type, final int marginAndSpacing) {
    return createRowLayout(type, marginAndSpacing, marginAndSpacing);
  }

}
