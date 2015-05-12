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
package eu.cloudwave.wp5.feedback.eclipse.base.resources.base;

import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

/**
 * An abstract base implementation for {@link IResourceDelta} decorators. Delegates all methods to the decorated
 * {@link IResourceDelta}.
 */
public class AbstractResourceDeltaDecorator implements IResourceDelta {

  protected final IResourceDelta delta;

  public AbstractResourceDeltaDecorator(final IResourceDelta delta) {
    this.delta = delta;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object getAdapter(@SuppressWarnings("rawtypes") final Class adapter) {
    return delta.getAdapter(adapter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void accept(final IResourceDeltaVisitor visitor) throws CoreException {
    delta.accept(visitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void accept(final IResourceDeltaVisitor visitor, final boolean includePhantoms) throws CoreException {
    delta.accept(visitor, includePhantoms);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void accept(final IResourceDeltaVisitor visitor, final int memberFlags) throws CoreException {
    delta.accept(visitor, memberFlags);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IResourceDelta findMember(final IPath path) {
    return delta.findMember(path);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IResourceDelta[] getAffectedChildren() {
    return getAffectedChildren();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IResourceDelta[] getAffectedChildren(final int kindMask) {
    return delta.getAffectedChildren(kindMask);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IResourceDelta[] getAffectedChildren(final int kindMask, final int memberFlags) {
    return delta.getAffectedChildren(kindMask, memberFlags);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getFlags() {
    return delta.getFlags();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IPath getFullPath() {
    return delta.getFullPath();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getKind() {
    return delta.getKind();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IMarkerDelta[] getMarkerDeltas() {
    return delta.getMarkerDeltas();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IPath getMovedFromPath() {
    return delta.getMovedFromPath();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IPath getMovedToPath() {
    return delta.getMovedToPath();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IPath getProjectRelativePath() {
    return delta.getProjectRelativePath();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IResource getResource() {
    return delta.getResource();
  }

}
