package eu.cloudwave.wp5.feedback.eclipse.performance.extension.example;

import java.util.List;

import eu.cloudwave.wp5.feedback.eclipse.performance.core.builders.participants.ProcedureExecutionData;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ProgrammMarkerContext;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.Expression;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.Invocation;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.LoopStatement;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.visitor.ProgrammMarkerVisitor;

class LoopBlockTimeCollector extends BlockTimeCollector{
	
	private final double avgSize;
    private final List<Expression> headerExprs; //ether the foreach source or the initializer
    private final LoopStatement loop;
    
	LoopBlockTimeCollector(BlockTimeCollector parent, BlockTimeCollectorCallback callback, ProgrammMarkerContext context, double avgSize, LoopStatement loop) {
		super(parent, callback, context);
		this.avgSize = avgSize;
		this.headerExprs = loop.getInitExpressions();
		this.loop = loop;
	}
	
	public LoopBlockTimeCollector(BlockTimeCollectorCallback callback, ProgrammMarkerContext context, double avgSize, LoopStatement loop) {
		this(null, callback, context, avgSize, loop);
	}

	//todo:add the other expressions + add the count once stuff
	
	@Override
	public ProgrammMarkerVisitor visit(Expression expr) {
		if(headerExprs.contains(expr)) return SKIP_CHILDS; 	//Todo: latter add to some count once
		return super.visit(expr);
	}

	@Override
	public ProgrammMarkerVisitor visit(Invocation invocation) {
		if(headerExprs.contains(invocation)) return SKIP_CHILDS; 	//Todo: latter add to some count once
		return super.visit(invocation);
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