package eu.cloudwave.wp5.feedback.eclipse.performance;

import com.google.inject.AbstractModule;

/**
 * A provider for {@link AbstractModule}'s. Different implementation of this interface allow to provide different
 * modules for production and testing.
 */
public interface ModuleProvider {

  /**
   * Get the {@link AbstractModule}.
   * 
   * @return the {@link AbstractModule}
   */
  public AbstractModule module();

}
