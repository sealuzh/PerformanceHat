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
package eu.cloudwave.wp5.feedback.eclipse.performance.core.ast.extensions;

import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;

/**
 * Provides complementary functionality for {@link MethodDeclaration}'s. It wraps a {@link MethodDeclaration} similar to
 * a decorator but does not implement the same interface.
 */
public class MethodDeclarationExtension extends AbstractMethodExtension<MethodDeclaration> {

  private final MethodDeclaration methodDeclaration;

  public MethodDeclarationExtension(final MethodDeclaration methodDeclaration) {
    this.methodDeclaration = methodDeclaration;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public MethodDeclaration get() {
    return methodDeclaration;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getStartPosition() {
    return methodDeclaration.getName().getStartPosition();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getEndPosition() {
    return getStartPosition() + methodDeclaration.getName().getLength();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IMethodBinding getMethodBinding() {
    return methodDeclaration.resolveBinding().getMethodDeclaration();
  }
}
