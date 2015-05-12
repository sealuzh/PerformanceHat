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
package eu.cloudwave.wp5.feedback.eclipse.base.ui.hovers.contentprovider;

import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.FeedbackMarkerType;

/**
 * This class binds each marker type to the respective {@link FeedbackInformationControlContentProvider}.
 */
public interface FeedbackInformationControlContentProviderRegistry {

  public static FeedbackInformationControlContentProviderRegistry INSTANCE = new FeedbackInformationControlContentProviderRegistryImpl();

  /**
   * Returns the corresponding {@link FeedbackInformationControlContentProvider} for the given
   * {@link PerformanceMarkerType}
   * 
   * @param type
   *          the type of the marker
   * @return the corresponding {@link FeedbackInformationControlContentProvider} for the given
   *         {@link PerformanceMarkerType}
   */
  public FeedbackInformationControlContentProvider get(FeedbackMarkerType type);

  /**
   * Register a new mapping between {@link PerformanceMarkerType} and {@link FeedbackInformationControlContentProvider}.
   * Supposed to be called in the concrete plugins.
   * 
   * @param type
   *          the type of the marker
   * @param contentProvider
   *          the corresponding {@link FeedbackInformationControlContentProvider} for the given
   *          {@link PerformanceMarkerType}
   */
  public void register(FeedbackMarkerType type, FeedbackInformationControlContentProvider contentProvider);

}
