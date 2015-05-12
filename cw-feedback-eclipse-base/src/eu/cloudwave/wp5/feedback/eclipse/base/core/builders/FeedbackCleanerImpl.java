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
package eu.cloudwave.wp5.feedback.eclipse.base.core.builders;

import java.util.Set;

import org.eclipse.core.runtime.CoreException;

import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaFile;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaProject;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaResourceDelta;

/**
 * Implementation of {@link FeedbackCleaner}.
 */
public class FeedbackCleanerImpl implements FeedbackCleaner {

  /**
   * {@inheritDoc}
   */
  @Override
  public void cleanAll(final FeedbackJavaProject project) throws CoreException {
    clean(project.getJavaSourceFiles());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void cleanDelta(final FeedbackJavaResourceDelta delta) throws CoreException {
    clean(delta.getChangedJavaFiles());
  }

  private void clean(final Set<FeedbackJavaFile> javaFiles) throws CoreException {
    for (final FeedbackJavaFile javaFile : javaFiles) {
      javaFile.deleteMarkers();
    }
  }
}
