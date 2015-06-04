package eu.cloudwave.wp5.feedbackhandler.aggregations;

import eu.cloudwave.wp5.feedbackhandler.aggregations.strategies.RequestAggregationStrategy;

public class AggregatedClientRequestCollector extends AggregatedRequestCollector {

  public AggregatedClientRequestCollector(RequestCollector requests, RequestAggregationStrategy strategy) {
    super(requests, strategy);
  }

  @Override
  public ClientRequestCollector getCollector() {
    return (ClientRequestCollector) super.getCollector();
  }

  public String getCaller() {
    return this.getCollector().getCaller();
  }

  public String getCallee() {
    return this.getCollector().getCallee();
  }

  public String getCalleeMethod() {
    return this.getCollector().getCalleeMethod();
  }
}
