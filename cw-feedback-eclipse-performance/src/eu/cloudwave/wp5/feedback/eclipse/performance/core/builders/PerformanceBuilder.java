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
package eu.cloudwave.wp5.feedback.eclipse.performance.core.builders;

import java.util.List;

import com.google.common.collect.Lists;

import eu.cloudwave.wp5.feedback.eclipse.base.core.builders.FeedbackBuilder;
import eu.cloudwave.wp5.feedback.eclipse.base.core.builders.FeedbackCleaner;
import eu.cloudwave.wp5.feedback.eclipse.base.core.builders.participants.FeedbackBuilderParticipant;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaResourceFactory;
import eu.cloudwave.wp5.feedback.eclipse.performance.PerformancePluginActivator;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.builders.participants.CriticalLoopBuilderParticipant;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.builders.participants.HotspotsBuilderParticipant;

public class PerformanceBuilder extends FeedbackBuilder {

  /**
   * {@inheritDoc}
   */
  @Override
  protected List<FeedbackBuilderParticipant> getParticipants() {
    System.out.println("PerformanceBuilder: register participants");
    List<FeedbackBuilderParticipant> participants = Lists.newArrayList();

    participants.add(new HotspotsBuilderParticipant());
    participants.add(new CriticalLoopBuilderParticipant());

    return participants;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected FeedbackJavaResourceFactory getFeedbackJavaResourceFactory() {
    return PerformancePluginActivator.instance(FeedbackJavaResourceFactory.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected FeedbackCleaner getFeedbackCleaner() {
    return PerformancePluginActivator.instance(FeedbackCleaner.class);
  }
}
