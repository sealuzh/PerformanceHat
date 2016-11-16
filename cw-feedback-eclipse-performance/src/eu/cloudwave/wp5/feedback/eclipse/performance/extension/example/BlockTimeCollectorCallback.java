package eu.cloudwave.wp5.feedback.eclipse.performance.extension.example;

import java.util.List;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ProgrammMarkerContext;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.Branching;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.Invocation;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.Loop;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.Try;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.timestats.AvgTimeNode;

public interface BlockTimeCollectorCallback {
	
	public class ExecutionStats{
		public final double avgExcecutionTime;
		public final List<AvgTimeNode> excecutionTimeStats;
		
		public ExecutionStats(double avgExcecutionTime, List<AvgTimeNode> excecutionTimeStats) {
			this.avgExcecutionTime = avgExcecutionTime;
			this.excecutionTimeStats = excecutionTimeStats;
		}
		
	}
	
	public AvgTimeNode invocationEncountered(Invocation invocation, ProgrammMarkerContext context);
	public AvgTimeNode loopMeasured(ExecutionStats iterationExecutionTime, ExecutionStats headerExecutionTime, Loop loop, ProgrammMarkerContext context);
	public AvgTimeNode branchMeasured(ExecutionStats conditionExecutionTime, List<ExecutionStats> branchExccutionTimes, Branching branch, ProgrammMarkerContext context);
	public AvgTimeNode tryMeasured(ExecutionStats tryExecutionTime, ExecutionStats finnalyExecutionTime, List<ExecutionStats> catchExccutionTimes, Try tryStm, ProgrammMarkerContext context);

}