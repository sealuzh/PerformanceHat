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
import org.eclipse.jdt.core.dom.CompilationUnit;

import com.google.common.base.Optional;

import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.FeedbackFile;

/**
 * Extends a {@link FeedbackFile} with Java related functionality.
 */
public interface FeedbackJavaFile extends FeedbackFile {

  /**
   * Returns the respective {@link ICompilationUnit} if the {@link IFile} is a Java source or binary file.
   * 
   * @return the respective {@link ICompilationUnit} of the current {@link IFile}
   */
  public Optional<? extends ICompilationUnit> getCompilationUnit();

  /**
   * Returns the root node of the Abstract Syntax Tree (AST) if the {@link IFile} is a Java source file.
   * 
   * @return the root AST node which is of type {@link CompilationUnit}
   */
  public Optional<CompilationUnit> getAstRoot();

}
