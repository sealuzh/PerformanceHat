package eu.cloudwave.wp5.feedback.eclipse.performance.extension.example;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ProgrammMarkerContext;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.Block;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.IAstNode;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.Try;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.BlockTimeCollectorCallback.ExecutionStats;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.timestats.AvgTimeNode;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.visitor.ProgrammMarkerVisitor;

class TryBlockTimeCollector extends BlockTimeCollector{
	private final List<List<AvgTimeNode>> catches = Lists.newArrayList();
    private final Set<IAstNode> catchesStarts; //ether the foreach source or the initializer
    private List<AvgTimeNode> finallyPart = null;
    private final Block finallyBlock;
    private final Try tryStm;

	TryBlockTimeCollector(BlockTimeCollector parent, BlockTimeCollectorCallback callback, ProgrammMarkerContext context, Try tryStm) {
		super(parent, callback, context);
		this.catchesStarts = Sets.newHashSet(tryStm.getCactchClauses());
		this.finallyBlock = tryStm.getFinally();
		this.tryStm = tryStm;
	}
	
	@Override
	public ProgrammMarkerVisitor concreteNodeVisitor(IAstNode node) {
		if(finallyBlock.equals(node)){
			return new BlockTimeCollector(callback, context){
				@Override
				public void finish() {
					finallyPart = excecutionTimeStats;			
				}	
			};
		}else if(catchesStarts.contains(node)){
			return new BlockTimeCollector(callback, context){
				@Override
				public void finish() {
					catches.add(excecutionTimeStats);			
				}	
			};
		}
		return null;
	}
	
	@Override
	public AvgTimeNode generateResults() {
		return callback.tryMeasured(excecutionTimeStats, finallyPart, catches, tryStm, context);
	}
  }