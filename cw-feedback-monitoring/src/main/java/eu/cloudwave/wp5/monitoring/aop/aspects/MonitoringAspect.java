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
package eu.cloudwave.wp5.monitoring.aop.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import eu.cloudwave.wp5.monitoring.tracing.TracingHandler;

/**
 * This class holds all pointcuts and advices used for the monitoring.
 */
@Aspect
public class MonitoringAspect {

  // /**
  // * A pointcut targeting the execution of all methods annotated with the trace annotation.
  // */
  // @Pointcut("execution(@eu.cloudwave.wp5.monitoring.annotations.Trace * *(..))")
  // public void traceAnnotatedMethods() {}

  /**
   * A pointcut targeting all method calls.
   */
  @Pointcut("call(* *.*(..))")
  public void allMethodCalls() {}

  /**
   * A pointcut targeting all constructor calls.
   */
  @Pointcut("call(*.new(..))")
  public void allConstructorCalls() {}

  /**
   * A pointcut targeting all procedures inside the package 'eu.cloudwave.wp5.monitoring' and its sub-packages.
   */
  @Pointcut("within(eu.cloudwave.wp5.monitoring..*)")
  public void allMonitoringPackages() {}

  /**
   * An advice targeting all method and constructor calls outside the monitoring packages.
   * 
   * @param joinPoint
   *          the {@link ProceedingJoinPoint}
   * @return the result of the method/constructor execution
   */
  @Around("(allMethodCalls() || allConstructorCalls()) && !allMonitoringPackages()")
  public Object aroundProcedureCalls(final ProceedingJoinPoint joinPoint) {
    return TracingHandler.of().execute(joinPoint);
  }

}
