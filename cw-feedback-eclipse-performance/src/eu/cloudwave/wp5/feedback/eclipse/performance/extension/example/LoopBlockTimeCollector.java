package eu.cloudwave.wp5.feedback.eclipse.performance.extension.example;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ProgrammMarkerContext;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.IAstNode;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.Loop;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.BlockTimeCollectorCallback.ExecutionStats;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.timestats.AvgTimeNode;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.visitor.ProgrammMarkerVisitor;

//todo: do we need seperate class for this (or would inner class do)
//todo: if we keep make CTR private and use static methode calling the skip thing
class LoopBlockTimeCollector extends BlockTimeCollector{
	
    private final Set<IAstNode> headerExprs; //ether the foreach source or the initializer
    private final Loop loop;
    private final List<AvgTimeNode> headerTimeStats = Lists.newArrayList();

	LoopBlockTimeCollector(BlockTimeCollector parent, BlockTimeCollectorCallback callback, ProgrammMarkerContext context, Loop loop) {
		super(parent, callback, context);
		this.headerExprs = Sets.newHashSet(loop.getInitNodes());
		this.loop = loop;
	}
	
	@Override
	public ProgrammMarkerVisitor concreteNodeVisitor(IAstNode node) {
		if(headerExprs.contains(node)){
			return new BlockTimeCollector(callback, context){
				@Override
				public void finish() {
					headerTimeStats.addAll(excecutionTimeStats);				
				}	
			};
		}
		return null;
	}	
	
	@Override
	public AvgTimeNode generateResults() {
		return callback.loopMeasured(excecutionTimeStats, headerTimeStats, loop, context);
	}
	
  }