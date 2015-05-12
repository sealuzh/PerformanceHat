/*******************************************************************************
 * Copyright 2015 Software Evolution and Architecture Lab, University of Zurich
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package eu.cloudwave.wp5.feedback.eclipse.base.core;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * The activator class controls the plug-in life cycle
 */
public class BasePluginActivator extends AbstractUIPlugin {

  // The shared instance
  private static BasePluginActivator plugin;

  private BundleActivator testFragmentActivator;

  // The shared instance of the injector
  private static Injector injector;

  /**
   * The constructor
   */
  public BasePluginActivator() {}

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
   */
  @Override
  public void start(final BundleContext context) throws Exception {
    super.start(context);

    injector = Guice.createInjector(new BaseModule());
    plugin = this;
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
  public static BasePluginActivator getDefault() {
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
    return imageDescriptorFromPlugin(BaseIds.BASEPLUGIN, path);
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
