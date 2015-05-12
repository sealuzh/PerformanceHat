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

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.CompilationUnit;

import com.google.common.base.Optional;

import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.FeedbackFileImpl;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.FeedbackResourceExtensionFactory;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.FeedbackResourceFactory;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.decorators.AstCompilationUnitDecorator;

/**
 * Implementation of {@link FeedbackJavaFile}. Acts as a decorator for the wrapped {@link IFile}.
 */
public class FeedbackJavaFileImpl extends FeedbackFileImpl implements FeedbackJavaFile {

  private Optional<AstCompilationUnitDecorator> astCompilationUnitDecorator;

  protected FeedbackJavaFileImpl(final IFile file, final FeedbackResourceExtensionFactory extensionFactory, final FeedbackResourceFactory resourceFactory) {
    super(file, extensionFactory, resourceFactory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<? extends ICompilationUnit> getCompilationUnit() {
    return getAstCompilationUnitDecorator();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<CompilationUnit> getAstRoot() {
    if (getAstCompilationUnitDecorator().isPresent()) {
      return Optional.of(getAstCompilationUnitDecorator().get().getAst());
    }
    return Optional.absent();
  }

  private Optional<AstCompilationUnitDecorator> getAstCompilationUnitDecorator() {
    if (astCompilationUnitDecorator == null) {
      astCompilationUnitDecorator = loadJavaCompilationUnit();
    }
    return astCompilationUnitDecorator;
  }

  private Optional<AstCompilationUnitDecorator> loadJavaCompilationUnit() {
    final ICompilationUnit compilationUnit = (ICompilationUnit) JavaCore.create(file);
    if (compilationUnit != null) {
      return Optional.of(AstCompilationUnitDecorator.of(compilationUnit));
    }
    return Optional.absent();
  }

}
