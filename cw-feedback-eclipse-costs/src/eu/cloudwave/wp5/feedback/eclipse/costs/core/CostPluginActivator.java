package eu.cloudwave.wp5.feedback.eclipse.costs.core;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.google.inject.Guice;
import com.google.inject.Injector;

import eu.cloudwave.wp5.feedback.eclipse.base.ui.hovers.contentprovider.FeedbackInformationControlContentProviderRegistry;
import eu.cloudwave.wp5.feedback.eclipse.costs.core.markers.CostMarkerTypes;
import eu.cloudwave.wp5.feedback.eclipse.costs.ui.hovers.ClientInvocationInformationControlContentProvider;
import eu.cloudwave.wp5.feedback.eclipse.costs.ui.hovers.MethodDeclarationInformationControlContentProvider;

/**
 * The activator class controls the plug-in life cycle
 */
public class CostPluginActivator extends AbstractUIPlugin {

  // The plug-in ID
  public static final String PLUGIN_ID = CostIds.PLUGIN; //$NON-NLS-1$

  // The shared instance
  private static CostPluginActivator plugin;

  private static Injector injector;

  /**
   * The constructor
   */
  public CostPluginActivator() {}

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
   */
  public void start(BundleContext context) throws Exception {
    super.start(context);

    this.registerHoverContentProviders();

    injector = Guice.createInjector(new GuiceModule());
    plugin = this;
  }

  /*
   * Register mapping between Marker Types and Content Providers to the singleton registry of the Base Plugin
   */
  private void registerHoverContentProviders() {
    FeedbackInformationControlContentProviderRegistry.INSTANCE.register(CostMarkerTypes.METHOD_DECLARATION, new MethodDeclarationInformationControlContentProvider());
    FeedbackInformationControlContentProviderRegistry.INSTANCE.register(CostMarkerTypes.CLIENT_INVOCATION, new ClientInvocationInformationControlContentProvider());
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
   */
  public void stop(BundleContext context) throws Exception {
    plugin = null;
    super.stop(context);
  }

  /**
   * Returns the shared instance
   *
   * @return the shared instance
   */
  public static CostPluginActivator getDefault() {
    return plugin;
  }

  public static Injector injector() {
    return injector;
  }

  /**
   * Returns the appropriate concrete implementation for the given type.
   * 
   * @param type
   *          the type for which the concrete implementation is asked
   * @return the appropriate concrete implementation for the given type
   */
  public static <T> T instance(final Class<T> type) {
    return injector().getInstance(type);
  }

}
