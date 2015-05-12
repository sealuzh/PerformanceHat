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

import java.util.Set;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

import eu.cloudwave.wp5.feedback.eclipse.base.resources.base.AbstractResourceDecorator;

/**
 * Implementation of {@link FeedbackResource}. Acts as a decorator for the wrapped {@link IResource}.
 */
public class FeedbackResourceImpl extends AbstractResourceDecorator implements FeedbackResource {

  private final FeedbackResourceExtension feedbackResourceExtension;

  protected FeedbackResourceImpl(final IResource resource, final FeedbackResourceExtensionFactory extensionFactory) {
    super(resource);
    this.feedbackResourceExtension = extensionFactory.create(resource);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteMarkers() throws CoreException {
    feedbackResourceExtension.deleteMarkers();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<IMarker> findMarkers(final String id) {
    return feedbackResourceExtension.findMarkers(id);
  }

}
