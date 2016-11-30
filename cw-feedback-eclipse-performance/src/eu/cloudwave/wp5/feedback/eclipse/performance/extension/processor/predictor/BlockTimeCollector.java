package eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.predictor;

import java.util.List;

import com.google.common.collect.Lists;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.AstContext;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.PredictionNode;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.Branching;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.Invocation;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.Loop;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.Try;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.visitor.PerformanceVisitor;

public abstract class BlockTimeCollector extends PerformanceVisitor{
	protected final BlockTimeCollectorCallback callback;
	protected final BlockTimeCollector parent;
	protected final AstContext context;
	protected List<PredictionNode> excecutionTimeStats = Lists.newArrayList();

	BlockTimeCollector(BlockTimeCollector parent, BlockTimeCollectorCallback callback, AstContext context) {
		this.parent = parent;
		this.callback = callback;
		this.context = context;
	}
	
	public BlockTimeCollector(BlockTimeCollectorCallback callback,AstContext context) {
		this(null,callback, context);
	}

	@Override
	public PerformanceVisitor visit(Invocation invocation) {
		PredictionNode data = callback.invocationEncountered(invocation, context);
		if(data != null){
			excecutionTimeStats.add(data);
		}
		return CONTINUE;
	}
	
	@Override
	public PerformanceVisitor visit(Loop loop) {
		  return new LoopBlockTimeCollector(this,callback,context, loop);
	}
	
	@Override
	public PerformanceVisitor visit(Branching branch) {
		  return new BranchBlockTimeCollector(this,callback,context, branch);
	}

	@Override
	public PerformanceVisitor visit(Try tryStm) {
		  return new TryBlockTimeCollector(this,callback,context, tryStm);
	}

	public PredictionNode generateResults(){return null;}
	
	public void finish(){
		 PredictionNode data = generateResults();
		 if(parent != null && data != null) {
			 parent.excecutionTimeStats.add(data);
		 }
	}
	
  }