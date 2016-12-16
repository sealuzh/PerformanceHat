package eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.predictor;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.AstContext;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.PredictionNode;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.Block;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.IAstNode;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.Try;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.visitor.PerformanceVisitor;

/**
 * Visitor for the TryPart of the BlockTimePredictor framework
 * @author Markus Knecht
 *
 */
class TryBlockTimePredictor extends BlockTimePredictor{
	private final List<List<PredictionNode>> catches = Lists.newArrayList();
    private final Set<IAstNode> catchesStarts; //ether the foreach source or the initializer
    private List<PredictionNode> finallyPart = null;
    private final Block finallyBlock;
    private final Try tryStm;

	TryBlockTimePredictor(BlockTimePredictor parent, BlockTimePredictorCallback callback, Try tryStm) {
		super(parent, callback);
		//all the catches
		this.catchesStarts = Sets.newHashSet(tryStm.getCactchClauses());
		//the finally
		this.finallyBlock = tryStm.getFinally();
		this.tryStm = tryStm;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public PerformanceVisitor concreteNodeVisitor(IAstNode node) {
		//if we encounter the finally, do prediction recursively and record it
		if(finallyBlock != null && finallyBlock.equals(node)){
			return new BlockTimePredictor(callback){
				@Override
				public void finish() {
					finallyPart = excecutionTimeStats;			
				}	
			};
		//if we encounter a catchClause, do prediction recursively and add it to the catch clause predictions
		}else if(catchesStarts.contains(node)){
			return new BlockTimePredictor(callback){
				@Override
				public void finish() {
					catches.add(excecutionTimeStats);			
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
		return callback.tryMeasured(excecutionTimeStats, finallyPart, catches, tryStm);
	}
  }