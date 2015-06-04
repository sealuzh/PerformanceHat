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

import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaFile;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaProject;

/**
 * A {@link FeedbackBuilderParticipant} is a participant that acts as a part of the whole feedback builder. As soon as
 * it is registered in the feedback builder it is executed each time the feedback builder is triggered.
 */
public interface FeedbackBuilderParticipant {

  /**
   * Builds the given files of the given project
   * 
   * @param project
   *          the project
   * @param files
   *          the Java files to be built
   * @throws CoreException
   *           if build process could not be successfully finished
   */
  public void build(final FeedbackJavaProject project, final Set<FeedbackJavaFile> files) throws CoreException;
}
