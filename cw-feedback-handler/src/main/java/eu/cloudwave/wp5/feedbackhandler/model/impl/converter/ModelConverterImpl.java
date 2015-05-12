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
package eu.cloudwave.wp5.feedbackhandler.model.impl.converter;

import org.springframework.stereotype.Service;

import eu.cloudwave.wp5.common.model.Procedure;
import eu.cloudwave.wp5.common.model.impl.ProcedureImpl;

/**
 * Implementation of {@link ModelConverter}.
 */
@Service
public class ModelConverterImpl implements ModelConverter {

  /**
   * {@inheritDoc}
   */
  @Override
  public ProcedureImpl convert(final Procedure procedure) {
    return new ProcedureImpl(procedure.getClassName(), procedure.getName(), procedure.getKind(), procedure.getArguments(), procedure.getAnnotations());
  }

}
