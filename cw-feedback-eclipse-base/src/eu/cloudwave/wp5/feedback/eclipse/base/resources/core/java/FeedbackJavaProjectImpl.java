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
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import eu.cloudwave.wp5.feedback.eclipse.base.infrastructure.logging.Logger;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.FeedbackProjectImpl;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.FeedbackResourceExtensionFactory;

/**
 * Implementation of {@link FeedbackJavaProject}. Acts as a decorator for the wrapped {@link IJavaProject}.
 */
public class FeedbackJavaProjectImpl extends FeedbackProjectImpl implements FeedbackJavaProject {

  private static final String JAVA_MODEL_EXCEPTION_MSG_PATTERN = "JavaModelException while fetching %s's of a Java project.";
  private static final String PACKAGE_FRAGMENT = "IPackageFragment";
  private static final String COMPILATION_UNIT = "ICompilationUnit";

  private IJavaProject javaProject;

  private final FeedbackJavaResourceFactory feedbackJavaResourceFactory;

  protected FeedbackJavaProjectImpl(final IProject project, final FeedbackResourceExtensionFactory extensionFactory, final FeedbackJavaResourceFactory javaResourceFactory) {
    super(project, extensionFactory);
    this.feedbackJavaResourceFactory = javaResourceFactory;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isJavaNatureEnabled() {
    try {
      return project.isNatureEnabled(JavaCore.NATURE_ID);
    }
    catch (final CoreException e) {
      return false;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IJavaProject getJavaProject() {
    if (javaProject == null) {
      javaProject = JavaCore.create(project);
    }
    return javaProject;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<FeedbackJavaFile> getJavaSourceFiles() {
    final Set<FeedbackJavaFile> javaFiles = Sets.newHashSet();
    for (final IPackageFragment packageFragment : getPackageFragmentsContainingSourceFiles()) {
      javaFiles.addAll(getJavaSourceFiles(packageFragment));
    }
    return ImmutableSet.copyOf(javaFiles);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<FeedbackJavaFile> getJavaSourceFile(final String name) {
    for (final FeedbackJavaFile feedbackJavaFile : getJavaSourceFiles()) {
      if (feedbackJavaFile.getName().equals(name)) {
        return Optional.of(feedbackJavaFile);
      }
    }
    return Optional.absent();
  }

  /**
   * Returns all package fragments of the Java project. Package fragments that only contain binary files are NOT
   * included.
   * 
   * @return a {@link Set} containing all package fragments that contain Java source files
   */
  private Set<IPackageFragment> getPackageFragmentsContainingSourceFiles() {
    final Set<IPackageFragment> packageFragments = Sets.newHashSet();
    if (getJavaProject().exists()) {
      try {
        for (final IPackageFragment packageFragment : getJavaProject().getPackageFragments()) {
          if (packageFragment.getKind() != IPackageFragmentRoot.K_BINARY) {
            packageFragments.add(packageFragment);
          }
        }
      }
      catch (final JavaModelException e) {
        Logger.print(String.format(JAVA_MODEL_EXCEPTION_MSG_PATTERN, PACKAGE_FRAGMENT));
        // do nothing, package fragment is simply not added to the list
      }
    }
    return packageFragments;
  }

  /**
   * Returns all source files of the given package fragment. The sources files are of type {@link ICompilationUnit}.
   * Binary files are NOT included.
   * 
   * @param packageFragment
   *          the package fragment
   * @return a {@link Set} containing all {@link ICompilationUnit}'s (i.e. Java source files)
   */
  private Set<FeedbackJavaFile> getJavaSourceFiles(final IPackageFragment packageFragment) {
    final Set<FeedbackJavaFile> javaSourceFiles = Sets.newHashSet();
    try {
      for (final ICompilationUnit compilationUnit : packageFragment.getCompilationUnits()) {
        final IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(compilationUnit.getPath());
        final Optional<? extends FeedbackJavaFile> javaFileOptional = this.feedbackJavaResourceFactory.create(file);
        if (javaFileOptional.isPresent()) {
          javaSourceFiles.add(javaFileOptional.get());
        }
      }
    }
    catch (final JavaModelException e) {
      Logger.print(String.format(JAVA_MODEL_EXCEPTION_MSG_PATTERN, COMPILATION_UNIT));
      // do nothing, source file is simply not added to the list
    }
    return javaSourceFiles;
  }
}
