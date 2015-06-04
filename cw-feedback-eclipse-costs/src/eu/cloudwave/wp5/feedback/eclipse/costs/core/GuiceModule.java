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
package eu.cloudwave.wp5.feedback.eclipse.costs.core;

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
import eu.cloudwave.wp5.feedback.eclipse.costs.core.builders.participants.TemplateHandlerImpl;
import eu.cloudwave.wp5.feedback.eclipse.costs.core.feedbackhandler.FeedbackHandlerClient;
import eu.cloudwave.wp5.feedback.eclipse.costs.core.feedbackhandler.FeedbackHandlerClientFactory;
import eu.cloudwave.wp5.feedback.eclipse.costs.core.feedbackhandler.FeedbackHandlerClientImpl;
import eu.cloudwave.wp5.feedback.eclipse.costs.core.feedbackhandler.FeedbackHandlerEclipseClient;
import eu.cloudwave.wp5.feedback.eclipse.costs.core.feedbackhandler.FeedbackHandlerEclipseClientImpl;
import eu.cloudwave.wp5.feedback.eclipse.costs.resources.core.FeedbackResourceExtensionFactoryImpl;
import eu.cloudwave.wp5.feedback.eclipse.costs.resources.core.FeedbackResourceFactoryImpl;
import eu.cloudwave.wp5.feedback.eclipse.costs.resources.core.java.FeedbackJavaResourceFactoryImpl;

public class GuiceModule extends AbstractModule {

  @Override
  protected void configure() {
    // Resource Factories for markers
    bind(FeedbackResourceFactory.class).to(FeedbackResourceFactoryImpl.class).in(Singleton.class);
    bind(FeedbackJavaResourceFactory.class).to(FeedbackJavaResourceFactoryImpl.class).in(Singleton.class);
    bind(FeedbackResourceExtensionFactory.class).to(FeedbackResourceExtensionFactoryImpl.class).in(Singleton.class);

    // Hovers
    bind(TemplateHandler.class).to(TemplateHandlerImpl.class).in(Singleton.class);

    // Cost Builder
    bind(FeedbackCleaner.class).to(FeedbackCleanerImpl.class).in(Singleton.class);

    // Feedback Handler Client
    bind(RestClient.class).to(RestClientImpl.class).in(Singleton.class);
    install(new FactoryModuleBuilder().implement(FeedbackHandlerClient.class, FeedbackHandlerClientImpl.class).build(FeedbackHandlerClientFactory.class));
    bind(FeedbackHandlerEclipseClient.class).to(FeedbackHandlerEclipseClientImpl.class).in(Singleton.class);
  }
}
