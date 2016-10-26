package eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.jdt.ui.actions.ProjectActionGroup;

import eu.cloudwave.wp5.common.model.Procedure;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.FeedbackProject;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.feedbackhandler.FeedbackHandlerClient;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.feedbackhandler.FeedbackHandlerEclipseClient;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ProgrammMarkerContext;

public abstract class AMethodRelated<T> extends AAstNode<T> {
	public abstract Procedure createCorrelatingProcedure();

	public AMethodRelated(T inner, ProgrammMarkerContext ctx) {
		super(inner, ctx);
	}
	
	private List<Double> metrics = null;
	
	@Override
	public List<Double> getDoubleTags(String name) {
		if(name.equals("AvgExcecutionTime")){
			if (metrics == null) {
				Procedure prod = createCorrelatingProcedure();;
				String[] args = prod.getArguments().toArray(new String[prod.getArguments().size()]);
				Double measure = ctx.getFeedBackClient().avgExecTime(ctx.getProject(),prod.getClassName(),prod.getName(), args);
				if(measure == null) return Collections.emptyList();
				metrics = Collections.singletonList(measure);
				
	        }
	        return metrics;
		}
		return Collections.emptyList();
	}

	@Override
	public List<Object> getTags(String name) {
		return getDoubleTags(name).stream().map(t -> (Object)t).collect(Collectors.toList());
	}
}
