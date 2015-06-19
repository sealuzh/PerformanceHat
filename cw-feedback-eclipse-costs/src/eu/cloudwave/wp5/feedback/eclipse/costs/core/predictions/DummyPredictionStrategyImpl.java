package eu.cloudwave.wp5.feedback.eclipse.costs.core.predictions;

import java.util.List;

import com.google.common.collect.Lists;

import eu.cloudwave.wp5.common.dto.ApplicationDto;
import eu.cloudwave.wp5.common.dto.costs.AggregatedRequestsDto;
import eu.cloudwave.wp5.common.model.Prediction;
import eu.cloudwave.wp5.common.model.PredictionType;
import eu.cloudwave.wp5.common.model.impl.PredictionImpl;
import eu.cloudwave.wp5.common.model.impl.PredictionTypeImpl;

public class DummyPredictionStrategyImpl implements PredictionStrategy {

  // Prediction types of this strategy
  public static final PredictionType NEW_INSTANCES_PREDICTION = new PredictionTypeImpl("New instances", "instances");
  public static final PredictionType ADDITIONAL_COSTS_PER_HOUR_PREDICTION = new PredictionTypeImpl("Additional costs per hour", "USD");
  public static final PredictionType COST_TREND = new PredictionTypeImpl("Cost Trend", "%");

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Prediction> predict(final ApplicationDto invokedApplication, final AggregatedRequestsDto existingRequestsToInvokedApplication, final AggregatedRequestsDto expectedAdditionalRequests) {

    List<Prediction> predictions = Lists.newArrayList();

    predictions.add(new PredictionImpl(NEW_INSTANCES_PREDICTION, 5));
    predictions.add(new PredictionImpl(ADDITIONAL_COSTS_PER_HOUR_PREDICTION, 0.5));
    predictions.add(new PredictionImpl(COST_TREND, 50));

    return predictions;
  }
}
