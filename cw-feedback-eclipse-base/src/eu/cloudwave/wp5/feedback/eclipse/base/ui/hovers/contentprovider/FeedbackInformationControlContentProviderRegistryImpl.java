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
package eu.cloudwave.wp5.feedback.eclipse.base.ui.hovers.contentprovider;

import java.util.Map;

import com.google.common.collect.Maps;

import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.FeedbackMarkerType;

/**
 * Implementation of {@link FeedbackInformationControlContentProviderRegistry}.
 */
public class FeedbackInformationControlContentProviderRegistryImpl implements FeedbackInformationControlContentProviderRegistry {

  private Map<FeedbackMarkerType, FeedbackInformationControlContentProvider> registry;

  public FeedbackInformationControlContentProviderRegistryImpl() {
    registry = Maps.newHashMap();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public FeedbackInformationControlContentProvider get(final FeedbackMarkerType type) {
    return registry.get(type);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void register(FeedbackMarkerType type, FeedbackInformationControlContentProvider contentProvider) {
    registry.put(type, contentProvider);
  }

}
