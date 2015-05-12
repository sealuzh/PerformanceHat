/*******************************************************************************
 * Copyright 2015 Software Evolution and Architecture Lab, University of Zurich
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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import eu.cloudwave.wp5.common.constants.Headers;
import eu.cloudwave.wp5.common.constants.Params;
import eu.cloudwave.wp5.common.constants.Urls;
import eu.cloudwave.wp5.common.model.Procedure;
import eu.cloudwave.wp5.common.model.ProcedureExecutionMetric;
import eu.cloudwave.wp5.common.model.impl.MetricTypeImpl;
import eu.cloudwave.wp5.common.model.impl.ProcedureImpl;
import eu.cloudwave.wp5.common.util.DateTimes;
import eu.cloudwave.wp5.common.util.Splitters;
import eu.cloudwave.wp5.feedbackhandler.constants.Attributes;
import eu.cloudwave.wp5.feedbackhandler.model.db.DbApplication;
import eu.cloudwave.wp5.feedbackhandler.repositories.MetricRepository;

@Controller
public class PlotController extends AbstractBaseUiController {

  private static final String DATE_TIME_PATTERN = "yyyy/MM/dd HH:mm:ss";
  private static final String TIME_SERIES_PATTERN = "[ new Date('%s'), %s ],";
  private static final String OPENING_BRACKET = "[";
  private static final String CLOSING_BRACKET = "]";

  private static final String PROCEDURE_PLOT = "ProcedurePlot";

  @Autowired
  private MetricRepository metricRepository;

  @RequestMapping(Urls.PLOTS_PROCEDURE)
  @ResponseStatus(HttpStatus.OK)
  public ModelAndView procedure(
      @RequestHeader(value = Headers.ACCESS_TOKEN, required = false) final String accessToken,
      @RequestHeader(value = Headers.APPLICATION_ID, required = false) final String applicationId,
      @RequestParam(Params.CLASS_NAME) final String className,
      @RequestParam(Params.PROCEDURE_NAME) final String procedureName,
      @RequestParam(Params.ARGUMENTS) final String arguments) {
    final DbApplication application = handleUnauthorized(applicationId, accessToken);
    final String[] procedureArguments = Iterables.toArray(Splitters.onComma(arguments), String.class);
    final List<? extends ProcedureExecutionMetric> metrics = metricRepository.find(application, className, procedureName, procedureArguments);
    final Procedure procedure = metrics.size() > 0 ? metrics.get(0).getProcedure() : new ProcedureImpl(className, procedureName, null, Lists.newArrayList(procedureArguments), Lists.newArrayList());
    final ModelAndView model = new ModelAndView(PROCEDURE_PLOT);
    final String[] formattedData = formatData(metrics);
    model.addObject(Attributes.PROCEDURE, procedure);
    model.addObject(Attributes.DATA_EXECUTION_TIME, formattedData[0]);
    model.addObject(Attributes.DATA_CPU_USAGE, formattedData[1]);
    return model;
  }

  private String[] formatData(final List<? extends ProcedureExecutionMetric> metrics) {
    String dataExecTime = OPENING_BRACKET;
    String dataCpuUsage = OPENING_BRACKET;
    for (final ProcedureExecutionMetric metric : metrics) {
      final String formattedStartTime = DateTimes.format(DateTimes.fromMilliSeconds(metric.getTimestamp()), DATE_TIME_PATTERN);
      if (metric.getType().equals(MetricTypeImpl.EXECUTION_TIME)) {
        dataExecTime += String.format(TIME_SERIES_PATTERN, formattedStartTime, metric.getValue());
      }
      else if (metric.getType().equals(MetricTypeImpl.CPU_USAGE)) {
        final double cpuUsage = Double.isNaN((Double) metric.getValue()) ? 0 : (Double) metric.getValue();
        dataCpuUsage += String.format(TIME_SERIES_PATTERN, formattedStartTime, cpuUsage);
      }
    }
    dataExecTime += CLOSING_BRACKET;
    dataCpuUsage += CLOSING_BRACKET;
    return new String[] { dataExecTime, dataCpuUsage };
  }
}
