/*******************************************************************************
 * Copyright 2015 Software Evolution and Architecture Lab, University of Zurich
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 ******************************************************************************/
package eu.cloudwave.wp5.monitoring.tracing;

import java.lang.management.ManagementFactory;
import java.util.Collection;
import java.util.Map;

import eu.cloudwave.wp5.common.debug.ProcedureExecutionPrinter;
import eu.cloudwave.wp5.common.model.MetricType;
import eu.cloudwave.wp5.common.model.factories.MetricFactory;
import eu.cloudwave.wp5.common.model.factories.MetricFactoryImpl;
import eu.cloudwave.wp5.common.model.impl.MetricTypeImpl;
import eu.cloudwave.wp5.monitoring.aop.handlers.AbstractAroundJoinPointHandlerTemplate;
import eu.cloudwave.wp5.monitoring.aop.joinpoints.ProcedureCallJoinPoint;
import eu.cloudwave.wp5.monitoring.debug.DebugMode;
import eu.cloudwave.wp5.monitoring.dto.RunningProcedureExecution;
import eu.cloudwave.wp5.monitoring.rest.FeedbackHandlerMonitoringClient;

/**
 * A join-point handler that handles methods annotated with the Trace annotation.
 */
public class TracingHandler extends AbstractAroundJoinPointHandlerTemplate {

  private RunningProcedureExecution runningProcedureExecution;
  private long beforeUpTime;
  private long beforeCpuTime;

  private MetricFactory metricFactory;

  private TracingHandler() {
    this.metricFactory = new MetricFactoryImpl();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void before(final ProcedureCallJoinPoint joinPoint) {
    startProcedureExecution(joinPoint);
    setBeforeTimes();
  }

  private void startProcedureExecution(final ProcedureCallJoinPoint joinPoint) {
    runningProcedureExecution = new RunningProcedureExecution(joinPoint.getTargetProcedure(), System.currentTimeMillis());
    addParametersCollectionSizes(joinPoint);
    TraceStorage.INSTANCE.add(runningProcedureExecution);
  }

  private void setBeforeTimes() {
    beforeUpTime = getCurrentUpTime();
    beforeCpuTime = getCurrentCpuTime();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void after(final ProcedureCallJoinPoint joinPoint, final Object result) {
    addMetrics(result);
    finish();
  }

  private void addMetrics(final Object result) {
    addGeneralMetrics();
    addReturnTypeCollectionSize(result);
  }

  private void addGeneralMetrics() {
    addMetric(MetricTypeImpl.EXECUTION_TIME, executionTime());
    addMetric(MetricTypeImpl.CPU_USAGE, cpuUsage());
  }

  private void addReturnTypeCollectionSize(final Object result) {
    if (result instanceof Collection<?>) {
    	final int size = ((Collection<?>) result).size();
    	addMetric(MetricTypeImpl.COLLECTION_SIZE, size);
    } else if(result instanceof Map<?,?>){
    	final int size = ((Map<?,?>) result).size();
    	addMetric(MetricTypeImpl.COLLECTION_SIZE, size);
    }
  }

  private void addParametersCollectionSizes(final ProcedureCallJoinPoint joinPoint) {
    for (int i = 0; i < joinPoint.getArgs().length; i++) {
      final Object argument = joinPoint.getArgs()[i];
      if (argument instanceof Collection<?>) {
        final int size = ((Collection<?>) argument).size();
        addMetric(MetricTypeImpl.COLLECTION_SIZE, String.valueOf(i), size);
      }else if(argument instanceof Map<?,?>){
      	final int size = ((Map<?,?>) argument).size();
      	addMetric(MetricTypeImpl.COLLECTION_SIZE, size);
      }
    }
  }

  private void addMetric(final MetricType type, final Number value) {
    runningProcedureExecution.addMetric(metricFactory.create(type, runningProcedureExecution, value));
  }

  private void addMetric(final MetricType type, final String additionalQualifier, final Number value) {
    runningProcedureExecution.addMetric(metricFactory.create(type, runningProcedureExecution, additionalQualifier, value));
  }

  private void finish() {
    runningProcedureExecution.finish();
    setCaller();
    sendDataIfFinished();
  }

  private void setCaller() {
    final RunningProcedureExecution caller = TraceStorage.INSTANCE.getLastNotFinished();
    if (caller != null) {
      caller.addCallee(runningProcedureExecution);
    }
    runningProcedureExecution.setCaller(caller);
  }

  private void sendDataIfFinished() {
    if (TraceStorage.INSTANCE.isFinished()) {
      if (!TraceStorage.INSTANCE.getCallTrace().isEmpty()) {
        final RunningProcedureExecution rootProcedureExecution = TraceStorage.INSTANCE.getCallTrace().get(0);

        new Thread(new Runnable() {
          @Override
          public void run() {
            if (DebugMode.isActive()) {
              printData(rootProcedureExecution);
            }
            else {
              System.out.println("Monitoring: Sending data to feedback handler");
              sendData(rootProcedureExecution);
            }
          }
        }).start();
      }
      TraceStorage.INSTANCE.clear();
    }
  }

  private void sendData(final RunningProcedureExecution rootProcedureExecution) {
    final boolean success = new FeedbackHandlerMonitoringClient().postData(rootProcedureExecution);
    if (!success) {
      printData(rootProcedureExecution);
    }
  }

  private void printData(final RunningProcedureExecution rootProcedureExecution) {
    ProcedureExecutionPrinter.print(rootProcedureExecution, true);
  }

  private long executionTime() {
    return getCurrentUpTime() - beforeUpTime;
  }

  private float cpuUsage() {
    final long executionTime = executionTime();
    final long cpuTime = getCurrentCpuTime() - beforeCpuTime;
    final int availableProcessors = ManagementFactory.getOperatingSystemMXBean().getAvailableProcessors();
    if (executionTime > 0) {
      return Math.min(99F, cpuTime / (executionTime * 10000F * availableProcessors));
    }
    return Float.NaN;
  }

  private long getCurrentCpuTime() {
    return ManagementFactory.getThreadMXBean().getCurrentThreadCpuTime();
  }

  private long getCurrentUpTime() {
    return ManagementFactory.getRuntimeMXBean().getUptime();
  }

  public static TracingHandler of() {
    return new TracingHandler();
  }
}
