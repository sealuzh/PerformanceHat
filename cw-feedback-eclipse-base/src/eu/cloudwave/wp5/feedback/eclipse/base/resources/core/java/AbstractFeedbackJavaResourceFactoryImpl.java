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
package eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.JavaCore;

import com.google.common.base.Optional;

import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.FeedbackResourceExtensionFactory;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.FeedbackResourceFactory;

/**
 * Implementation of {@link FeedbackJavaResourceFactory}.
 */
abstract public class AbstractFeedbackJavaResourceFactoryImpl implements FeedbackJavaResourceFactory {

  private static final String JAVA_SOURCE_FILE_EXTENSION = "java";

  abstract protected FeedbackResourceExtensionFactory getFeedbackResourceExtensionFactory();

  abstract protected FeedbackResourceFactory getFeedbackResourceFactory();

  abstract protected FeedbackJavaResourceFactory getFeedbackJavaResourceFactory();

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<? extends FeedbackJavaProject> create(final IProject project) {
    if (isJavaProject(project)) {
      return Optional.of(new FeedbackJavaProjectImpl(project, getFeedbackResourceExtensionFactory(), getFeedbackJavaResourceFactory()));
    }
    return Optional.absent();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<? extends FeedbackJavaFile> create(final IFile file) {
    if (isJavaSourceFile(file)) {
      return Optional.of(new FeedbackJavaFileImpl(file, getFeedbackResourceExtensionFactory(), getFeedbackResourceFactory()));
    }
    return Optional.absent();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<? extends FeedbackJavaResourceDelta> create(final IResourceDelta resourceDelta) {
    if (resourceDelta != null) {
      return Optional.of(new FeedbackJavaResourceDeltaImpl(resourceDelta, getFeedbackJavaResourceFactory()));
    }
    return Optional.absent();
  }

  protected boolean isJavaProject(final IProject project) {
    try {
      return project != null && project.hasNature(JavaCore.NATURE_ID);
    }
    catch (final CoreException e) {
      return false;
    }
  }

  protected static boolean isJavaSourceFile(final IFile file) {
    return file != null && file.getFileExtension().equals(JAVA_SOURCE_FILE_EXTENSION);
  }

}
