package eu.cloudwave.wp5.feedback.eclipse.base.ui.hovers;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.jdt.ui.text.java.hover.IJavaEditorTextHover;
import org.eclipse.jface.text.IInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextHoverExtension;
import org.eclipse.jface.text.ITextHoverExtension2;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;

import com.google.common.base.Optional;

import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.FeedbackFile;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.FeedbackResourceFactory;

/**
 * This class provides basic functionality for hovers that should be connected with annotations of {@link IMarker}'s
 * (i.e. the hover appears when the user moves the cursor onto a annotation of an {@link IMarker}).
 * 
 * In fact there is a default hover for {@link IMarker}'s, but this only contains the one-line message that is also
 * shown in the problems view. Eclipse doesn't provide any functionality to directly connect a hover with an
 * {@link IMarker}. (More info: 'http://www.eclipse.org/forums/index.php/t/100609/' and
 * 'http://stackoverflow.com/questions/25323477/line-break-in-imarker-tooltips-at-the-left-border-of-the-eclipse-java-ed
 * i t o r ' )
 * 
 * To overcome that limitation, this class can be sub-classed to create a Hover that can be used in combination with
 * {@link IMarker}'s. The {@link IMarker}'s are passed as input content to the respective {@link IInformationControl}
 * that is used to fill the hover.
 */
public abstract class AbstractJavaEditorMarkerHover implements IJavaEditorTextHover, ITextHoverExtension, ITextHoverExtension2 {

  private IEditorPart editor;

  abstract protected FeedbackResourceFactory getFeedbackResourceFactory();

  /**
   * {@inheritDoc}
   */
  @Override
  public final void setEditor(final IEditorPart editor) {
    this.editor = editor;
  }

  /**
   * 
   * {@inheritDoc}
   */
  @Override
  public final String getHoverInfo(final ITextViewer textViewer, final IRegion hoverRegion) {
    return null; // deprecated getHoverInfo2() is used instead
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final IRegion getHoverRegion(final ITextViewer textViewer, final int offset) {
    return null; // not used
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object getHoverInfo2(final ITextViewer textViewer, final IRegion hoverRegion) {
    final IFile file = ((IFileEditorInput) editor.getEditorInput()).getFile();
    final Optional<? extends FeedbackFile> feedbackFileOptional = getFeedbackResourceFactory().create(file);
    if (feedbackFileOptional.isPresent()) {
      for (final IMarker marker : feedbackFileOptional.get().findMarkers(getMarkerId())) {
        if (isOffsetInMarkerRegion(hoverRegion.getOffset(), marker)) {
          return marker;
        }
      }
    }

    return null;
  }

  /**
   * Returns the id of the {@link IMarker} for which the hover should be displayed.
   * 
   * @return the id of the {@link IMarker} for which the hover should be displayed
   */
  protected abstract String getMarkerId();

  /**
   * Checks whether the hover offset is in-between the start and end position of a marker annotation.
   * 
   * @param hoverOffset
   *          the hover offset (i.e. the cursor position)
   * @param marker
   *          the {@link IMarker}
   * @return <code>true</code> if the hover offset is in-between the start and end position of the marker annotation,
   *         <code>false</code> otherwise
   */
  private boolean isOffsetInMarkerRegion(final int hoverOffset, final IMarker marker) {
    final int markerStartPosition = marker.getAttribute(IMarker.CHAR_START, Integer.MAX_VALUE);
    final int markerEndPosition = marker.getAttribute(IMarker.CHAR_END, -1);
    return hoverOffset >= markerStartPosition && hoverOffset <= markerEndPosition;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final IInformationControlCreator getHoverControlCreator() {
    return new IInformationControlCreator() {
      @Override
      public IInformationControl createInformationControl(final Shell parent) {
        return AbstractJavaEditorMarkerHover.this.createInformationControl(parent, false);
      }
    };
  }

  /**
   * Creates the {@link IInformationControl} that is shown on hovering with the given {@link Shell} as parent.
   * 
   * @param parent
   *          the parent {@link Shell}
   * @param indicates
   *          whether the control should be resizable
   * @return the created {@link IInformationControl}
   */
  protected abstract IInformationControl createInformationControl(final Shell parent, final boolean resizable);
}
