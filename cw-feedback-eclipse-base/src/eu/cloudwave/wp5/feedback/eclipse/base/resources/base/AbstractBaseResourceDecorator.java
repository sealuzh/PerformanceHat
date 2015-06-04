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
package eu.cloudwave.wp5.feedback.eclipse.base.resources.base;

import java.net.URI;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IPathVariableManager;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourceAttributes;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.jobs.ISchedulingRule;

/**
 * Abstract base implementation for decorators for sub-classes of {@link IResource}. A concrete decorator should NEVER
 * directly inherit from this class but from a more specific abstract decorator class.
 */
public abstract class AbstractBaseResourceDecorator implements IResource {

  /**
   * Returns the decorated {@link IResource}.
   * 
   * @return the decorated {@link IResource}
   */
  protected abstract IResource resource();

  /**
   * {@inheritDoc}
   */
  @Override
  public Object getAdapter(@SuppressWarnings("rawtypes") final Class adapter) {
    return resource().getAdapter(adapter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean contains(final ISchedulingRule rule) {
    return resource().contains(rule);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isConflicting(final ISchedulingRule rule) {
    return resource().isConflicting(rule);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void accept(final IResourceProxyVisitor visitor, final int memberFlags) throws CoreException {
    resource().accept(visitor, memberFlags);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void accept(final IResourceProxyVisitor visitor, final int depth, final int memberFlags) throws CoreException {
    resource().accept(visitor, depth, memberFlags);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void accept(final IResourceVisitor visitor) throws CoreException {
    resource().accept(visitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void accept(final IResourceVisitor visitor, final int depth, final boolean includePhantoms) throws CoreException {
    resource().accept(visitor, depth, includePhantoms);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void accept(final IResourceVisitor visitor, final int depth, final int memberFlags) throws CoreException {
    resource().accept(visitor, depth, memberFlags);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void clearHistory(final IProgressMonitor monitor) throws CoreException {
    resource().clearHistory(monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void copy(final IPath destination, final boolean force, final IProgressMonitor monitor) throws CoreException {
    resource().copy(destination, force, monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void copy(final IPath destination, final int updateFlags, final IProgressMonitor monitor) throws CoreException {
    resource().copy(destination, updateFlags, monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void copy(final IProjectDescription description, final boolean force, final IProgressMonitor monitor) throws CoreException {
    resource().copy(description, force, monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void copy(final IProjectDescription description, final int updateFlags, final IProgressMonitor monitor) throws CoreException {
    resource().copy(description, updateFlags, monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IMarker createMarker(final String type) throws CoreException {
    return resource().createMarker(type);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IResourceProxy createProxy() {
    return resource().createProxy();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void delete(final boolean force, final IProgressMonitor monitor) throws CoreException {
    resource().delete(force, monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void delete(final int updateFlags, final IProgressMonitor monitor) throws CoreException {
    resource().delete(updateFlags, monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteMarkers(final String type, final boolean includeSubtypes, final int depth) throws CoreException {
    resource().deleteMarkers(type, includeSubtypes, depth);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean exists() {
    return resource().exists();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IMarker findMarker(final long id) throws CoreException {
    return resource().findMarker(id);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IMarker[] findMarkers(final String type, final boolean includeSubtypes, final int depth) throws CoreException {
    return resource().findMarkers(type, includeSubtypes, depth);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int findMaxProblemSeverity(final String type, final boolean includeSubtypes, final int depth) throws CoreException {
    return resource().findMaxProblemSeverity(type, includeSubtypes, depth);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getFileExtension() {
    return resource().getFileExtension();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IPath getFullPath() {
    return resource().getFullPath();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long getLocalTimeStamp() {
    return resource().getLocalTimeStamp();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IPath getLocation() {
    return resource().getLocation();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public URI getLocationURI() {
    return resource().getLocationURI();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IMarker getMarker(final long id) {
    return resource().getMarker(id);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long getModificationStamp() {
    return resource().getModificationStamp();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {
    return resource().getName();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IPathVariableManager getPathVariableManager() {
    return resource().getPathVariableManager();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IContainer getParent() {
    return resource().getParent();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Map<QualifiedName, String> getPersistentProperties() throws CoreException {
    return resource().getPersistentProperties();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getPersistentProperty(final QualifiedName key) throws CoreException {
    return resource().getPersistentProperty(key);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IProject getProject() {
    return resource().getProject();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IPath getProjectRelativePath() {
    return resource().getProjectRelativePath();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IPath getRawLocation() {
    return resource().getRawLocation();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public URI getRawLocationURI() {
    return resource().getRawLocationURI();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ResourceAttributes getResourceAttributes() {
    return resource().getResourceAttributes();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Map<QualifiedName, Object> getSessionProperties() throws CoreException {
    return resource().getSessionProperties();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object getSessionProperty(final QualifiedName key) throws CoreException {
    return resource().getSessionProperty(key);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getType() {
    return resource().getType();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IWorkspace getWorkspace() {
    return resource().getWorkspace();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isAccessible() {
    return resource().isAccessible();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isDerived() {
    return resource().isDerived();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isDerived(final int options) {
    return resource().isDerived(options);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isHidden() {
    return resource().isHidden();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isHidden(final int options) {
    return resource().isHidden(options);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isLinked() {
    return resource().isLinked();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isVirtual() {
    return resource().isVirtual();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isLinked(final int options) {
    return resource().isLinked(options);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("deprecation")
  @Override
  public boolean isLocal(final int depth) {
    return resource().isLocal(depth);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isPhantom() {
    return resource().isPhantom();
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("deprecation")
  @Override
  public boolean isReadOnly() {
    return resource().isReadOnly();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isSynchronized(final int depth) {
    return resource().isSynchronized(depth);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isTeamPrivateMember() {
    return resource().isTeamPrivateMember();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isTeamPrivateMember(final int options) {
    return resource().isTeamPrivateMember(options);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void move(final IPath destination, final boolean force, final IProgressMonitor monitor) throws CoreException {
    resource().move(destination, force, monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void move(final IPath destination, final int updateFlags, final IProgressMonitor monitor) throws CoreException {
    resource().move(destination, updateFlags, monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void move(final IProjectDescription description, final boolean force, final boolean keepHistory, final IProgressMonitor monitor) throws CoreException {
    resource().move(description, force, keepHistory, monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void move(final IProjectDescription description, final int updateFlags, final IProgressMonitor monitor) throws CoreException {
    resource().move(description, updateFlags, monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void refreshLocal(final int depth, final IProgressMonitor monitor) throws CoreException {
    resource().refreshLocal(depth, monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void revertModificationStamp(final long value) throws CoreException {
    resource().revertModificationStamp(value);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("deprecation")
  @Override
  public void setDerived(final boolean isDerived) throws CoreException {
    resource().setDerived(isDerived);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setDerived(final boolean isDerived, final IProgressMonitor monitor) throws CoreException {
    resource().setDerived(isDerived, monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setHidden(final boolean isHidden) throws CoreException {
    resource().setHidden(isHidden);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("deprecation")
  @Override
  public void setLocal(final boolean flag, final int depth, final IProgressMonitor monitor) throws CoreException {
    resource().setLocal(flag, depth, monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long setLocalTimeStamp(final long value) throws CoreException {
    return resource().setLocalTimeStamp(value);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setPersistentProperty(final QualifiedName key, final String value) throws CoreException {
    resource().setPersistentProperty(key, value);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("deprecation")
  @Override
  public void setReadOnly(final boolean readOnly) {
    resource().setReadOnly(readOnly);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setResourceAttributes(final ResourceAttributes attributes) throws CoreException {
    resource().setResourceAttributes(attributes);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSessionProperty(final QualifiedName key, final Object value) throws CoreException {
    resource().setSessionProperty(key, value);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTeamPrivateMember(final boolean isTeamPrivate) throws CoreException {
    resource().setTeamPrivateMember(isTeamPrivate);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void touch(final IProgressMonitor monitor) throws CoreException {
    resource().touch(monitor);
  }

}
