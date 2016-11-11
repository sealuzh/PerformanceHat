package eu.cloudwave.wp5.feedback.eclipse.performance.extension.example;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ProgrammMarkerContext;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.IAstNode;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.LoopStatement;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.timestats.AvgTimeNode;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.visitor.ProgrammMarkerVisitor;

//todo: do we need seperate class for this (or would inner class do)
//todo: if we keep make CTR private and use static methode calling the skip thing
class LoopBlockTimeCollector extends BlockTimeCollector{
	
	private final double avgSize;
    private final Set<IAstNode> headerExprs; //ether the foreach source or the initializer
    private final LoopStatement loop;
    private final List<AvgTimeNode> headerTimeStats = Lists.newArrayList();
	protected double headerAvgExcecutionTime = 0.0;

	LoopBlockTimeCollector(BlockTimeCollector parent, BlockTimeCollectorCallback callback, ProgrammMarkerContext context, double avgSize, LoopStatement loop) {
		super(parent, callback, context);
		this.avgSize = avgSize;
		this.headerExprs = Sets.newHashSet(loop.getInitNodes());
		this.loop = loop;
	}
	
	public LoopBlockTimeCollector(BlockTimeCollectorCallback callback, ProgrammMarkerContext context, double avgSize, LoopStatement loop) {
		this(null, callback, context, avgSize, loop);
	}
	
	@Override
	public ProgrammMarkerVisitor concreteBranchVisitor(IAstNode node) {
		if(headerExprs.contains(node)){
			return new BlockTimeCollector(callback, context){
				@Override
				public void finish() {
					headerAvgExcecutionTime+=avgExcecutionTime;
					headerTimeStats.addAll(excecutionTimeStats);				
				}	
			};
		}
		return null;
	}

	

	@Override
	public void finish() {
		 AvgTimeNode data = callback.loopBlockMeasured(avgExcecutionTime, avgSize, excecutionTimeStats,headerAvgExcecutionTime, headerTimeStats, loop, context);
		 if(parent != null) {
			 parent.avgExcecutionTime+=(headerAvgExcecutionTime+(avgExcecutionTime*avgSize));
			 if(data != null) parent.excecutionTimeStats.add(data);
		 }
	}
  }