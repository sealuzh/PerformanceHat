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
package eu.cloudwave.wp5.feedback.eclipse.tests.fixtures.base;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IPluginDescriptor;
import org.eclipse.core.runtime.IPluginRegistry;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.IJavaSearchConstants;
import org.eclipse.jdt.core.search.ITypeNameRequestor;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.launching.JavaRuntime;

import com.google.common.io.CharStreams;

/**
 * This class acts as a fixture for projects in the junit-workspace.
 * 
 * It provides functionality to create a Java project and fill it with packages and types.
 * 
 * The code comes from the book 'Contributing to Eclipse: Principles, Patterns, and Plug-Ins' by Erich Gamma and Kent
 * Beck and has been adapted/extended.
 */
@SuppressWarnings("deprecation")
public class JavaProjectFixture {

  private static final String UTF_8 = "UTF-8";
  private static final String BIN_FOLDER = "bin";

  private IProject project;
  private IJavaProject javaProject;
  private IPackageFragmentRoot sourceFolder;

  public JavaProjectFixture(final String name) throws CoreException {
    final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
    project = root.getProject(name);
    project.create(null);
    project.open(null);
    javaProject = JavaCore.create(project);
    final IFolder binFolder = createBinFolder();
    addJavaNature();
    javaProject.setRawClasspath(new IClasspathEntry[0], null);
    createOutputFolder(binFolder);
    addSystemLibraries();
  }

  public IProject getProject() {
    return project;
  }

  public String getName() {
    return project.getName();
  }

  public IJavaProject getJavaProject() {
    return javaProject;
  }

  /**
   * Adds a jar-File from a plugin deployed with the workbench to the project.
   * 
   * @param plugin
   *          the id of the plugin that contains the jar-File
   * @param name
   *          the name of the jar-File
   */
  public void addJar(final String plugin, final String name) throws MalformedURLException, IOException, JavaModelException {
    addJar(findFileInPlugin(plugin, name));
  }

  /**
   * Adds a jar-File from a specified path to the project.
   * 
   * @param path
   *          the path to the jar-File (can be either relative to the project root or absolute)
   */
  public void addJar(final String path) throws MalformedURLException, IOException, JavaModelException {
    addJar(new Path(getAbsolutePath(path)));
  }

  private String getAbsolutePath(final String path) {
    return new File(path).getAbsolutePath();
  }

  /**
   * Adds a jar-File from a specified path to the project.
   * 
   * @param path
   *          the path to the jar-File
   */
  public void addJar(final Path path) throws MalformedURLException, IOException, JavaModelException {
    final IClasspathEntry[] oldEntries = javaProject.getRawClasspath();
    final IClasspathEntry[] newEntries = new IClasspathEntry[oldEntries.length + 1];
    System.arraycopy(oldEntries, 0, newEntries, 0, oldEntries.length);
    newEntries[oldEntries.length] = JavaCore.newLibraryEntry(path, null, null);
    javaProject.setRawClasspath(newEntries, null);
  }

  /**
   * Creates a package fragment.
   * 
   * @param name
   *          the name of the package fragment
   * @return the created {@link IPackageFragment}
   */
  public IPackageFragment createPackage(final String name) throws CoreException {
    if (sourceFolder == null)
      sourceFolder = createSourceFolder();
    return sourceFolder.createPackageFragment(name, false, null);
  }

  /**
   * Creates a Java type (i.e. a class or an interface).
   * 
   * @param packageFragment
   *          the package to which the type should be added
   * @param name
   *          the name of the type
   * @param content
   *          the source content of the type
   * @return the created {@link IType}
   */
  public IType createTypeFromContent(final IPackageFragment packageFragment, final String name, final String content) throws JavaModelException {
    final ICompilationUnit compilationUnit = packageFragment.createCompilationUnit(name, content, false, null);
    return compilationUnit.getTypes()[0];
  }

  /**
   * Creates a Java type (i.e. a class or an interface).
   * 
   * @param packageFragment
   *          the package to which the type should be added
   * @param name
   *          the name of the type
   * @param filePath
   *          the path to the file that contains the content for the type
   * @return the created {@link IType}
   */
  public IType createTypeFromFile(final IPackageFragment packageFragment, final String name, final String filePath) throws JavaModelException, UnsupportedEncodingException, IOException {
    return createTypeFromContent(packageFragment, name, getResourceAsString(filePath));
  }

  private String getResourceAsString(final String path) throws UnsupportedEncodingException, IOException {
    final InputStream is = getClass().getResourceAsStream(path);
    return CharStreams.toString(new InputStreamReader(is, UTF_8));
  }

  /**
   * Disppses the project.
   */
  public void dispose() throws CoreException {
    waitForIndexer();
    project.delete(true, true, null);
  }

  private IFolder createBinFolder() throws CoreException {
    final IFolder binFolder = project.getFolder(BIN_FOLDER);
    binFolder.create(false, true, null);
    return binFolder;
  }

  private void addJavaNature() throws CoreException {
    final IProjectDescription description = project.getDescription();
    description.setNatureIds(new String[] { JavaCore.NATURE_ID });
    project.setDescription(description, null);
  }

  private void createOutputFolder(final IFolder binFolder) throws JavaModelException {
    final IPath outputLocation = binFolder.getFullPath();
    javaProject.setOutputLocation(outputLocation, null);
  }

  private IPackageFragmentRoot createSourceFolder() throws CoreException {
    final IFolder folder = project.getFolder("src");
    folder.create(false, true, null);
    final IPackageFragmentRoot root = javaProject.getPackageFragmentRoot(folder);
    final IClasspathEntry[] oldEntries = javaProject.getRawClasspath();
    final IClasspathEntry[] newEntries = new IClasspathEntry[oldEntries.length + 1];
    System.arraycopy(oldEntries, 0, newEntries, 0, oldEntries.length);
    newEntries[oldEntries.length] = JavaCore.newSourceEntry(root.getPath());
    javaProject.setRawClasspath(newEntries, null);
    return root;
  }

  private void addSystemLibraries() throws JavaModelException {
    final IClasspathEntry[] oldEntries = javaProject.getRawClasspath();
    final IClasspathEntry[] newEntries = new IClasspathEntry[oldEntries.length + 1];
    System.arraycopy(oldEntries, 0, newEntries, 0, oldEntries.length);
    newEntries[oldEntries.length] = JavaRuntime.getDefaultJREContainerEntry();
    javaProject.setRawClasspath(newEntries, null);
  }

  private Path findFileInPlugin(final String plugin, final String file) throws MalformedURLException, IOException {
    final IPluginRegistry registry = Platform.getPluginRegistry();
    final IPluginDescriptor descriptor = registry.getPluginDescriptor(plugin);
    final URL pluginURL = descriptor.getInstallURL();
    final URL jarURL = new URL(pluginURL, file);
    final URL localJarURL = Platform.asLocalURL(jarURL);
    return new Path(localJarURL.getPath());
  }

  private void waitForIndexer() throws JavaModelException {
    new SearchEngine().searchAllTypeNames(ResourcesPlugin.getWorkspace(), null, null, IJavaSearchConstants.EXACT_MATCH, IJavaSearchConstants.CASE_SENSITIVE, IJavaSearchConstants.CLASS,
        SearchEngine.createJavaSearchScope(new IJavaElement[0]), new ITypeNameRequestor() {
          @Override
          public void acceptClass(final char[] packageName, final char[] simpleTypeName, final char[][] enclosingTypeNames, final String path) {}

          @Override
          public void acceptInterface(final char[] packageName, final char[] simpleTypeName, final char[][] enclosingTypeNames, final String path) {}
        }, IJavaSearchConstants.WAIT_UNTIL_READY_TO_SEARCH, null);
  }

}
