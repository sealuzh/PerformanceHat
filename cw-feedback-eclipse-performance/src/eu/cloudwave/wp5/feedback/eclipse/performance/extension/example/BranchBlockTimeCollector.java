package eu.cloudwave.wp5.feedback.eclipse.performance.extension.example;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ProgrammMarkerContext;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.Branching;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.IAstNode;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.BlockTimeCollectorCallback.ExecutionStats;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.timestats.AvgTimeNode;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.visitor.ProgrammMarkerVisitor;

//todo: do we need seperate class for this (or would inner class do)
//todo: if we keep make CTR private and use static methode calling the skip thing
class BranchBlockTimeCollector extends BlockTimeCollector{
	private final List<ExecutionStats> stats = Lists.newArrayList();
    private final Set<IAstNode> branchStarts; //ether the foreach source or the initializer
    private final Branching branch;

	BranchBlockTimeCollector(BlockTimeCollector parent, BlockTimeCollectorCallback callback, ProgrammMarkerContext context, Branching branch) {
		super(parent, callback, context);
		this.branchStarts = Sets.newHashSet(branch.getBranches());
		this.branch = branch;
	}
	
	@Override
	public ProgrammMarkerVisitor concreteNodeVisitor(IAstNode node) {
		if(branchStarts.contains(node)){
			return new BlockTimeCollector(callback, context){
				@Override
				public void finish() {
					stats.add(new ExecutionStats(avgExcecutionTime,excecutionTimeStats));			
				}	
			};
		}
		return null;
	}
	
	@Override
	public AvgTimeNode generateResults() {
		return callback.branchMeasured(new ExecutionStats(avgExcecutionTime, excecutionTimeStats), stats, branch, context);
	}

  }