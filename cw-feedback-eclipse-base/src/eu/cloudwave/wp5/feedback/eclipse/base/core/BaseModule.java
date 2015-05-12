package eu.cloudwave.wp5.feedback.eclipse.base.core;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

import eu.cloudwave.wp5.feedback.eclipse.base.infrastructure.config.ConfigLoader;
import eu.cloudwave.wp5.feedback.eclipse.base.infrastructure.config.ConfigLoaderImpl;

public class BaseModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(ConfigLoader.class).to(ConfigLoaderImpl.class).in(Singleton.class);
  }
}
