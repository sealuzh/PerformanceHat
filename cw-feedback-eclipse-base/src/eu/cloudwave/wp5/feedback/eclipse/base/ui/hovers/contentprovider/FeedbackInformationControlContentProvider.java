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
