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

//todo: do we need seperate class for this (or would inner class do)
//todo: if we keep make CTR private and use static methode calling the skip thing
class LoopBlockTimeCollector extends BlockTimePredictor{
	
    private final Set<IAstNode> headerExprs; //ether the foreach source or the initializer
    private final Loop loop;
    private final List<PredictionNode> headerTimeStats = Lists.newArrayList();

	LoopBlockTimeCollector(BlockTimePredictor parent, BlockTimeCollectorCallback callback, AstContext context, Loop loop) {
		super(parent, callback, context);
		this.headerExprs = Sets.newHashSet(loop.getInitNodes());
		this.loop = loop;
	}
	
	@Override
	public PerformanceVisitor concreteNodeVisitor(IAstNode node) {
		if(headerExprs.contains(node)){
			return new BlockTimePredictor(callback, context){
				@Override
				public void finish() {
					headerTimeStats.addAll(excecutionTimeStats);				
				}	
			};
		}
		return null;
	}	
	
	@Override
	public PredictionNode generateResults() {
		return callback.loopMeasured(excecutionTimeStats, headerTimeStats, loop, context);
	}
	
  }