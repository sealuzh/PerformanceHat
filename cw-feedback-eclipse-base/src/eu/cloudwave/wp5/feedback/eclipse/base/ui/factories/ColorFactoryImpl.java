package eu.cloudwave.wp5.feedback.eclipse.base.ui.factories;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

/**
 * Implementation of {@link ColorFactory}.
 */
public class ColorFactoryImpl implements ColorFactory {

  /**
   * {@inheritDoc}
   */
  @Override
  public Color create(final int r, final int g, final int b) {
    return new Color(Display.getCurrent(), r, g, b);
  }

}
