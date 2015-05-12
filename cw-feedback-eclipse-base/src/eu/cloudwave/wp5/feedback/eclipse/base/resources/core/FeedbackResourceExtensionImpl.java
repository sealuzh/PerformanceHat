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

import com.google.common.collect.ImmutableSet;

/**
 * Tis class provides functionality
 * 
 * Implementation of {@link FeedbackResourceExtension}. Can be added to {@link IResource}'s via composition to overcome
 * the fact that Java doesn't support multiple inheritance.
 */
public class FeedbackResourceExtensionImpl implements FeedbackResourceExtension {

  private final IResource resource;

  private final String type;

  public FeedbackResourceExtensionImpl(final IResource resource, String type) {
    this.resource = resource;
    this.type = type;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteMarkers() throws CoreException {
    resource.deleteMarkers(this.type, true, IResource.DEPTH_INFINITE);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<IMarker> findMarkers(final String id) {
    try {
      return ImmutableSet.copyOf(resource.findMarkers(id, true, IResource.DEPTH_INFINITE));
    }
    catch (final CoreException e) {
      return ImmutableSet.of();
    }
  }
}
