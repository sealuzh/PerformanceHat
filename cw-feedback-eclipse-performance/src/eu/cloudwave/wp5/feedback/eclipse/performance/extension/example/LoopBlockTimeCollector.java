package eu.cloudwave.wp5.feedback.eclipse.performance.extension.example;

import java.util.Set;

import com.google.common.collect.Sets;

import eu.cloudwave.wp5.feedback.eclipse.performance.core.builders.participants.ProcedureExecutionData;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ProgrammMarkerContext;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.IAstNode;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.LoopStatement;

//todo: do we need seperate class for this (or would inner class do)
//todo: if we keep make CTR private and use static methode calling the skip thing
class LoopBlockTimeCollector extends BlockTimeCollector{
	
	private final double avgSize;
    private final Set<IAstNode> headerExprs; //ether the foreach source or the initializer
    private final LoopStatement loop;
    
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
	public boolean shouldVisitNode(IAstNode node) {
		return !headerExprs.contains(node);
	}

	@Override
	public void finish() {
		 ProcedureExecutionData data = callback.loopBlockMeasured(avgExcecutionTime, avgSize, procedureExecutionTimes, loop, context);
		 if(parent != null) {
			 parent.avgExcecutionTime+=(avgExcecutionTime*avgSize);
			 if(data != null) parent.procedureExecutionTimes.add(data);
		 }
	}
  }