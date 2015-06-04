/*******************************************************************************
 * Copyright 2015 Software Evolution and Architecture Lab, University of Zurich
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package eu.cloudwave.wp5.feedbackhandler.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Optional;

import eu.cloudwave.wp5.common.constants.Headers;
import eu.cloudwave.wp5.common.constants.Params;
import eu.cloudwave.wp5.common.constants.Urls;
import eu.cloudwave.wp5.common.dto.AggregatedProcedureMetricsDto;
import eu.cloudwave.wp5.common.dto.model.ProcedureExecutionMetricDto;
import eu.cloudwave.wp5.common.model.ProcedureExecutionMetric;
import eu.cloudwave.wp5.common.util.Splitters;
import eu.cloudwave.wp5.feedbackhandler.advices.RuntimeDataProvider;
import eu.cloudwave.wp5.feedbackhandler.aggregations.AggregatedAverage;
import eu.cloudwave.wp5.feedbackhandler.controller.dto.MetricDtoFactory;
import eu.cloudwave.wp5.feedbackhandler.model.db.DbApplication;
import eu.cloudwave.wp5.feedbackhandler.repositories.MetricRepository;

@RestController
public class AnalysisController extends AbstractBaseRestController {

  private static final String EMPTY = "";

  @Autowired
  private MetricRepository metricRepository;

  @Autowired
  private MetricDtoFactory metricDtoFactory;

  @Autowired
  private RuntimeDataProvider runtimeDataProvider;

  @RequestMapping(Urls.ANALYIS__HOTSPOTS)
  @ResponseStatus(HttpStatus.OK)
  public AggregatedProcedureMetricsDto[] hotspots(
      @RequestHeader(Headers.ACCESS_TOKEN) final String accessToken,
      @RequestHeader(Headers.APPLICATION_ID) final String applicationId,
      @RequestParam(Params.THRESHOLD) final Double threshold) {
    final DbApplication application = handleUnauthorized(applicationId, accessToken);
    final List<AggregatedProcedureMetricsDto> hotspots = runtimeDataProvider.hotspots(application, threshold);
    return hotspots.toArray(new AggregatedProcedureMetricsDto[hotspots.size()]);
  }

  @RequestMapping(Urls.ANALYIS__PROCEDURE)
  @ResponseStatus(HttpStatus.OK)
  public ProcedureExecutionMetricDto[] procedure(
      @RequestHeader(Headers.ACCESS_TOKEN) final String accessToken,
      @RequestHeader(Headers.APPLICATION_ID) final String applicationId,
      @RequestParam(Params.CLASS_NAME) final String className,
      @RequestParam(Params.PROCEDURE_NAME) final String procedureName,
      @RequestParam(Params.ARGUMENTS) final String arguments) {
    final DbApplication application = handleUnauthorized(applicationId, accessToken);
    final List<? extends ProcedureExecutionMetric> metrics = metricRepository.find(application, className, procedureName, Splitters.arrayOnComma(arguments));
    final List<ProcedureExecutionMetricDto> metricDtos = metricDtoFactory.create(metrics);
    return metricDtos.toArray(new ProcedureExecutionMetricDto[metricDtos.size()]);
  }

  @RequestMapping(Urls.ANALYIS__AVG_EXEC_TIME)
  @ResponseStatus(HttpStatus.OK)
  public Double avgExecTime(
      @RequestHeader(Headers.ACCESS_TOKEN) final String accessToken,
      @RequestHeader(Headers.APPLICATION_ID) final String applicationId,
      @RequestParam(Params.CLASS_NAME) final String className,
      @RequestParam(Params.PROCEDURE_NAME) final String procedureName,
      @RequestParam(Params.ARGUMENTS) final String arguments) {
    final DbApplication application = handleUnauthorized(applicationId, accessToken);
    final Optional<Double> averageExecTime = metricRepository.aggregateExecutionTime(application, className, procedureName, Splitters.arrayOnComma(arguments));
    return averageExecTime.isPresent() ? averageExecTime.get() : null;
  }

  @RequestMapping(Urls.ANALYIS__COLLECTION_SIZE)
  @ResponseStatus(HttpStatus.OK)
  public Double collectionsize(
      @RequestHeader(Headers.ACCESS_TOKEN) final String accessToken,
      @RequestHeader(Headers.APPLICATION_ID) final String applicationId,
      @RequestParam(Params.CLASS_NAME) final String className,
      @RequestParam(Params.PROCEDURE_NAME) final String procedureName,
      @RequestParam(Params.ARGUMENTS) final String arguments,
      @RequestParam(value = Params.NUMBER, required = false) final String number) {
    final String qualifier = number != null ? number : EMPTY;
    final DbApplication application = handleUnauthorized(applicationId, accessToken);
    final AggregationResults<AggregatedAverage> aggregationResult = metricRepository.aggregateCollectionSizes(application, className, procedureName, Splitters.arrayOnComma(arguments));
    for (final AggregatedAverage aggregation : aggregationResult) {
      if (aggregation.getAdditionalQualifier().equals(qualifier)) {
        return aggregation.getAverageValue();
      }
    }
    return null;
  }
}
