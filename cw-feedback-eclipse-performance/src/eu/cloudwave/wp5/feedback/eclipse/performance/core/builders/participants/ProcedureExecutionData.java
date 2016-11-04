package eu.cloudwave.wp5.feedback.eclipse.performance.core.builders.participants;

import eu.cloudwave.wp5.common.model.Procedure;
import eu.cloudwave.wp5.common.util.TimeValues;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.tag.MethodLocator;

public class ProcedureExecutionData {
    private static final String POINT = ".";
    private static final int DECIMAL_PLACES = 3;

    private String className;
    private String name;
    private String executionTime;

    private ProcedureExecutionData(final String className, final String name, final String executionTime) {
      this.className = className;
      this.name = name;
      this.executionTime = executionTime;
    }

    public String getClassName() {
      return className;
    }

    public String getName() {
      return name;
    }

    public String getExecutionTime() {
      return executionTime;
    }

    public static ProcedureExecutionData of(final MethodLocator loc, final Double executionTime) {
        final String simpleClassName = loc.className.substring(loc.className.lastIndexOf(POINT) + 1);
        return new ProcedureExecutionData(simpleClassName,loc.methodName, TimeValues.toText(executionTime, DECIMAL_PLACES));
      }
    
    public static ProcedureExecutionData of(final Procedure procedure, final Double executionTime) {
      final String simpleClassName = procedure.getClassName().substring(procedure.getClassName().lastIndexOf(POINT) + 1);
      return new ProcedureExecutionData(simpleClassName, procedure.getName(), TimeValues.toText(executionTime, DECIMAL_PLACES));
    }
  }