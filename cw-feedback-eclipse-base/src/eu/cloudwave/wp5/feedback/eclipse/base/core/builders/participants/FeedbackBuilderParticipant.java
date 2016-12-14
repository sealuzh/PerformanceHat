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
package eu.cloudwave.wp5.feedback.eclipse.base.core.builders.participants;

import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.dom.CompilationUnit;

import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaFile;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaProject;

/**
 * A {@link FeedbackBuilderParticipant} is a participant that acts as a part of the whole feedback builder. As soon as
 * it is registered in the feedback builder it is executed each time the feedback builder is triggered.
 */
public interface FeedbackBuilderParticipant {	
	
 /**
   * Template method that is triggered once before the build, allowing implementers to prepare for the build. Subclasses should to
   * the actual work here.
   * 
   * @param project
   *          the {@link FeedbackJavaProject}
   * @param javaFiles
   *          the {@link FeedbackJavaFile}s
   */	
  public void prepare(FeedbackJavaProject project, Set<FeedbackJavaFile> javaFiles) throws CoreException;

  /**
   * Template method that is triggered during the build for each Java file that has to be built. Subclasses should to
   * the actual work here.
   * 
   * @param project
   *          the {@link FeedbackJavaProject}
   * @param javaFile
   *          the {@link FeedbackJavaFile}
   * @param astRoot
   *          the {@link CompilationUnit} of the file
   */
  public void buildFile(final FeedbackJavaProject project, final FeedbackJavaFile javaFile, final CompilationUnit astRoot);
  
  
  /**
   * Template method that is triggered once after the build, allowing implementers to clean up ressources. Subclasses should to
   * the actual work here.
   * 
   * @param project
   *          the {@link FeedbackJavaProject}
   * @param javaFiles
   *          the {@link FeedbackJavaFile}s
   */
  public void cleanup(FeedbackJavaProject project, Set<FeedbackJavaFile> javaFiles) throws CoreException;

}
