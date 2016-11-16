package eu.cloudwave.wp5.feedback.eclipse.performance.extension.example;

import java.util.List;

import com.google.common.collect.Lists;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ProgrammMarkerContext;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.Branching;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.Invocation;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.Loop;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.Try;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.timestats.AvgTimeNode;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.visitor.ProgrammMarkerVisitor;

//Todo: Comment
abstract class BlockTimeCollector extends ProgrammMarkerVisitor{
	protected final BlockTimeCollectorCallback callback;
	protected final BlockTimeCollector parent;
	protected final ProgrammMarkerContext context;
	protected double avgExcecutionTime = 0.0;
	protected List<AvgTimeNode> excecutionTimeStats = Lists.newArrayList();

	protected BlockTimeCollector(BlockTimeCollector parent, BlockTimeCollectorCallback callback, ProgrammMarkerContext context) {
		this.parent = parent;
		this.callback = callback;
		this.context = context;
	}
	
	protected BlockTimeCollector(BlockTimeCollectorCallback callback,ProgrammMarkerContext context) {
		this(null,callback, context);
	}

	@Override
	public ProgrammMarkerVisitor visit(Invocation invocation) {
		AvgTimeNode data = callback.invocationEncountered(invocation, context);
		if(data != null){
			avgExcecutionTime+= data.getAvgTime();
			excecutionTimeStats.add(data);
		}
		return CONTINUE;
	}
	
	@Override
	public ProgrammMarkerVisitor visit(Loop loop) {
		  return new LoopBlockTimeCollector(this,callback,context, loop);
	}
	
	@Override
	public ProgrammMarkerVisitor visit(Branching branch) {
		  return new BranchBlockTimeCollector(this,callback,context, branch);
	}

	@Override
	public ProgrammMarkerVisitor visit(Try tryStm) {
		  return new TryBlockTimeCollector(this,callback,context, tryStm);
	}

	public AvgTimeNode generateResults(){return null;}
	
	public void finish(){
		 AvgTimeNode data = generateResults();
		 if(parent != null && data != null) {
			 parent.avgExcecutionTime+=data.getAvgTime();
			 parent.excecutionTimeStats.add(data);
		 }
	}
	
  }