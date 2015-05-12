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
package eu.cloudwave.wp5.feedback.eclipse.base.resources.core;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;

import com.google.common.base.Optional;

/**
 * Implementation of {@link FeedbackResourceFactory}.
 */
abstract public class AbstractFeedbackResourceFactoryImpl implements FeedbackResourceFactory {

  abstract protected FeedbackResourceExtensionFactory getFeedbackResourceExtensionFactory();

  abstract protected FeedbackResourceFactory getFeedbackResourceFactory();

  /**
   * Helper function to check if object is null
   * 
   * @param object
   * @return true if object is NOT null
   */
  protected boolean isNotNull(final Object object) {
    return object != null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<? extends FeedbackResource> create(final IResource resource) {
    if (isNotNull(resource)) {
      return Optional.of(new FeedbackResourceImpl(resource, getFeedbackResourceExtensionFactory()));
    }
    return Optional.absent();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<? extends FeedbackProject> create(final IProject project) {
    if (isNotNull(project)) {
      return Optional.of(new FeedbackProjectImpl(project, getFeedbackResourceExtensionFactory()));
    }
    return Optional.absent();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<? extends FeedbackFile> create(final IFile file) {
    if (isNotNull(file)) {
      return Optional.of(new FeedbackFileImpl(file, getFeedbackResourceExtensionFactory(), getFeedbackResourceFactory()));
    }
    return Optional.absent();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<? extends FeedbackResourceDelta> create(final IResourceDelta resourceDelta) {
    if (isNotNull(resourceDelta)) {
      return Optional.of(new FeedbackResourceDeltaImpl(resourceDelta));
    }
    return Optional.absent();
  }
}
