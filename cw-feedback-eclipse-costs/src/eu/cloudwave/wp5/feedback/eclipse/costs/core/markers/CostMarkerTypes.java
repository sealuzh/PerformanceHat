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
package eu.cloudwave.wp5.feedback.eclipse.costs.core.markers;

import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.FeedbackMarkerType;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.FeedbackMarkerTypeImpl;

/**
 * The different types of markers. The feedback marker defined in the base project plugin.xml has an attribute 'type'
 * that specifies its type. This property stores the string representation of {@link CostMarkerTypes}.
 */
public class CostMarkerTypes {

  public static FeedbackMarkerType METHOD_DECLARATION = new FeedbackMarkerTypeImpl("microserviceMethodDeclaration");
  public static FeedbackMarkerType CLIENT_INVOCATION = new FeedbackMarkerTypeImpl("microserviceClientInvocation");

  private CostMarkerTypes() {}
}
