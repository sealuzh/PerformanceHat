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
