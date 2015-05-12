package eu.cloudwave.wp5.feedback.eclipse.base.ui.factories;

import org.eclipse.swt.SWTError;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;

import com.google.common.base.Optional;

/**
 * A Factory for SWT controls.
 */
public interface ControlFactory {

  /**
   * Creates a {@link Composite}
   * 
   * @param parent
   *          the parent {@link Composite}
   * @param layout
   *          the {@link Layout} for the {@link Composite}
   * @return the created {@link Composite}
   */
  public Composite createComposite(Composite parent, Layout layout);

  /**
   * Creates a {@link Composite}
   * 
   * @param parent
   *          the parent {@link Composite}
   * @param layout
   *          the {@link Layout} for the {@link Composite}
   * @param layoutData
   *          the layout data for the {@link Composite}
   * @return the created {@link Composite}
   */
  public Composite createComposite(Composite parent, Layout layout, Object layoutData);

  /**
   * Creates a {@link Label}.
   * 
   * @param parent
   *          the parent {@link Composite}
   * @param text
   *          the text for the {@link Label}
   * @return the created {@link Label}
   */
  public Label createLabel(Composite parent, String text);

  /**
   * Creates a {@link Button}.
   * 
   * @param parent
   *          the parent {@link Composite}
   * @param text
   *          the text for the {@link Button}
   * @param icon
   *          the icon for the {@link Button}
   * @param action
   *          the click action of the button
   * @return the created {@link Button}.
   */
  public Button createButton(Composite parent, String text, Image icon, SelectionListener action);

  /**
   * Creates a {@link Button}.
   * 
   * @param parent
   *          the parent {@link Composite}
   * @param text
   *          the text for the {@link Button}
   * @param action
   *          the click action of the button
   * @return the created {@link Button}.
   */
  public Button createButton(Composite parent, String text, SelectionListener action);

  /**
   * Creates a {@link Button}.
   * 
   * @param parent
   *          the parent {@link Composite}
   * @param icon
   *          the icon for the {@link Button}
   * @param action
   *          the click action of the button
   * @return the created {@link Button}.
   */
  public Button createButton(Composite parent, Image icon, SelectionListener action);

  /**
   * Creates a {@link Browser} and adds it to the given parent {@link Composite}.
   * 
   * @param parent
   *          the parent {@link Composite}
   * @param layoutData
   *          the layout data for the {@link Browser}
   * @return the created {@link Browser}
   */
  public Browser createBrowser(Composite parent, Object layoutData) throws SWTError;

  /**
   * Creates a {@link Browser} and adds it to the given parent {@link Composite}. If the Browser could not be
   * successfully created, a fallback label displaying a respective error message is created and added to the parent
   * composite.
   * 
   * @param parent
   *          the parent {@link Composite}
   * @param layoutData
   *          the layout data for the {@link Browser}
   * @return the created {@link Browser} or {@link Optional#absent()}, if the browser could not be successfully created
   */
  public Optional<Browser> createBrowserOrFallback(Composite parent, Object layoutData);

}
