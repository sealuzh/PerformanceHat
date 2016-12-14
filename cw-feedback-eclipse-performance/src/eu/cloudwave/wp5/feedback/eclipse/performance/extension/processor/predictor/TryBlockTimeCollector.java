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

class TryBlockTimeCollector extends BlockTimePredictor{
	private final List<List<PredictionNode>> catches = Lists.newArrayList();
    private final Set<IAstNode> catchesStarts; //ether the foreach source or the initializer
    private List<PredictionNode> finallyPart = null;
    private final Block finallyBlock;
    private final Try tryStm;

	TryBlockTimeCollector(BlockTimePredictor parent, BlockTimeCollectorCallback callback, AstContext context, Try tryStm) {
		super(parent, callback, context);
		this.catchesStarts = Sets.newHashSet(tryStm.getCactchClauses());
		this.finallyBlock = tryStm.getFinally();
		this.tryStm = tryStm;
	}
	
	@Override
	public PerformanceVisitor concreteNodeVisitor(IAstNode node) {
		if(finallyBlock != null && finallyBlock.equals(node)){
			return new BlockTimePredictor(callback, context){
				@Override
				public void finish() {
					finallyPart = excecutionTimeStats;			
				}	
			};
		}else if(catchesStarts.contains(node)){
			return new BlockTimePredictor(callback, context){
				@Override
				public void finish() {
					catches.add(excecutionTimeStats);			
				}	
			};
		}
		return null;
	}
	
	@Override
	public PredictionNode generateResults() {
		return callback.tryMeasured(excecutionTimeStats, finallyPart, catches, tryStm, context);
	}
  }