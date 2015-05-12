package eu.cloudwave.wp5.feedback.eclipse.base.resources.base;

import java.net.URI;
import java.util.Map;

import org.eclipse.core.resources.FileInfoMatcherDescription;
import org.eclipse.core.resources.IBuildConfiguration;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceFilterDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IPluginDescriptor;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.content.IContentTypeMatcher;

/**
 * An abstract base implementation for {@link IProject} decorators. Delegates all methods to the decorated
 * {@link IProject}.
 */
@SuppressWarnings("deprecation")
public abstract class AbstractProjectDecorator extends AbstractBaseResourceDecorator implements IProject {

  protected final IProject project;

  public AbstractProjectDecorator(final IProject project) {
    this.project = project;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected IProject resource() {
    return project;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean exists(final IPath path) {
    return resource().exists(path);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IResource findMember(final String path) {
    return resource().findMember(path);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IResource findMember(final String path, final boolean includePhantoms) {
    return resource().findMember(path, includePhantoms);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IResource findMember(final IPath path) {
    return resource().findMember(path);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IResource findMember(final IPath path, final boolean includePhantoms) {
    return resource().findMember(path, includePhantoms);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getDefaultCharset() throws CoreException {
    return resource().getDefaultCharset();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getDefaultCharset(final boolean checkImplicit) throws CoreException {
    return resource().getDefaultCharset(checkImplicit);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IFile getFile(final IPath path) {
    return resource().getFile(path);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IFolder getFolder(final IPath path) {
    return resource().getFolder(path);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IResource[] members() throws CoreException {
    return resource().members();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IResource[] members(final boolean includePhantoms) throws CoreException {
    return resource().members(includePhantoms);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IResource[] members(final int memberFlags) throws CoreException {
    return resource().members(memberFlags);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IFile[] findDeletedMembersWithHistory(final int depth, final IProgressMonitor monitor) throws CoreException {
    return resource().findDeletedMembersWithHistory(depth, monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setDefaultCharset(final String charset) throws CoreException {
    resource().setDefaultCharset(charset);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setDefaultCharset(final String charset, final IProgressMonitor monitor) throws CoreException {
    resource().setDefaultCharset(charset, monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IResourceFilterDescription createFilter(final int type, final FileInfoMatcherDescription matcherDescription, final int updateFlags, final IProgressMonitor monitor) throws CoreException {
    return resource().createFilter(type, matcherDescription, updateFlags, monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IResourceFilterDescription[] getFilters() throws CoreException {
    return resource().getFilters();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void build(final int kind, final String builderName, final Map<String, String> args, final IProgressMonitor monitor) throws CoreException {
    resource().build(kind, builderName, args, monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void build(final int kind, final IProgressMonitor monitor) throws CoreException {
    resource().build(kind, monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void build(final IBuildConfiguration config, final int kind, final IProgressMonitor monitor) throws CoreException {
    resource().build(config, kind, monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void close(final IProgressMonitor monitor) throws CoreException {
    resource().close(monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void create(final IProjectDescription description, final IProgressMonitor monitor) throws CoreException {
    resource().create(description, monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void create(final IProgressMonitor monitor) throws CoreException {
    resource().create(monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void create(final IProjectDescription description, final int updateFlags, final IProgressMonitor monitor) throws CoreException {
    resource().create(description, updateFlags, monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void delete(final boolean deleteContent, final boolean force, final IProgressMonitor monitor) throws CoreException {
    resource().delete(deleteContent, force, monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IBuildConfiguration getActiveBuildConfig() throws CoreException {
    return resource().getActiveBuildConfig();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IBuildConfiguration getBuildConfig(final String configName) throws CoreException {
    return resource().getBuildConfig(configName);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IBuildConfiguration[] getBuildConfigs() throws CoreException {
    return resource().getBuildConfigs();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IContentTypeMatcher getContentTypeMatcher() throws CoreException {
    return resource().getContentTypeMatcher();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IProjectDescription getDescription() throws CoreException {
    return resource().getDescription();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IFile getFile(final String name) {
    return resource().getFile(name);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IFolder getFolder(final String name) {
    return resource().getFolder(name);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IProjectNature getNature(final String natureId) throws CoreException {
    return resource().getNature(natureId);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IPath getPluginWorkingLocation(final IPluginDescriptor plugin) {
    return resource().getPluginWorkingLocation(plugin);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IPath getWorkingLocation(final String id) {
    return resource().getWorkingLocation(id);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IProject[] getReferencedProjects() throws CoreException {
    return resource().getReferencedProjects();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IProject[] getReferencingProjects() {
    return resource().getReferencingProjects();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IBuildConfiguration[] getReferencedBuildConfigs(final String configName, final boolean includeMissing) throws CoreException {
    return resource().getReferencedBuildConfigs(configName, includeMissing);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasBuildConfig(final String configName) throws CoreException {
    return resource().hasBuildConfig(configName);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasNature(final String natureId) throws CoreException {
    return resource().hasNature(natureId);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isNatureEnabled(final String natureId) throws CoreException {
    return resource().isNatureEnabled(natureId);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isOpen() {
    return resource().isOpen();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void loadSnapshot(final int options, final URI snapshotLocation, final IProgressMonitor monitor) throws CoreException {
    resource().loadSnapshot(options, snapshotLocation, monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void move(final IProjectDescription description, final boolean force, final IProgressMonitor monitor) throws CoreException {
    resource().move(description, force, monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void open(final int updateFlags, final IProgressMonitor monitor) throws CoreException {
    resource().open(updateFlags, monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void open(final IProgressMonitor monitor) throws CoreException {
    resource().open(monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void saveSnapshot(final int options, final URI snapshotLocation, final IProgressMonitor monitor) throws CoreException {
    resource().saveSnapshot(options, snapshotLocation, monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setDescription(final IProjectDescription description, final IProgressMonitor monitor) throws CoreException {
    resource().setDescription(description, monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setDescription(final IProjectDescription description, final int updateFlags, final IProgressMonitor monitor) throws CoreException {
    resource().setDescription(description, updateFlags, monitor);
  }

}
