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
package eu.cloudwave.wp5.feedback.eclipse.performance.core.ast;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.VariableDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import com.google.common.base.Optional;

import eu.cloudwave.wp5.common.model.Procedure;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.ast.extensions.MethodDeclarationExtension;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.ast.extensions.MethodInvocationExtension;

/**
 * Provides functionality to determine the source of a collection. The source is either a parameter of a procedure or a
 * method invocation inside a field/variable assignment.
 */
public class CollectionSourceDetector {

  /**
   * Determines the source of the collection that is iterated in the given foreach-loop. The source is either a
   * parameter or a return value of a procedure (method or constructor). This information is required to fetch
   * information about the average size of the collection from the feedback handler.
   * 
   * @param foreachStatement
   * @param methodDeclaration
   * @return
   */
  public final Optional<CollectionSource> getSource(final EnhancedForStatement foreachStatement, final MethodDeclaration methodDeclaration) {
    final Expression expression = foreachStatement.getExpression();

    // Case 1: the for loop contains a variable; 'for(Object item : items)'
    if (expression instanceof SimpleName) {
      final String variableName = expression.toString();
      // Case 1a: the variable of the for loop is a method parameter of the containing method
      final Optional<Integer> position = getParameterPosition(variableName, methodDeclaration);
      if (position.isPresent()) {
        return Optional.of(new CollectionSource(new MethodDeclarationExtension(methodDeclaration).createCorrelatingProcedure(), position.get()));
      }
      // Case 1b: the variable of the for-loop is locally defined inside the method
      final Optional<Procedure> procedure = getLocalVariableProcedureAssignment(variableName, methodDeclaration, foreachStatement.getStartPosition());
      if (procedure.isPresent()) {
        return Optional.of(new CollectionSource(procedure.get()));
      }
      // Case 1c: the variable is a field of the containing class
      // TODO enhancement: Support cases where the collection is a class field (if possible).
      // This case is tricky with SCA, because you don't know which code of the class already has been executed.
      // Therefore one can not simply search for assignments of the class field.
      // A solution could be to look at all the call traces that are executed when the loop is executed. This might help
      // to find out which code is executed and then in combination with SCA to find the place where the collection is
      // set. But this would require many calls to the Feedback handler which could make the build very slow.
    }
    // Case 2: the for loop contains a method invocation; example: 'for(Object item : getItems())'
    else if (expression instanceof MethodInvocation) {
      final MethodInvocationExtension methodInvocationExtension = new MethodInvocationExtension((MethodInvocation) expression);
      return Optional.of(new CollectionSource(methodInvocationExtension.createCorrelatingProcedure()));
    }
    return Optional.absent();
  }

  /**
   * Checks if the given {@link SimpleName} is a parameter of the given {@link MethodDeclaration}. If this is the case,
   * the position of the parameter (in the method signature) is returned.
   * 
   * @param simpleName
   *          the {@link SimpleName}
   * @param methodDeclaration
   *          the {@link MethodDeclaration}
   * @return the position of the parameter (in the method signature) if the {@link SimpleName} is a parameter of the
   *         given {@link MethodDeclaration}, {@link Optional#absent()} otherwise
   */
  private Optional<Integer> getParameterPosition(final String variableName, final MethodDeclaration methodDeclaration) {
    int counter = 0;
    for (final Object parameter : methodDeclaration.parameters()) {
      if (parameter instanceof VariableDeclaration) { // should always be the case
        if (((VariableDeclaration) parameter).getName().toString().equals(variableName)) {
          return Optional.of(counter);
        }
      }
      counter++;
    }
    return Optional.absent();
  }

  private Optional<Procedure> getLocalVariableProcedureAssignment(final String variableName, final MethodDeclaration methodDeclaration, final int maxPosition) {
    final ProcedureWrapper procedureBucket = new ProcedureWrapper();

    methodDeclaration.getBody().accept(new ASTVisitor() {

      @Override
      public boolean visit(final VariableDeclarationFragment node) {
        if (node.getStartPosition() < maxPosition && node.getName().toString().equals(variableName)) {
          setSourceProcedure(node.getInitializer());
        }
        return false;
      }

      @Override
      public boolean visit(final Assignment node) {
        if (node.getStartPosition() < maxPosition && node.getLeftHandSide().toString().equals(variableName)) {
          setSourceProcedure(node.getRightHandSide());
        }
        return false;
      }

      private void setSourceProcedure(final Expression expression) {
        if (expression != null && expression instanceof MethodInvocation) {
          final MethodInvocation methodInvocation = ((MethodInvocation) expression);
          procedureBucket.set(new MethodInvocationExtension(methodInvocation).createCorrelatingProcedure());
        }
      }
    });

    return procedureBucket.get();
  }

  /**
   * Holds the information about the source of a collection.
   */
  public static class CollectionSource {
    private static final String EMPTY = "";

    private Procedure procedure;
    private String position;

    public CollectionSource(final Procedure procedure) {
      this.procedure = procedure;
      this.position = EMPTY;
    }

    public CollectionSource(final Procedure procedure, final int position) {
      this.procedure = procedure;
      this.position = String.valueOf(position);
    }

    public Procedure getProcedure() {
      return procedure;
    }

    public String getPosition() {
      return position;
    }
  }

  /**
   * A wrapper to store a {@link Procedure}. It is used to set the procedure from within an inner class. The reason is
   * that inner classes can only access final-fields. Therefor a field of type {@link Procedure} cannot directly be set.
   */
  private static class ProcedureWrapper {
    private Procedure procedure;

    public ProcedureWrapper() {
      this.procedure = null;
    }

    public void set(final Procedure procedure) {
      this.procedure = procedure;
    }

    public Optional<Procedure> get() {
      if (procedure != null) {
        return Optional.of(procedure);
      }
      return Optional.absent();
    }
  }

}
