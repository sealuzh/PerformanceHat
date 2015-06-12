/*******************************************************************************
 * Copyright 2015 Software Evolution and Architecture Lab, University of Zurich
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 ******************************************************************************/
package eu.cloudwave.wp5.common.model.impl;

import eu.cloudwave.wp5.common.model.Metric;
import eu.cloudwave.wp5.common.model.Prediction;
import eu.cloudwave.wp5.common.model.PredictionType;

/**
 * Default implementation of {@link Metric}.
 */
public class PredictionImpl implements Prediction {

  private PredictionType type;

  private Number value;

  public PredictionImpl(final PredictionType type, final Number value) {
    this.type = type;
    this.value = value;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public PredictionType getType() {
    return type;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Number getValue() {
    return value;
  }

}
