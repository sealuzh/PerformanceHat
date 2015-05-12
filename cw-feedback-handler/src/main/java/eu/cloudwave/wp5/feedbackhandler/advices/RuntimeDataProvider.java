package eu.cloudwave.wp5.feedbackhandler.advices;

import java.util.List;

import eu.cloudwave.wp5.common.dto.AggregatedProcedureMetricsDto;
import eu.cloudwave.wp5.feedbackhandler.model.db.DbApplication;

/**
 * This class provides bunch of methods to get filtered an aggregated runtime data.
 */
public interface RuntimeDataProvider {

  /**
   * Get all hotspot methods of a given application.
   * 
   * @param application
   *          the application
   * @param threshold
   *          the hotspots threshold
   * @return a list of all hotspot methods
   */
  public List<AggregatedProcedureMetricsDto> hotspots(DbApplication application, double threshold);

}
