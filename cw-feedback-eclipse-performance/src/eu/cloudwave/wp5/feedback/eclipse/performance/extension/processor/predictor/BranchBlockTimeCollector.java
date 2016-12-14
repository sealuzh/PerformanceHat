package eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.predictor;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.AstContext;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.PredictionNode;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.Branching;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.IAstNode;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.visitor.PerformanceVisitor;

//todo: do we need seperate class for this (or would inner class do)
//todo: if we keep make CTR private and use static methode calling the skip thing
class BranchBlockTimeCollector extends BlockTimePredictor{
	private final List<List<PredictionNode>> stats = Lists.newArrayList();
    private final Set<IAstNode> branchStarts; //ether the foreach source or the initializer
    private final Branching branch;

	BranchBlockTimeCollector(BlockTimePredictor parent, BlockTimeCollectorCallback callback, AstContext context, Branching branch) {
		super(parent, callback, context);
		this.branchStarts = Sets.newHashSet(branch.getBranches());
		this.branch = branch;
	}
	
	@Override
	public PerformanceVisitor concreteNodeVisitor(IAstNode node) {
		if(branchStarts.contains(node)){
			return new BlockTimePredictor(callback, context){
				@Override
				public void finish() {
					stats.add(excecutionTimeStats);			
				}	
			};
		}
		return null;
	}
	
	@Override
	public PredictionNode generateResults() {
		return callback.branchMeasured(excecutionTimeStats, stats, branch, context);
	}

  }