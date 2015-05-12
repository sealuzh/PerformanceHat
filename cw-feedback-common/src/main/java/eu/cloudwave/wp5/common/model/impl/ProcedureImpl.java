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
package eu.cloudwave.wp5.common.model.impl;

import java.util.List;

import eu.cloudwave.wp5.common.model.Annotation;
import eu.cloudwave.wp5.common.model.Procedure;
import eu.cloudwave.wp5.common.model.ProcedureKind;

public class ProcedureImpl extends AbstractProcedure implements Procedure {

  private String className;
  private String name;
  private ProcedureKind kind;
  private List<String> arguments;
  private List<Annotation> annotations;

  public ProcedureImpl(final String className, final String name, final ProcedureKind kind, final List<String> arguments, final List<Annotation> annotations) {
    this.className = className;
    this.name = name;
    this.kind = kind;
    this.arguments = arguments;
    this.annotations = annotations;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getClassName() {
    return className;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<String> getArguments() {
    return arguments;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ProcedureKind getKind() {
    return kind;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Annotation> getAnnotations() {
    return this.annotations;
  }
}
