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
package eu.cloudwave.wp5.feedback.eclipse.performance.core.ast;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Statement;

import com.google.common.collect.Lists;

import eu.cloudwave.wp5.common.model.Procedure;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.ast.extensions.AbstractMethodExtension;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.ast.extensions.ClassInstanceCreationExtension;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.ast.extensions.MethodInvocationExtension;

/**
 * An AST visitor to go through bodies of for- and foreach-loops.
 */
public class ForLoopBodyVisitor extends ASTVisitor {

  private List<Procedure> procedures;

  /**
   * Returns a {@link List} of all procedures (methods and constructors) invoked in the body of the given for-statement.
   * 
   * @param forStatement
   *          the {@link ForStatement}
   * @return a {@link List} of all procedures invoked in the body of the given for-statement
   */
  public List<Procedure> getProcedureInvocations(final ForStatement forStatement) {
    return getProcedureInvocationsInternal(forStatement.getBody());
  }

  /**
   * Returns a {@link List} of all procedures (methods and constructors) invoked in the body of the given
   * foreach-statement.
   * 
   * @param foreachStatement
   *          the {@link EnhancedForStatement}
   * @return a {@link List} of all procedures invoked in the body of the given foreach-statement
   */
  public List<Procedure> getProcedureInvocations(final EnhancedForStatement foreachStatement) {
    return getProcedureInvocationsInternal(foreachStatement.getBody());
  }

  private List<Procedure> getProcedureInvocationsInternal(final Statement statement) {
    this.procedures = Lists.newArrayList();
    statement.accept(this);
    return procedures;
  }

  @Override
  public boolean visit(final MethodInvocation node) {
    addProcedure(new MethodInvocationExtension(node));
    return super.visit(node);
  }

  @Override
  public boolean visit(final ClassInstanceCreation node) {
    addProcedure(new ClassInstanceCreationExtension(node));
    return super.visit(node);
  }

  private void addProcedure(final AbstractMethodExtension<?> methodExtension) {
    addProcedure(methodExtension.createCorrelatingProcedure());
  }

  private void addProcedure(final Procedure procedure) {
    this.procedures.add(procedure);
  }

}
