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
	public AvgTimeNode loopMeasured(List<AvgTimeNode> iterationExecutionTimes, List<AvgTimeNode> headerExecutionTimes, Loop loop, ProgrammMarkerContext context);
	public AvgTimeNode branchMeasured(List<AvgTimeNode> conditionExecutionTimes, List<List<AvgTimeNode>> branchExccutionTimes, Branching branch, ProgrammMarkerContext context);
	public AvgTimeNode tryMeasured(List<AvgTimeNode> tryExecutionTimes, List<AvgTimeNode> finnalyExecutionTimes, List<List<AvgTimeNode>> catchExccutionTimes, Try tryStm, ProgrammMarkerContext context);

}