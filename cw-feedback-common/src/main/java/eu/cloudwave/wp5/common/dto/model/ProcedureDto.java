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
package eu.cloudwave.wp5.common.dto.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.collect.Lists;

import eu.cloudwave.wp5.common.model.Annotation;
import eu.cloudwave.wp5.common.model.Procedure;
import eu.cloudwave.wp5.common.model.ProcedureKind;
import eu.cloudwave.wp5.common.model.impl.ProcedureImpl;

/**
 * A DTO for {@link Procedure}.
 */
public class ProcedureDto extends ProcedureImpl implements Procedure {

  // default constructor is required for jackson deserialization
  public ProcedureDto() {
    this(null, null, null, Lists.newArrayList(), Lists.newArrayList());
  }

  public ProcedureDto(final String className, final String name, final ProcedureKind kind, final List<String> arguments, final List<Annotation> annotations) {
    super(className, name, kind, arguments, annotations);
  }

  /**
   * {@inheritDoc}
   */
  @JsonIgnore
  @Override
  public String getQualifier() {
    return super.getQualifier();
  }

  @Override
  @JsonDeserialize(contentAs = AnnotationDto.class)
  public List<Annotation> getAnnotations() {
    return super.getAnnotations();
  }
}
