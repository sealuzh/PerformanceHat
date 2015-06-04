package eu.cloudwave.wp5.feedbackhandler.aggregations;

import java.util.List;

public interface RequestCollector {

  /**
   * All request timestamps in milliseconds
   * 
   * @return returns a list of {@link Long}
   */
  public List<Long> getReqTimestamps();
}
