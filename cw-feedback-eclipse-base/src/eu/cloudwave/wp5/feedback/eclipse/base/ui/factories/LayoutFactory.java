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
package eu.cloudwave.wp5.feedback.eclipse.base.ui.factories;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;

/**
 * Factory for SWT layouts.
 */
public interface LayoutFactory {

  /**
   * Creates a {@link GridLayout}.
   * 
   * @param margin
   *          the value for the margin property
   * @param spacing
   *          the value for the spacing property
   * @return the created {@link GridLayout}
   */
  public GridLayout createGridLayout(int margin, int spacing);

  /**
   * Creates a {@link GridLayout}.
   * 
   * @param marginAndSpacing
   *          the value for the margin- and spacing-properties
   * @return the created {@link GridLayout}
   */
  public GridLayout createGridLayout(int marginAndSpacing);

  /**
   * Creates a {@link RowLayout}.
   * 
   * Possible values are:
   * <ul>
   * <li>HORIZONTAL: Position the controls horizontally from left to right</li>
   * <li>VERTICAL: Position the controls vertically from top to bottom</li>
   * </ul>
   * 
   * @param type
   *          specifies whether the layout places controls in rows or columns (possible values are SWT.HORIZONTAL and
   *          SWT.VERTICAL)
   * @param margin
   *          the value for the margin property
   * @param spacing
   *          the value for the spacing property
   * @return the created {@link RowLayout}
   */
  public RowLayout createRowLayout(int type, int margin, int spacing);

  /**
   * Creates a {@link RowLayout}.
   * 
   * @param type
   *          specifies whether the layout places controls in rows or columns (possible values are SWT.HORIZONTAL and
   *          SWT.VERTICAL)
   * @param marginAndSpacing
   *          the value for the margin- and spacing-properties
   * @return the created {@link RowLayout}
   */
  public RowLayout createRowLayout(int type, int marginAndSpacing);

}
