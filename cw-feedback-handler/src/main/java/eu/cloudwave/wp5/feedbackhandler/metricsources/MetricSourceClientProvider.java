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
