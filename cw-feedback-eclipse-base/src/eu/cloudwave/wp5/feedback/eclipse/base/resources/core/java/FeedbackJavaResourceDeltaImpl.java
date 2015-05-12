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

import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResourceDelta;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.FeedbackResourceDeltaImpl;

/**
 * Implementation of {@link FeedbackJavaResourceDelta}. Acts as a decorator for the wrapped {@link IResourceDelta}.
 */
public class FeedbackJavaResourceDeltaImpl extends FeedbackResourceDeltaImpl implements FeedbackJavaResourceDelta, IResourceDelta {

  private final FeedbackJavaResourceFactory feedbackJavaResourceFactory;

  protected FeedbackJavaResourceDeltaImpl(final IResourceDelta delta, final FeedbackJavaResourceFactory feedbackJavaResourceFactory) {
    super(delta);
    this.feedbackJavaResourceFactory = feedbackJavaResourceFactory;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<FeedbackJavaFile> getChangedJavaFiles() {
    final Set<FeedbackJavaFile> javaFiles = Sets.newHashSet();
    for (final IFile file : getChangedFiles()) {
      final Optional<? extends FeedbackJavaFile> feedbackJavaFileOptional = feedbackJavaResourceFactory.create(file);
      if (feedbackJavaFileOptional.isPresent()) {
        javaFiles.add(feedbackJavaFileOptional.get());
      }
    }
    return ImmutableSet.copyOf(javaFiles);
  }

}
