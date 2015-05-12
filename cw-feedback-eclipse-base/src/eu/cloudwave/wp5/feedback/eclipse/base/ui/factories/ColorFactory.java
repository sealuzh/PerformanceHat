package eu.cloudwave.wp5.feedback.eclipse.base.ui.factories;

import org.eclipse.swt.graphics.Color;

/**
 * Factory for SWT colors.
 */
public interface ColorFactory {

  /**
   * Creates a {@link Color}.
   * 
   * @param r
   *          the amount of red in the color
   * @param g
   *          the amount of green in the color
   * @param b
   *          the amount of blue in the color
   * @return the created {@link Color}
   */
  Color create(int r, int g, int b);

}
