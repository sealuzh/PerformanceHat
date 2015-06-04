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

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import eu.cloudwave.wp5.common.model.Metric;
import eu.cloudwave.wp5.common.model.MetricType;
import eu.cloudwave.wp5.common.model.impl.MetricImpl;
import eu.cloudwave.wp5.common.model.impl.MetricTypeImpl;

/**
 * A DTO for {@link Metric}.
 */
public class MetricDto extends MetricImpl implements Metric {

  // default constructor is required for jackson deserialization
  public MetricDto() {
    super(null, null, null);
  }

  public MetricDto(final MetricType type, final String qualifier, final Number value) {
    super(type, qualifier, value);
  }

  /**
   * {@inheritDoc}
   */
  @JsonDeserialize(as = MetricTypeImpl.class)
  @Override
  public MetricType getType() {
    return super.getType();
  }

}
