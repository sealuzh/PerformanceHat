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
package eu.cloudwave.wp5.monitoring.aop.handlers;

import org.aspectj.lang.ProceedingJoinPoint;

import eu.cloudwave.wp5.monitoring.aop.joinpoints.ProcedureCallJoinPoint;

/**
 * A template for around-join-point handlers. Is responsible for the sequence and provides hook-methods to specify the
 * code that should be executed before and after the actual method. Additionally there are some small utility methods
 * that can be used by subclasses.
 */
public abstract class AbstractAroundJoinPointHandlerTemplate {

  /**
   * The {@link #execute(ProceedingJoinPoint)} method should be called inside aspects to execute the
   * {@link AbstractAroundJoinPointHandlerTemplate}. It first executes the {@link #before(ProcedureCallJoinPoint)}
   * method, then executes the actual procedure, executes the {@link #after(ProcedureCallJoinPoint, Object)} method and
   * finally returns the result of the actual procedure.
   * 
   * @param joinPoint
   *          the {@link ProceedingJoinPoint}
   * @return the result of the actual procedure
   */
  public final Object execute(final ProceedingJoinPoint joinPoint) {
    final ProcedureCallJoinPoint procedureCallJoinPoint = new ProcedureCallJoinPoint(joinPoint);
    before(procedureCallJoinPoint);
    try {
      final Object result = joinPoint.proceed();
      after(procedureCallJoinPoint, result);
      return result;
    }
    catch (final Throwable e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * The code that is executed before the actual method.
   * 
   * @param joinPoint
   *          the {@link ProcedureCallJoinPoint} wrapping the actual {@link ProceedingJoinPoint}
   */
  protected abstract void before(ProcedureCallJoinPoint joinPoint);

  /**
   * The code that is executed after the actual method.
   * 
   * @param joinPoint
   *          the {@link ProcedureCallJoinPoint} wrapping the actual {@link ProceedingJoinPoint}
   * @param result
   *          the result of the procedure execution. If the procedure is a constructor, the result is the created
   *          object.
   */
  protected abstract void after(ProcedureCallJoinPoint joinPoint, Object result);

}
