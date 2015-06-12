package eu.cloudwave.wp5.feedback.eclipse.costs.ui.hovers;

import eu.cloudwave.wp5.common.constants.AggregationInterval;
import eu.cloudwave.wp5.common.dto.ApplicationDto;
import eu.cloudwave.wp5.common.dto.costs.AggregatedRequestsDto;

public class CostContextBuilder extends ContextBuilder {

  /**
   * Creates a new builder that can be used in a fluent way
   * 
   * @return {@link CostContextBuilder} to fluently extend the context
   */
  public static CostContextBuilder init() {
    return new CostContextBuilder();
  }

  /**
   * Adds a key/value pair to the context and returns the builder in a fluent style
   * 
   * @param key
   * @param value
   * 
   * @return {@link CostContextBuilder} to fluently extend the context
   */
  public CostContextBuilder add(String key, Object value) {
    super.add(key, value);
    return this;
  }

  /**
   * Add the alternative object to the context if the first object is null
   * 
   * @param key
   * @param value
   * @param alternative
   * 
   * @return {@link CostContextBuilder} to fluently extend the context
   */
  public CostContextBuilder addIfNotNull(String key, Object value, Object alternative) {
    super.addIfNotNull(key, value, alternative);
    return this;
  }

  /**
   * Add object to context if it is not null
   * 
   * @param key
   *          {@link String}
   * @param value
   * 
   * @return {@link CostContextBuilder} to fluently extend the context
   */
  public CostContextBuilder addIfNotNull(String key, Object value) {
    super.addIfNotNull(key, value);
    return this;
  }

  /**
   * Adding time range and aggregation interval to the context
   * 
   * @param timeRangeFrom
   *          {@link String}
   * @param timeRangeTo
   *          {@link String}
   * @param aggregationInterval
   *          {@link AggregationInterval}
   * 
   * @return {@link CostContextBuilder} to fluently extend the context
   */
  public CostContextBuilder setTimeParameters(final String timeRangeFrom, final String timeRangeTo, final AggregationInterval aggregationInterval) {
    context.put("from", timeRangeFrom);
    context.put("to", timeRangeTo);
    context.put("interval", aggregationInterval);
    return this;
  }

  /**
   * Adding an {@link ApplicationDto}
   * 
   * @param app
   * 
   * @return {@link CostContextBuilder} to fluently extend the context
   */
  public CostContextBuilder setApplication(ApplicationDto app) {
    if (app != null) {
      context.put("instances", app.getInstances());
      context.put("maxRequests", app.getMaxRequestsPerInstancePerSecond());
      context.put("pricePerInstance", app.getPricePerInstanceInUSD());
    }
    return this;
  }

  /**
   * Adding requests statistics to the context
   * 
   * @param identifier
   *          {@link String} that is used to build the keys (e.g. overall --> overallMin, overallAvg, etc.)
   * @param requests
   *          {@link AggregatedRequestsDto}
   * 
   * @return {@link CostContextBuilder} to fluently extend the context
   */
  public CostContextBuilder setRequestStats(final String identifier, final AggregatedRequestsDto requests) {
    if (requests != null) {
      // e.g. identifier "overall" leads to the keys "overallMin", "overallAvg", "overallMax"
      context.put(identifier + "Min", requests.getMin());
      context.put(identifier + "Avg", requests.getAvg());
      context.put(identifier + "Max", requests.getMax());
    }
    return this;
  }

}
