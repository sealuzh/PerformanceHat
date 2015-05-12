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

import eu.cloudwave.wp5.common.model.MetricType;

/**
 * Implementation of {@link MetricType}.
 * 
 * Currently this is implemented as an enum and the available metric types are therefore hardcoded (enum) constants. A
 * cleaner solution would be to store the available metrics in the database. This would make it much more easier to add
 * new metric types. The disadvantage is, that the monitoring component as well as the eclipse client would have to make
 * a call to the feedback handler in order to fetch the available metrics (or the metrics are replicated to all three
 * components).
 * 
 * Due to the fact that adding metrics is not a frequent action, the current implementation has been chosen for
 * simplicity reasons.
 */
public enum MetricTypeImpl implements MetricType {

  EXECUTION_TIME("executionTime", "ms"),
  CPU_USAGE("cpuUsage", "%"),
  COLLECTION_SIZE("collectionSize", "");

  private String name;
  private String unit;

  private MetricTypeImpl(final String name, final String unit) {
    this.name = name;
    this.unit = unit;
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
  public String getUnit() {
    return unit;
  }

}
