package eu.cloudwave.wp5.feedback.eclipse.performance.extension;

import java.util.Collections;
import java.util.List;

import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaFile;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaProject;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.visitor.PerformanceVisitor;

public interface PerformancePlugin {

	default List<String> getProvidedTags(){return Collections.emptyList();}
	default List<String> getRequiredTags(){return Collections.emptyList();}
	default List<String> getOptionalRequiredTags(){return Collections.emptyList();}

	public PerformanceVisitor createPerformanceVisitor(final AstContext rootContext);
}
