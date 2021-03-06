/*******************************************************************************
 * Copyright 2015 Software Evolution and Architecture Lab, University of Zurich
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 ******************************************************************************/
package eu.cloudwave.wp5.feedback.eclipse.costs.core.builders;

import java.util.List;

import com.google.common.collect.Lists;

import eu.cloudwave.wp5.feedback.eclipse.base.core.builders.FeedbackBuilder;
import eu.cloudwave.wp5.feedback.eclipse.base.core.builders.FeedbackCleaner;
import eu.cloudwave.wp5.feedback.eclipse.base.core.builders.participants.FeedbackBuilderParticipant;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaResourceFactory;
import eu.cloudwave.wp5.feedback.eclipse.costs.core.CostPluginActivator;
import eu.cloudwave.wp5.feedback.eclipse.costs.core.builders.participants.MicroserviceClientInvocationParticipant;
import eu.cloudwave.wp5.feedback.eclipse.costs.core.builders.participants.MicroserviceMethodDeclarationParticipant;

/**
 * A builder for the cost feedback plugin. Delegates the work to a list of registered participants.
 */
public class CostBuilder extends FeedbackBuilder {

  /**
   * {@inheritDoc}
   */
  @Override
  protected List<FeedbackBuilderParticipant> getParticipants() {
    System.out.println("CostBuilder: register participants");
    List<FeedbackBuilderParticipant> participants = Lists.newArrayList();

    participants.add(new MicroserviceClientInvocationParticipant());
    participants.add(new MicroserviceMethodDeclarationParticipant());

    return participants;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected FeedbackJavaResourceFactory getFeedbackJavaResourceFactory() {
    return CostPluginActivator.instance(FeedbackJavaResourceFactory.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected FeedbackCleaner getFeedbackCleaner() {
    return CostPluginActivator.instance(FeedbackCleaner.class);
  }
}
