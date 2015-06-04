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
package eu.cloudwave.wp5.feedback.eclipse.base.resources.core;

import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import eu.cloudwave.wp5.feedback.eclipse.base.resources.base.AbstractResourceDeltaDecorator;

/**
 * Implementation of {@link FeedbackResourceDelta}. Acts as a decorator for the wrapped {@link IResourceDelta}.
 */
public class FeedbackResourceDeltaImpl extends AbstractResourceDeltaDecorator implements FeedbackResourceDelta {

  protected FeedbackResourceDeltaImpl(final IResourceDelta delta) {
    super(delta);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<IFile> getChangedFiles() {
    final Set<IFile> affectedFiles = getChangedFiles(delta);
    return ImmutableSet.copyOf(affectedFiles);
  }

  private Set<IFile> getChangedFiles(final IResourceDelta delta) {
    final Set<IFile> affectedFiles = Sets.newHashSet();
    final IResource resource = delta.getResource();
    if (resource instanceof IFile) {
      affectedFiles.add((IFile) resource);
    }
    else {
      for (final IResourceDelta childDelta : delta.getAffectedChildren()) {
        affectedFiles.addAll(getChangedFiles(childDelta));
      }
    }
    return affectedFiles;
  }
}
