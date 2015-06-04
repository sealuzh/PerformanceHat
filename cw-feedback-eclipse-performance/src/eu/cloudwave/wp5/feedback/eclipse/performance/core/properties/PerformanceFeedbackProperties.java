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
package eu.cloudwave.wp5.feedback.eclipse.performance.core.properties;

import eu.cloudwave.wp5.feedback.eclipse.base.core.BaseIds;

/**
 * Contains keys of (project) properties related to the feedback plugin.
 */
public class PerformanceFeedbackProperties {

  private static final String DOT = "."; //$NON-NLS-1$
  private static final String QUALIFIER = BaseIds.ID + DOT;

  private static final String TRESHOLD_HANDLER_QUALIFIER = QUALIFIER + "thresholds" + DOT; //$NON-NLS-1$
  public static final String TRESHOLD__HOTSPOTS = TRESHOLD_HANDLER_QUALIFIER + "hotspots"; //$NON-NLS-1$
  public static final String TRESHOLD__LOOPS = TRESHOLD_HANDLER_QUALIFIER + "loops"; //$NON-NLS-1$

  private static final String NEW_RELIC_QUALIFIER = QUALIFIER + "newRelic" + DOT; //$NON-NLS-1$
  public static final String NEW_RELIC__API_KEY = NEW_RELIC_QUALIFIER + "apiKey"; //$NON-NLS-1$
  public static final String NEW_RELIC__APPLICATION_ID = NEW_RELIC_QUALIFIER + "applicationId"; //$NON-NLS-1$

}
