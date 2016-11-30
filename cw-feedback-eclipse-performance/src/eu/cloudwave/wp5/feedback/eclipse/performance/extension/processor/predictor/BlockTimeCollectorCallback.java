package eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.predictor;

import java.util.List;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.AstContext;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.PredictionNode;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.Branching;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.Invocation;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.Loop;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.Try;

public interface BlockTimeCollectorCallback {
	
	public class ExecutionStats{
		public final double avgExcecutionTime;
		public final List<PredictionNode> excecutionTimeStats;
		
		public ExecutionStats(double avgExcecutionTime, List<PredictionNode> excecutionTimeStats) {
			this.avgExcecutionTime = avgExcecutionTime;
			this.excecutionTimeStats = excecutionTimeStats;
		}
		
	}
	
	public PredictionNode invocationEncountered(Invocation invocation, AstContext context);
	public PredictionNode loopMeasured(List<PredictionNode> iterationExecutionTimes, List<PredictionNode> headerExecutionTimes, Loop loop, AstContext context);
	public PredictionNode branchMeasured(List<PredictionNode> conditionExecutionTimes, List<List<PredictionNode>> branchExccutionTimes, Branching branch, AstContext context);
	public PredictionNode tryMeasured(List<PredictionNode> tryExecutionTimes, List<PredictionNode> finnalyExecutionTimes, List<List<PredictionNode>> catchExccutionTimes, Try tryStm, AstContext context);

}