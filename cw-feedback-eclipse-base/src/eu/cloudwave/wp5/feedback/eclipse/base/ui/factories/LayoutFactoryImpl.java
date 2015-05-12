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
