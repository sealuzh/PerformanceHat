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
package eu.cloudwave.wp5.common.debug;

import java.util.ArrayList;
import java.util.List;

import eu.cloudwave.wp5.common.dto.model.MetricContainingProcedureExecutionDto;
import eu.cloudwave.wp5.common.dto.model.ProcedureExecutionDto;
import eu.cloudwave.wp5.common.model.ProcedureMetric;
import eu.cloudwave.wp5.common.model.Procedure;
import eu.cloudwave.wp5.common.model.ProcedureExecution;

/**
 * Provides a method to print a {@link ProcedureExecution} and its callees to the console.
 */
public class ProcedureExecutionPrinter {

  private static final String METRIC_PREFIX = "-> ";
  private static final String EMPTY = "";
  private static final String INDENT_ONE = "  ";
  private static final String COLON_AND_SPACE = ": ";
  private static final String ADDITION_PATTERN = " (%s)";

  public static void print(final ProcedureExecutionDto procedureExecution, final boolean printMetrics) {
    print(procedureExecution, 0, printMetrics);
  }

  private static void print(final ProcedureExecutionDto procedureExecution, final int level, final boolean printMetrics) {
    printProcedureExecution(procedureExecution, level, printMetrics);
    for (final ProcedureExecutionDto callee : procedureExecution.getCallees()) {
      print(callee, level + 1, printMetrics);
    }
  }

  public static void print(final List<? extends ProcedureExecution> procedureExecutions, final boolean printMetrics) {
    final List<? extends ProcedureExecution> copy = new ArrayList<ProcedureExecution>(procedureExecutions);
    if (copy.size() > 0) {
      print(copy, 0, printMetrics);
    }
  }

  private static void print(final List<? extends ProcedureExecution> procedureExecutions, final int level, final boolean printMetrics) {
    if (procedureExecutions.size() > 0) {
      final ProcedureExecution current = procedureExecutions.get(0);
      procedureExecutions.remove(0);
      printProcedureExecution(current, level, printMetrics);
      while (procedureExecutions.size() > 0) {
        final ProcedureExecution next = procedureExecutions.get(0);
        if (next.getCaller().equals(current)) {
          print(procedureExecutions, level + 1, printMetrics);
        }
        else if (next.getCaller().equals(current.getCaller())) {
          print(procedureExecutions, level, printMetrics);
        }
        else {
          break;
        }
      }
    }
  }

  private static void printProcedureExecution(final ProcedureExecution procedureExecution, final int level, final boolean printMetrics) {
    System.out.println(asString(procedureExecution, level));
    if (printMetrics && procedureExecution instanceof MetricContainingProcedureExecutionDto) {
      final MetricContainingProcedureExecutionDto metricContainer = (MetricContainingProcedureExecutionDto) procedureExecution;
      for (final ProcedureMetric metric : metricContainer.getMetrics()) {
        printMetric(metric, level + 2);
      }
    }
  }

  private static void printMetric(final ProcedureMetric metric, final int level) {
    final String addition = metric.getAdditionalQualifier().equals(EMPTY) ? EMPTY : String.format(ADDITION_PATTERN, metric.getAdditionalQualifier());
    System.out.println(getIndent(level) + METRIC_PREFIX + metric.getType() + addition + COLON_AND_SPACE + metric.getValue());
  }

  private static String asString(final ProcedureExecution procedureExececution, final int level) {
    final Procedure procedure = procedureExececution.getProcedure();
    return getIndent(level) + procedure.getKind() + COLON_AND_SPACE + procedure;
  }

  private static String getIndent(final int level) {
    String indent = EMPTY;
    for (int i = 0; i < level; i++) {
      indent += INDENT_ONE;
    }
    return indent;
  }

}
