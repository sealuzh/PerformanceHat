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
package eu.cloudwave.wp5.feedbackhandler.cw.converter;

import org.springframework.stereotype.Service;

import eu.cloudwave.wp5.common.model.Metric;
import eu.cloudwave.wp5.feedbackhandler.cw.model.CwMetric;
import eu.cloudwave.wp5.feedbackhandler.cw.model.CwMetricImpl;

/**
 * Implementation of {@link CwMetricConverter}.
 */
@Service
public class CwMetricConverterImpl implements CwMetricConverter {

  /**
   * {@inheritDoc}
   */
  @Override
  public CwMetric convert(final Metric metric) {
    return CwMetricImpl.of(metric.getType().getName(), metric.getQualifier(), metric.getType().getUnit(), metric.getValue());
  }
}
