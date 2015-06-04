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
package eu.cloudwave.wp5.feedbackhandler.cw.model;

/**
 * Implementation of {@link CwMetric}.
 */
public class CwMetricImpl implements CwMetric {

  private String name;
  private String data;
  private String unit;
  private Number value;

  private CwMetricImpl(final String name, final String data, final String unit, final Number value) {
    this.name = name;
    this.data = data;
    this.unit = unit;
    this.value = value;
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
  public String getData() {
    return data;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getUnit() {
    return unit;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Number getValue() {
    return value;
  }

  public static CwMetric of(final String name, final String data, final String unit, final Number value) {
    return new CwMetricImpl(name, data, unit, value);
  }

}
