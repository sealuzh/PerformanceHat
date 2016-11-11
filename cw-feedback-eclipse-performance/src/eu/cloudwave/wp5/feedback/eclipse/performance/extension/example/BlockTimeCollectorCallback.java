package eu.cloudwave.wp5.feedback.eclipse.performance.extension.example;

import java.util.List;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ProgrammMarkerContext;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.LoopStatement;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.timestats.AvgTimeNode;

public interface BlockTimeCollectorCallback {
	//public AvgTimeNode methodBlockMeasured(double avgExecTime, List<AvgTimeNode> procedureExecutionTimes, ProgrammMarkerContext context);
	public AvgTimeNode loopBlockMeasured(double avgExecTimePerIter, double avgIters, List<AvgTimeNode> bodyProcedureExecutionTimes, double avgHeaderExcecutionTime, List<AvgTimeNode> headerProcedureExecutionTimes, LoopStatement loop, ProgrammMarkerContext context);

	//public AvgTimeNode ifBlockMeasured(double avgExecTimePerIter, double avgIters, List<AvgTimeNode> procedureExecutionTimes, ProgrammMarkerContext context);
	//public AvgTimeNode switchBlockMeasured(double avgExecTimePerIter, double avgIters, List<AvgTimeNode> procedureExecutionTimes, ProgrammMarkerContext context);
}