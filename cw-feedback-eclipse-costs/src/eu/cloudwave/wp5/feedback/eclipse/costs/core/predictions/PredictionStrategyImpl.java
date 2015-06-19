package eu.cloudwave.wp5.feedback.eclipse.costs.core.predictions;

import java.util.List;

import com.google.common.collect.Lists;

import eu.cloudwave.wp5.common.dto.ApplicationDto;
import eu.cloudwave.wp5.common.dto.costs.AggregatedRequestsDto;
import eu.cloudwave.wp5.common.model.Prediction;
import eu.cloudwave.wp5.common.model.PredictionType;
import eu.cloudwave.wp5.common.model.impl.PredictionImpl;
import eu.cloudwave.wp5.common.model.impl.PredictionTypeImpl;

public class PredictionStrategyImpl implements PredictionStrategy {

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

    Prediction newInstances = predictNewInstances(invokedApplication, existingRequestsToInvokedApplication, expectedAdditionalRequests);
    if (newInstances != null) {
      predictions.add(newInstances);

      Prediction additionalCosts = additionalCostsPerHour(invokedApplication, newInstances.getValue().doubleValue());
      if (additionalCosts != null)
        predictions.add(additionalCosts);

      Prediction costTrend = costTrend(invokedApplication, newInstances.getValue().doubleValue());
      if (costTrend != null)
        predictions.add(costTrend);
    }

    return predictions;
  }

  /**
   * Predicting the number of new instances of the given microservice
   * 
   * @param invokedApplication
   *          {@link ApplicationDto}
   * @param existingRequestsToInvokedApplication
   *          {@link AggregatedRequestsDto}
   * @param expectedAdditionalRequests
   *          {@link AggregatedRequestsDto}
   * 
   * @return {@link Prediction}
   */
  private Prediction predictNewInstances(
      final ApplicationDto invokedApplication,
      final AggregatedRequestsDto existingRequestsToInvokedApplication,
      final AggregatedRequestsDto expectedAdditionalRequests) {

    double newRequestTotal = (existingRequestsToInvokedApplication.getAvg() + expectedAdditionalRequests.getAvg());

    if (invokedApplication.getMaxRequestsPerInstancePerSecond() != null && invokedApplication.getMaxRequestsPerInstancePerSecond() > 0) {

      // calc impact
      double newInstancesTotal = newRequestTotal / invokedApplication.getMaxRequestsPerInstancePerSecond();
      double newInstances = newInstancesTotal - invokedApplication.getInstances();

      if (newInstances >= 0) {
        return new PredictionImpl(NEW_INSTANCES_PREDICTION, newInstances);
      }
    }
    return null;
  }

  /**
   * Predicting the additional costs per hours given a number of new instances
   * 
   * @param invokedApplication
   *          {@link ApplicationDto}
   * @param newInstances
   *          as double
   * 
   * @return {@link Prediction}
   */
  private Prediction additionalCostsPerHour(final ApplicationDto invokedApplication, double newInstances) {
    if (invokedApplication.getPricePerInstanceInUSD() != null) {
      return new PredictionImpl(ADDITIONAL_COSTS_PER_HOUR_PREDICTION, (int) Math.ceil(newInstances * invokedApplication.getPricePerInstanceInUSD()));
    }
    return null;
  }

  /**
   * Predicting the cost trend
   * 
   * @param invokedApplication
   *          {@link ApplicationDto}
   * @param newInstances
   *          as double
   * 
   * @return {@link Prediction}
   */
  private Prediction costTrend(final ApplicationDto invokedApplication, double newInstances) {
    // simplifying assumption: costs correlate with number of instances
    if (invokedApplication != null && invokedApplication.getInstances() != null && newInstances > 0) {
      int trend = (int) Math.ceil((newInstances / (newInstances + invokedApplication.getInstances().doubleValue())) * 100.0);
      return new PredictionImpl(COST_TREND, trend);
    }
    return null;
  }
}
