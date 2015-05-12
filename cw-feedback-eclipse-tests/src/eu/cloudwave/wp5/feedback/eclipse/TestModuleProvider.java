package eu.cloudwave.wp5.feedback.eclipse;

import com.google.inject.AbstractModule;

import eu.cloudwave.wp5.feedback.eclipse.performance.ModuleProvider;

/**
 * Implementation of {@link ModuleProvider} for testing.
 */
public class TestModuleProvider implements ModuleProvider {

  /**
   * {@inheritDoc}
   */
  @Override
  public AbstractModule module() {
    return new TestModule();
  }

}
