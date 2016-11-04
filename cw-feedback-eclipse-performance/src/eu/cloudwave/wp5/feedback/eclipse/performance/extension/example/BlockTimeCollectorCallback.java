package eu.cloudwave.wp5.feedback.eclipse.performance.extension.example;

import java.util.List;

import eu.cloudwave.wp5.feedback.eclipse.performance.core.builders.participants.ProcedureExecutionData;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ProgrammMarkerContext;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.LoopStatement;

public interface BlockTimeCollectorCallback {
	//public ProcedureExecutionData methodBlockMeasured(double avgExecTime, List<ProcedureExecutionData> procedureExecutionTimes, ProgrammMarkerContext context);
	public ProcedureExecutionData loopBlockMeasured(double avgExecTimePerIter, double avgIters, List<ProcedureExecutionData> procedureExecutionTimes, LoopStatement loop, ProgrammMarkerContext context);
	//public ProcedureExecutionData ifBlockMeasured(double avgExecTimePerIter, double avgIters, List<ProcedureExecutionData> procedureExecutionTimes, ProgrammMarkerContext context);
	//public ProcedureExecutionData switchBlockMeasured(double avgExecTimePerIter, double avgIters, List<ProcedureExecutionData> procedureExecutionTimes, ProgrammMarkerContext context);
}