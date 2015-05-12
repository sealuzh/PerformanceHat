/*******************************************************************************
 * Copyright 2015 Software Evolution and Architecture Lab, University of Zurich
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

import com.google.common.base.Optional;

import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaFile;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaProject;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.MarkerSpecification;

/**
 * Provides common functionality for concrete {@link FeedbackBuilderParticipant}'s.
 */
public abstract class AbstractFeedbackBuilderParticipant implements FeedbackBuilderParticipant {

  /**
   * Iterates over the files to be built and calls
   * {@link #buildFile(FeedbackJavaProject, FeedbackJavaFile, CompilationUnit)} on each of them.
   * 
   * {@link #buildFile(FeedbackJavaProject, FeedbackJavaFile, CompilationUnit)} for each file.
   */
  @Override
  public void build(final FeedbackJavaProject project, final Set<FeedbackJavaFile> javaFiles) throws CoreException {
    for (final FeedbackJavaFile javaFile : javaFiles) {
      final Optional<CompilationUnit> astRootOptional = javaFile.getAstRoot();
      if (astRootOptional.isPresent()) {
        buildFile(project, javaFile, astRootOptional.get());
      }
    }
  }

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
  protected abstract void buildFile(final FeedbackJavaProject project, final FeedbackJavaFile javaFile, final CompilationUnit astRoot);

  /**
   * Add a marker to the given file according to the given specification.
   * 
   * @param javaFile
   *          the file that the marker should be appended
   * @param markerSpecification
   *          the specification of the marker
   */
  protected final void addMarker(final FeedbackJavaFile javaFile, final MarkerSpecification markerSpecification) {
    try {
      javaFile.addMarker(markerSpecification);
    }
    catch (final CoreException e) {
      e.printStackTrace();
    }
  }

}
