/*******************************************************************************
 * Copyright 2015 Software Evolution and Architecture Lab, University of Zurich
 * 
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
package eu.cloudwave.wp5.feedback.eclipse.performance;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.assistedinject.FactoryModuleBuilder;

import eu.cloudwave.wp5.feedback.eclipse.base.core.builders.FeedbackCleaner;
import eu.cloudwave.wp5.feedback.eclipse.base.core.builders.FeedbackCleanerImpl;
import eu.cloudwave.wp5.feedback.eclipse.base.infrastructure.rest.RestClient;
import eu.cloudwave.wp5.feedback.eclipse.base.infrastructure.rest.RestClientImpl;
import eu.cloudwave.wp5.feedback.eclipse.base.infrastructure.template.TemplateHandler;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.FeedbackResourceExtensionFactory;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.FeedbackResourceFactory;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaResourceFactory;
import eu.cloudwave.wp5.feedback.eclipse.base.ui.factories.ColorFactory;
import eu.cloudwave.wp5.feedback.eclipse.base.ui.factories.ColorFactoryImpl;
import eu.cloudwave.wp5.feedback.eclipse.base.ui.factories.ControlFactory;
import eu.cloudwave.wp5.feedback.eclipse.base.ui.factories.ControlFactoryImpl;
import eu.cloudwave.wp5.feedback.eclipse.base.ui.factories.LayoutFactory;
import eu.cloudwave.wp5.feedback.eclipse.base.ui.factories.LayoutFactoryImpl;
import eu.cloudwave.wp5.feedback.eclipse.base.ui.hovers.contentprovider.FeedbackInformationControlContentProviderRegistry;
import eu.cloudwave.wp5.feedback.eclipse.base.ui.hovers.contentprovider.FeedbackInformationControlContentProviderRegistryImpl;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.builders.participants.TemplateHandlerImpl;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.feedbackhandler.FeedbackHandlerClient;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.feedbackhandler.FeedbackHandlerClientFactory;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.feedbackhandler.FeedbackHandlerClientImpl;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.feedbackhandler.FeedbackHandlerEclipseClient;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.feedbackhandler.FeedbackHandlerEclipseClientImpl;
import eu.cloudwave.wp5.feedback.eclipse.performance.resources.core.FeedbackResourceExtensionFactoryImpl;
import eu.cloudwave.wp5.feedback.eclipse.performance.resources.core.FeedbackResourceFactoryImpl;
import eu.cloudwave.wp5.feedback.eclipse.performance.resources.core.java.FeedbackJavaResourceFactoryImpl;

public class ProductionModule extends AbstractModule {

  /**
   * {@inheritDoc}
   */
  @Override
  protected final void configure() {
    finalConfiguration();
    overridableConfiguration();
  }

  private final void finalConfiguration() {
    // infrastructure
    bind(RestClient.class).to(RestClientImpl.class).in(Singleton.class);
    bind(TemplateHandler.class).to(TemplateHandlerImpl.class).in(Singleton.class);

    // Resource Factories
    bind(FeedbackResourceFactory.class).to(FeedbackResourceFactoryImpl.class).in(Singleton.class);
    bind(FeedbackJavaResourceFactory.class).to(FeedbackJavaResourceFactoryImpl.class).in(Singleton.class);
    bind(FeedbackResourceExtensionFactory.class).to(FeedbackResourceExtensionFactoryImpl.class).in(Singleton.class);

    // builder
    bind(FeedbackCleaner.class).to(FeedbackCleanerImpl.class).in(Singleton.class);
    bind(FeedbackInformationControlContentProviderRegistry.class).to(FeedbackInformationControlContentProviderRegistryImpl.class).in(Singleton.class);

    // feedback handler client
    install(new FactoryModuleBuilder().implement(FeedbackHandlerClient.class, FeedbackHandlerClientImpl.class).build(FeedbackHandlerClientFactory.class));

    // UI
    bind(ColorFactory.class).to(ColorFactoryImpl.class).in(Singleton.class);
    bind(ControlFactory.class).to(ControlFactoryImpl.class).in(Singleton.class);
    bind(LayoutFactory.class).to(LayoutFactoryImpl.class).in(Singleton.class);
  }

  protected void overridableConfiguration() {
    // core
    bind(FeedbackHandlerEclipseClient.class).to(FeedbackHandlerEclipseClientImpl.class).in(Singleton.class);
  }

}
