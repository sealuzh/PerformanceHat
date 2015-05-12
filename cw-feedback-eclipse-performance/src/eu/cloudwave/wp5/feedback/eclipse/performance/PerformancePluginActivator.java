package eu.cloudwave.wp5.feedback.eclipse.performance;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

import eu.cloudwave.wp5.feedback.eclipse.base.ui.hovers.contentprovider.FeedbackInformationControlContentProviderRegistry;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.markers.PerformanceMarkerTypes;
import eu.cloudwave.wp5.feedback.eclipse.performance.ui.hovers.contentprovider.CollectionSizeInformationControlContentProvider;
import eu.cloudwave.wp5.feedback.eclipse.performance.ui.hovers.contentprovider.HotspotsInformationControlContentProvider;

/**
 * The activator class controls the plug-in life cycle
 */
public class PerformancePluginActivator extends AbstractUIPlugin {

  private static final String TEST_MODULE_PROVIDER = "eu.cloudwave.wp5.feedback.eclipse.TestModuleProvider";

  // The shared instance
  private static PerformancePluginActivator plugin;

  private BundleActivator testFragmentActivator;

  private static Injector injector;

  /**
   * The constructor
   */
  public PerformancePluginActivator() {}

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
   */
  @Override
  public void start(final BundleContext context) throws Exception {
    super.start(context);

    this.registerHoverContentProviders();

    injector = Guice.createInjector(getInjectionModule(context));
    plugin = this;
  }

  /*
   * Register mapping between Marker Types and Content Providers to the singleton registry of the Base Plugin
   */
  private void registerHoverContentProviders() {
    FeedbackInformationControlContentProviderRegistry.INSTANCE.register(PerformanceMarkerTypes.HOTSPOT, new HotspotsInformationControlContentProvider());
    FeedbackInformationControlContentProviderRegistry.INSTANCE.register(PerformanceMarkerTypes.COLLECTION_SIZE, new CollectionSizeInformationControlContentProvider());
  }

  /*
   * Workaround: this is a workaround to load a different DI module when running the tests.
   * 
   * It is required because mocking with powermock didn't work for SWTBot tests. The problem is that SWTBot tests
   * depends on Junit 4.11 and hamcrest. Powermock provides a legacy version for Junit 4.11, but this legacy version is
   * not compatible with hamcrest.
   */
  private Module getInjectionModule(final BundleContext context) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    ModuleProvider moduleProvider;
    try {
      @SuppressWarnings("unchecked")
      final Class<? extends ModuleProvider> moduleProviderClass = (Class<? extends ModuleProvider>) Class.forName(TEST_MODULE_PROVIDER);
      moduleProvider = moduleProviderClass.newInstance();
    }
    catch (final ClassNotFoundException e) {
      moduleProvider = new ProductionModuleProvider();
    }
    return moduleProvider.module();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
   */
  @Override
  public void stop(final BundleContext context) throws Exception {
    if (testFragmentActivator != null) {
      testFragmentActivator.stop(context);
    }
    plugin = null;
    super.stop(context);
  }

  /**
   * Returns the shared instance
   *
   * @return the shared instance
   */
  public static PerformancePluginActivator getDefault() {
    return plugin;
  }

  /**
   * Returns an image descriptor for the image file at the given plug-in relative path
   *
   * @param path
   *          the path
   * @return the image descriptor
   */
  public static ImageDescriptor getImageDescriptor(final String path) {
    return imageDescriptorFromPlugin(Ids.PLUGIN, path);
  }

  public static Injector injector() {
    return injector;
  }

  /**
   * Returns the appropriate concrete implementation for the given type. The lookup is delegated to the
   * {@link ServiceRegistry}.
   * 
   * @param type
   *          the type for which the concrete implementation is asked
   * @return the appropriate concrete implementation for the given type
   */
  public static <T> T instance(final Class<T> type) {
    return injector().getInstance(type);
  }
}
