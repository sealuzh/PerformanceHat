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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

import com.google.common.collect.Lists;

import eu.cloudwave.wp5.feedback.eclipse.base.core.builders.FeedbackBuilder;
import eu.cloudwave.wp5.feedback.eclipse.base.core.builders.FeedbackCleaner;
import eu.cloudwave.wp5.feedback.eclipse.base.core.builders.participants.FeedbackBuilderParticipant;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaResourceFactory;
import eu.cloudwave.wp5.feedback.eclipse.performance.Ids;
import eu.cloudwave.wp5.feedback.eclipse.performance.PerformancePluginActivator;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.builders.participants.CriticalLoopBuilderParticipant;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.builders.participants.ProgrammMarkerParticipant;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ProgrammMarker;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.CriticalLoopProgrammMarker;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.HotspotProgrammMarker;

public class PerformanceBuilder extends FeedbackBuilder {

  /**
   * {@inheritDoc}
   */
  @Override
  protected List<FeedbackBuilderParticipant> getParticipants() {
    List<FeedbackBuilderParticipant> participants = Lists.newArrayList();
    
    IExtensionRegistry reg = Platform.getExtensionRegistry();
    
    for(IConfigurationElement elem: reg.getConfigurationElementsFor(Ids.MARKER)){
    	try {
			participants.add(new ProgrammMarkerParticipant((ProgrammMarker)elem.createExecutableExtension("Class")));
		} catch (CoreException e) {
			e.printStackTrace();
		}
    }
    
    //Todo: Make These Dynamically
    //Todo: Make new AstVisiter whiches wrappes the AstNodes when requested
    //Todo:   Lazy inject Performance Metricy from Datasources if requested
    //Todo:   Allow Helper
    //Todo:  Call ProgrammMarkerVisitor
    //Todo:   MakeStatePattern where a Subvisitor can be returned to process childs
    //Todo:   Stuff like file etc... is in BuilderContext which is passed down, the helpers are also their
    //Todo:   Think about Hierarchical Visitor
    //			alla: visitMethodeDeclaration, visitMethodeCall, visitMethodeOccurence <-- the last is called in both cases
    //			the visitors params are wrappers builded automatically each time needed
    //participants.add(new HotspotsBuilderParticipant());
	participants.add(new ProgrammMarkerParticipant(new HotspotProgrammMarker()));
    participants.add(new ProgrammMarkerParticipant(new CriticalLoopProgrammMarker()));

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
