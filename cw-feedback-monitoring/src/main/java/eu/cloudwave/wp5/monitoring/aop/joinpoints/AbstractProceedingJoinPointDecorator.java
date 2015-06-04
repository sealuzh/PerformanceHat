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
package eu.cloudwave.wp5.monitoring.aop.joinpoints;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.SourceLocation;
import org.aspectj.runtime.internal.AroundClosure;

/**
 * An abstract base implementation for decorators for {@link ProceedingJoinPoint}'s. Simply delegates all methods to the
 * decorated {@link ProceedingJoinPoint}.
 */
public class AbstractProceedingJoinPointDecorator implements ProceedingJoinPoint {

  private ProceedingJoinPoint joinPoint;

  public AbstractProceedingJoinPointDecorator(final ProceedingJoinPoint joinPoint) {
    this.joinPoint = joinPoint;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object[] getArgs() {
    return joinPoint.getArgs();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getKind() {
    return joinPoint.getKind();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Signature getSignature() {
    return joinPoint.getSignature();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SourceLocation getSourceLocation() {
    return joinPoint.getSourceLocation();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public StaticPart getStaticPart() {
    return joinPoint.getStaticPart();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object getTarget() {
    return joinPoint.getTarget();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object getThis() {
    return joinPoint.getThis();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object proceed() throws Throwable {
    return joinPoint.proceed();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object proceed(final Object[] arg0) throws Throwable {
    return joinPoint.proceed(arg0);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void set$AroundClosure(final AroundClosure arg0) {
    joinPoint.set$AroundClosure(arg0);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toLongString() {
    return joinPoint.toLongString();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toShortString() {
    return joinPoint.toShortString();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return joinPoint.toString();
  }

}
