package eu.cloudwave.wp5.feedback.eclipse.performance.extension.example;

import java.util.List;

import com.google.common.collect.Lists;

import eu.cloudwave.wp5.feedback.eclipse.performance.core.tag.MethodLocator;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ProgrammMarkerContext;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.Invocation;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.LoopStatement;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.timestats.AvgTimeLeafe;
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
		List<Double> tags = invocation.getDoubleTags(CriticalLoopProgrammMarker.AVG_EXEC_TIME_TAG);
		if(tags.isEmpty()) return CONTINUE;
		double avgExecTime = 0.0;
		for(double avgT : tags)avgExecTime+=avgT;
		avgExecTime /= tags.size();
		avgExcecutionTime+= avgExecTime;
		MethodLocator loc = invocation.createCorrespondingMethodLocation();
		excecutionTimeStats.add(new AvgTimeLeafe(loc.methodName, avgExecTime));
		return CONTINUE;
	}
	
	@Override
	public ProgrammMarkerVisitor visit(LoopStatement loop) {
		  Double averageSize = LoopUtils.findNumOfIterations(loop, context);
		  if(averageSize == null) return CONTINUE;
		  if(averageSize == 0) return SKIP_CHILDS;
		  return new LoopBlockTimeCollector(this,callback,context, averageSize, loop);
	}
	
	public abstract void finish();
	
  }