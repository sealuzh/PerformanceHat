package eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.predictor;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.AstContext;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.PredictionNode;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.IAstNode;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.Loop;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.visitor.PerformanceVisitor;


/**
 * Visitor for the LoopPart of the BlockTimePredictor framework
 * @author Markus Knecht
 *
 */
class LoopBlockTimePredictor extends BlockTimePredictor{
	
    private final Set<IAstNode> headerExprs; //ether the foreach source or the initializer
    private final Loop loop;
    private final List<PredictionNode> headerTimeStats = Lists.newArrayList();

	LoopBlockTimePredictor(BlockTimePredictor parent, BlockTimePredictorCallback callback, Loop loop) {
		super(parent, callback);
		//all the headers
		this.headerExprs = Sets.newHashSet(loop.getInitNodes());
		this.loop = loop;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public PerformanceVisitor concreteNodeVisitor(IAstNode node) {
		//if we encounter a specific header, do prediction recursively and add them to the list of header predictions
		if(headerExprs.contains(node)){
			return new BlockTimePredictor(callback){
				@Override
				public void finish() {
					headerTimeStats.addAll(excecutionTimeStats);				
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
		return callback.loopMeasured(excecutionTimeStats, headerTimeStats, loop);
	}
	
  }