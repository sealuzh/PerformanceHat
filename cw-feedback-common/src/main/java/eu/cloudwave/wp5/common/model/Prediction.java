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
package eu.cloudwave.wp5.common.model;

/**
 * Any kind of predicted value, for example the cost plugin uses the {@link Prediction} interface to abstract cost
 * predictions.
 */
public interface Prediction {

  /**
   * Returns the type of the {@link Prediction}.
   * 
   * @return the type of the {@link Prediction}
   */
  public PredictionType getType();

  /**
   * Returns the value of the {@link Prediction}. The value is any numerical value given in the unit of the respective
   * {@link PredictionType} (see {@link #getType()}.
   * 
   * @return the value of the {@link Prediction}
   */
  public Number getValue();

}
