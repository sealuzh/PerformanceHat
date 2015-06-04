package eu.cloudwave.wp5.feedbackhandler.aggregations;

import eu.cloudwave.wp5.feedbackhandler.aggregations.strategies.RequestAggregationStrategy;

public class AggregatedIncomingRequestCollector extends AggregatedRequestCollector {

  public AggregatedIncomingRequestCollector(RequestCollector requests, RequestAggregationStrategy strategy) {
    super(requests, strategy);
  }

  @Override
  public IncomingRequestCollector getCollector() {
    return (IncomingRequestCollector) super.getCollector();
  }

  public String getIdentifier() {
    return this.getCollector().getIdentifier();
  }

  public String getMethod() {
    return this.getCollector().getMethod();
  }

}
