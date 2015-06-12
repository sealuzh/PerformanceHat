package eu.cloudwave.wp5.feedback.eclipse.costs.core.predictions;

import java.util.List;

import eu.cloudwave.wp5.common.dto.ApplicationDto;
import eu.cloudwave.wp5.common.dto.costs.AggregatedRequestsDto;
import eu.cloudwave.wp5.common.model.Prediction;

public class PredictionStrategyImpl implements PredictionStrategy {

  @Override
  public List<Prediction> predict(ApplicationDto invokedApplication, AggregatedRequestsDto existingRequestsToInvokedApplication, AggregatedRequestsDto expectedAdditionalRequests) {
    return null;
  }

}
