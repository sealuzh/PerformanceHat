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
package eu.cloudwave.wp5.feedbackhandler.aggregations;

import com.google.common.collect.Lists;

import eu.cloudwave.wp5.common.model.Procedure;
import eu.cloudwave.wp5.common.model.ProcedureKind;
import eu.cloudwave.wp5.common.model.impl.ProcedureImpl;

/**
 * Result item of an aggregation of procedure metrics.
 */
public class ProcedureMetricAggregation {

  private String className;
  private String name;
  private String kind;
  private String[] arguments;

  private double averageValue;

  public ProcedureMetricAggregation(final String className, final String name, final String kind, final String[] arguments, final double averageValue) {
    this.className = className;
    this.name = name;
    this.kind = kind;
    this.arguments = arguments;
    this.averageValue = averageValue;
  }

  public String getClassName() {
    return className;
  }

  public String getName() {
    return name;
  }

  public String getKind() {
    return kind;
  }

  public String[] getArguments() {
    return arguments;
  }

  public double getAverageValue() {
    return averageValue;
  }

  public Procedure getProcedure() {
    return new ProcedureImpl(className, name, ProcedureKind.of(kind), Lists.newArrayList(arguments), Lists.newArrayList());
  }

}
