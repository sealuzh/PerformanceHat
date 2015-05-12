package eu.cloudwave.wp5.feedback.eclipse.base.ui.hovers;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.IInformationControl;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import com.google.common.base.Optional;

import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.FeedbackMarkerType;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.FeedbackMarkerTypeSerializer;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.MarkerAttributes;
import eu.cloudwave.wp5.feedback.eclipse.base.ui.factories.ColorFactory;
import eu.cloudwave.wp5.feedback.eclipse.base.ui.factories.ColorFactoryImpl;
import eu.cloudwave.wp5.feedback.eclipse.base.ui.factories.ControlFactory;
import eu.cloudwave.wp5.feedback.eclipse.base.ui.factories.ControlFactoryImpl;
import eu.cloudwave.wp5.feedback.eclipse.base.ui.factories.LayoutFactory;
import eu.cloudwave.wp5.feedback.eclipse.base.ui.factories.LayoutFactoryImpl;
import eu.cloudwave.wp5.feedback.eclipse.base.ui.hovers.contentprovider.FeedbackInformationControlContentProvider;
import eu.cloudwave.wp5.feedback.eclipse.base.ui.hovers.contentprovider.FeedbackInformationControlContentProviderRegistry;

/**
 * This class is responsible to create the content for the hovers in the Java Editor. For each feedback marker this
 * class is loaded to create the respective hover. In the {@link FeedbackInformationControlContentProviderRegistry} it
 * looks up the right {@link FeedbackInformationControlContentProvider} for the current {@link IMarker} (by its type,
 * which is defined as a marker property in the plugin.xml). The creation of the actual content is then delegated to
 * this {@link FeedbackInformationControlContentProvider}.
 */
public class FeedbackInformationControl extends AbstractMarkerHoverInformationControl {

  private Composite parent;
  private Optional<Browser> mainControlOptional;
  private Composite bottomControl;

  public FeedbackInformationControl(final Shell parentShell, final boolean resizable) {
    super(parentShell, resizable);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void createContent(final Composite parent) {
    final ColorFactory colorFactory = new ColorFactoryImpl();
    final ControlFactory controlFactory = new ControlFactoryImpl();
    final LayoutFactory layoutFactory = new LayoutFactoryImpl();
    this.parent = parent;
    parent.getShell().setBackgroundMode(SWT.INHERIT_FORCE);
    parent.setBackground(colorFactory.create(255, 255, 255));
    parent.setLayout(layoutFactory.createGridLayout(0));
    mainControlOptional = controlFactory.createBrowserOrFallback(parent, new GridData(SWT.FILL, SWT.FILL, true, true));
    bottomControl = controlFactory.createComposite(parent, layoutFactory.createRowLayout(SWT.HORIZONTAL, 2), new GridData(SWT.RIGHT, SWT.FILL, true, false));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void fillContent(final IMarker marker) {
    final FeedbackInformationControlContentProviderRegistry registry = FeedbackInformationControlContentProviderRegistry.INSTANCE;
    registry.get(getType(marker)).fillContent(parent, mainControlOptional, bottomControl, marker);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected IInformationControl createInformationControl(final Shell parent, final boolean resizable) {
    return new FeedbackInformationControl(parent, resizable);
  }

  private FeedbackMarkerType getType(final IMarker marker) {
    try {
      return new FeedbackMarkerTypeSerializer().deserialize((String) marker.getAttribute(MarkerAttributes.TYPE));
    }
    catch (final CoreException e2) {
      // no hover is shown
      e2.printStackTrace();
    }
    return null;
  }
}
