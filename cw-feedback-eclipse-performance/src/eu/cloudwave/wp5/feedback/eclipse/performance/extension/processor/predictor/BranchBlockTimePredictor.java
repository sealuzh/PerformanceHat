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

/**
 * Visitor for the BranchPart of the BlockTimePredictor framework
 * @author Markus Knecht
 *
 */
class BranchBlockTimePredictor extends BlockTimePredictor{
	
	private final List<List<PredictionNode>> stats = Lists.newArrayList();
    private final Set<IAstNode> branchStarts; //ether the foreach source or the initializer
    private final Branching branch;

	BranchBlockTimePredictor(BlockTimePredictor parent, BlockTimePredictorCallback callback, Branching branch) {
		super(parent, callback);
		//all the branches
		this.branchStarts = Sets.newHashSet(branch.getBranches());
		this.branch = branch;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public PerformanceVisitor concreteNodeVisitor(IAstNode node) {
		//if we encounter a specific branch, do prediction recursively and add them to the list of branch predictions
		if(branchStarts.contains(node)){
			return new BlockTimePredictor(callback){
				@Override
				public void finish() {
					stats.add(excecutionTimeStats);			
				}	
			};
		}
		return null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public PredictionNode generateResults() {
		//do call back to make the actual prediciton
		return callback.branchingMeasured(excecutionTimeStats, stats, branch);
	}

  }