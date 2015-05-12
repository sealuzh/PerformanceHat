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
package eu.cloudwave.wp5.feedbackhandler.metricsources;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * A provider for {@link MetricSourceClient}'s.
 */
@Component
public class MetricSourceClientProvider implements ApplicationContextAware {

  private ApplicationContext context;

  /**
   * Create a {@link MetricSourceClient} for the New Relic API.
   * 
   * @return a {@link MetricSourceClient} for the New Relic API.
   */
  public MetricSourceClient getNewRelicClient(final String apiKey) {
    return (MetricSourceClient) context.getBean(MetricSourceClientNames.NEW_RELIC_CLIENT, apiKey);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
    context = applicationContext;
  }

}
