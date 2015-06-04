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
package eu.cloudwave.wp5.common.model.impl;

import eu.cloudwave.wp5.common.model.Metric;

/**
 * Abstract base implementation of {@link Metric}.
 * 
 * REMARK: As long as the concrete metric type is implemented as enum, the type-attribute cannot be set in the abstract
 * base class, because the concrete implementation requires the exact type of the enum to be able to convert the String
 * representation back to the enum. Therefore the type attribute has to be set separately in each concrete
 * implementation.
 */
public abstract class AbstractMetric implements Metric {

  private Number value;

  public AbstractMetric(final Number value) {
    this.value = value;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final Number getValue() {
    return value;
  }

}
