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
package eu.cloudwave.wp5.feedback.eclipse.costs.core;

/**
 * As practiced in the performance FDD Plugin, this file holds all ID's that are referenced in the Java code. The reason
 * is that ID's from the plugin.xml-file cannot be directly referenced in source files. Aggregating all the ID's here
 * prohibits them from being spread over multiple source code files.
 */
public class CostIds {

  private static final String DOT = "."; //$NON-NLS-1$

  /**
   * The plug-in ID.
   */
  public static final String PLUGIN = "eu.cloudwave.wp5.feedback.eclipse.costs"; //$NON-NLS-1$

  public static final String COST_MARKER = PLUGIN + DOT + "markers.CostMarker";

  /*
   * Core
   */
  private static final String CORE_QUALIFIER = PLUGIN + DOT + "core" + DOT; //$NON-NLS-1$

  public static final String NATURE = CORE_QUALIFIER + "natures.CostNature"; //$NON-NLS-1$

  public static final String BUILDER = CORE_QUALIFIER + "builders.CostBuilder"; //$NON-NLS-1$

  private CostIds() {} // prevents instantiation

}
