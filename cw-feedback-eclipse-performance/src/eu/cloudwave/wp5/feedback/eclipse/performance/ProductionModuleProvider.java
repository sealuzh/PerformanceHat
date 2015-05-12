package eu.cloudwave.wp5.feedback.eclipse.performance;

import com.google.inject.AbstractModule;

/**
 * Implementation of {@link ModuleProvider} for production.
 */
public class ProductionModuleProvider implements ModuleProvider {

  /**
   * {@inheritDoc}
   */
  @Override
  public AbstractModule module() {
    return new ProductionModule();
  }

}
