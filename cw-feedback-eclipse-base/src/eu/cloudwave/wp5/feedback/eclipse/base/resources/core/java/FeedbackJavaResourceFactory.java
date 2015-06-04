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
package eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceDelta;

import com.google.common.base.Optional;

/**
 * A factory for feedback Java resources.
 */
public interface FeedbackJavaResourceFactory {

  /**
   * Checks whether the given project is a Java project and if yes, creates and returns a corresponding
   * {@link FeedbackJavaProject}.
   * 
   * @param project
   *          the {@link IProject}
   * @return the created {@link FeedbackJavaProject} or {@link Optional#absent()}, if the given {@link IProject} is not
   *         <code>null</code> and is a Java project.
   */
  public Optional<? extends FeedbackJavaProject> create(IProject project);

  /**
   * Checks whether the given file is a Java source file (.java-file) and if yes, creates and returns a corresponding
   * {@link FeedbackJavaFile}.
   * 
   * @param file
   *          the {@link IFile}
   * @return the created {@link FeedbackJavaFile} or {@link Optional#absent()} if the given {@link IFile} is not
   *         <code>null</code> and is a Java source file
   */
  public Optional<? extends FeedbackJavaFile> create(IFile file);

  /**
   * Creates a {@link FeedbackJavaResourceDelta}.
   * 
   * @param resourceDelta
   *          the {@link IResourceDelta}
   * @return the created {@link FeedbackJavaResourceDelta} or {@link Optional#absent()}, if the given
   *         {@link IResourceDelta} is <code>null</code>
   */
  public Optional<? extends FeedbackJavaResourceDelta> create(IResourceDelta resourceDelta);

}
