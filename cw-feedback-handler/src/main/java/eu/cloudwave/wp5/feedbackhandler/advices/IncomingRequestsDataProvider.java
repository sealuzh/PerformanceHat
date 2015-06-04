package eu.cloudwave.wp5.feedbackhandler.advices;

import java.util.List;

import eu.cloudwave.wp5.common.constants.AggregationInterval;
import eu.cloudwave.wp5.common.dto.costs.AggregatedIncomingRequestsDto;
import eu.cloudwave.wp5.feedbackhandler.model.db.DbApplication;

/**
 * This class provides bunch of methods to get aggregated statistics about incoming requests. Incoming requests are
 * collected at and associated with the corresponding receiving microservice. Thus no information about the caller is
 * available.
 */
public interface IncomingRequestsDataProvider {
  /**
   * All incoming microservice requests aggregated by given time interval
   * 
   * @param aggregationInterval
   * @param timeRangeFrom
   * @param timeRangeTo
   * @return a list of aggregated requests
   */
  public List<AggregatedIncomingRequestsDto> getAllIncomingRequests(final AggregationInterval aggregationInterval, final Long timeRangeFrom, final Long timeRangeTo);

  /**
   * Incoming microservice requests to the given application aggregated by given time interval. Separate statistics for
   * each service identifier and each service method.
   * 
   * @param application
   *          the application which is used as identifier
   * @param aggregationInterval
   * @param timeRangeFrom
   * @param timeRangeTo
   * @return a list of aggregated requests
   */
  public List<AggregatedIncomingRequestsDto> getIncomingRequestsByIdentifier(
      final DbApplication application,
      final AggregationInterval aggregationInterval,
      final Long timeRangeFrom,
      final Long timeRangeTo);

  /**
   * Incoming microservice requests to the given application aggregated by given time interval without grouping by
   * service method. No separate statistics for each service method, statistics are only grouped by service identifier.
   * 
   * @param application
   *          the application which is used as identifier
   * @param aggregationInterval
   * @param timeRangeFrom
   * @param timeRangeTo
   * 
   * @return a list of aggregated requests
   */
  public AggregatedIncomingRequestsDto getOverallIncomingRequestsByIdentifier(DbApplication application, AggregationInterval aggregationInterval, Long timeRangeFrom, Long timeRangeTo);
}
