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

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.dom.CompilationUnit;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;

import eu.cloudwave.wp5.feedback.eclipse.base.core.builders.FeedbackBuilder;
import eu.cloudwave.wp5.feedback.eclipse.base.core.builders.FeedbackCleaner;
import eu.cloudwave.wp5.feedback.eclipse.base.core.builders.participants.FeedbackBuilderParticipant;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaFile;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaProject;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaResourceFactory;
import eu.cloudwave.wp5.feedback.eclipse.performance.Ids;
import eu.cloudwave.wp5.feedback.eclipse.performance.PerformancePluginActivator;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.builders.participants.PerformancePluginsParticipant;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.PerformancePlugin;

/**
 * The feedback Builder for the plugin based Performance Builder
 *
 */
public class PerformanceBuilder extends FeedbackBuilder {
	
  /**
   * A static variant of the builder, to process files outside if the Build Cycle
   * @param project is the project to which the file belongs to
   * @param file is the file to process
   * @throws CoreException
   */
  public static void processFile(FeedbackJavaProject project, FeedbackJavaFile file)  throws CoreException{
	 	//Get the Ast
	  	Optional<CompilationUnit> astRoot = file.getAstRoot();
	 	if(!astRoot.isPresent()) return;	
	 	//Get all participants
	 	List<FeedbackBuilderParticipant> participants = getParticipantsStatic();
	 	//Prepare all Participants
	 	for (final FeedbackBuilderParticipant participant : participants) {
		 	participant.prepare(project, Collections.singleton(file));
	  	}
	 	//Excecute all Participants
	 	for (final FeedbackBuilderParticipant participant : participants) {
	 		participant.buildFile(project, file, astRoot.get());
	 	}
	 	//Clean up all Participants
	 	for (final FeedbackBuilderParticipant participant : participants) {
	 		participant.cleanup(project, Collections.singleton(file));
	 	}
  }
  
  /**
   * A static version of the getParticipants function
   * @return list of PerformancePluginParticipants
   */
  private static List<FeedbackBuilderParticipant> getParticipantsStatic(){
	    List<PerformancePlugin> markers  = Lists.newArrayList();
	    IExtensionRegistry reg = Platform.getExtensionRegistry();
	    
	    //looks up all Plugins registered over extension points
	    for(IConfigurationElement elem: reg.getConfigurationElementsFor(Ids.EXTENSION)){
	    	try {
	    		markers.add((PerformancePlugin) elem.createExecutableExtension("class"));
			} catch (CoreException e) {
				e.printStackTrace();
			}
	    }
	   
	    markers = DependencyOrderer.order(markers);
	    //initiate all plugins
	    return markers.stream().map(m -> new PerformancePluginsParticipant(m)).collect(Collectors.toList());
  }
	
  /**
   * {@inheritDoc}
   */
  @Override
  protected List<FeedbackBuilderParticipant> getParticipants() {
	 return getParticipantsStatic();
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
