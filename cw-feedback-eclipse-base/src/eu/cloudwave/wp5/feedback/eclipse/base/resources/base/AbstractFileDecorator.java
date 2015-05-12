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

import java.io.InputStream;
import java.io.Reader;
import java.net.URI;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFileState;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.content.IContentDescription;

/**
 * An abstract base implementation for {@link IFile} decorators. Delegates all methods to the decorated {@link IFile}.
 */
public abstract class AbstractFileDecorator extends AbstractBaseResourceDecorator implements IFile {

  protected final IFile file;

  public AbstractFileDecorator(final IFile file) {
    this.file = file;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected IFile resource() {
    return file;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void appendContents(final InputStream source, final boolean force, final boolean keepHistory, final IProgressMonitor monitor) throws CoreException {
    resource().appendContents(source, force, keepHistory, monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void appendContents(final InputStream source, final int updateFlags, final IProgressMonitor monitor) throws CoreException {
    resource().appendContents(source, updateFlags, monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void create(final InputStream source, final boolean force, final IProgressMonitor monitor) throws CoreException {
    resource().create(source, force, monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void create(final InputStream source, final int updateFlags, final IProgressMonitor monitor) throws CoreException {
    resource().create(source, updateFlags, monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void createLink(final IPath localLocation, final int updateFlags, final IProgressMonitor monitor) throws CoreException {
    resource().createLink(localLocation, updateFlags, monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void createLink(final URI location, final int updateFlags, final IProgressMonitor monitor) throws CoreException {
    resource().createLink(location, updateFlags, monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void delete(final boolean force, final boolean keepHistory, final IProgressMonitor monitor) throws CoreException {
    resource().delete(force, keepHistory, monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getCharset() throws CoreException {
    return resource().getCharset();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getCharset(final boolean checkImplicit) throws CoreException {
    return resource().getCharset(checkImplicit);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getCharsetFor(final Reader reader) throws CoreException {
    return resource().getCharsetFor(reader);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IContentDescription getContentDescription() throws CoreException {
    return resource().getContentDescription();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public InputStream getContents() throws CoreException {
    return resource().getContents();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public InputStream getContents(final boolean force) throws CoreException {
    return resource().getContents(force);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("deprecation")
  @Override
  public int getEncoding() throws CoreException {
    return resource().getEncoding();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IFileState[] getHistory(final IProgressMonitor monitor) throws CoreException {
    return resource().getHistory(monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void move(final IPath destination, final boolean force, final boolean keepHistory, final IProgressMonitor monitor) throws CoreException {
    resource().move(destination, force, keepHistory, monitor);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("deprecation")
  @Override
  public void setCharset(final String newCharset) throws CoreException {
    resource().setCharset(newCharset);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setCharset(final String newCharset, final IProgressMonitor monitor) throws CoreException {
    resource().setCharset(newCharset, monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setContents(final InputStream source, final boolean force, final boolean keepHistory, final IProgressMonitor monitor) throws CoreException {
    resource().setContents(source, force, keepHistory, monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setContents(final IFileState source, final boolean force, final boolean keepHistory, final IProgressMonitor monitor) throws CoreException {
    resource().setContents(source, force, keepHistory, monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setContents(final InputStream source, final int updateFlags, final IProgressMonitor monitor) throws CoreException {
    resource().setContents(source, updateFlags, monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setContents(final IFileState source, final int updateFlags, final IProgressMonitor monitor) throws CoreException {
    resource().setContents(source, updateFlags, monitor);
  }
  /**
   * {@inheritDoc}
   */
}
