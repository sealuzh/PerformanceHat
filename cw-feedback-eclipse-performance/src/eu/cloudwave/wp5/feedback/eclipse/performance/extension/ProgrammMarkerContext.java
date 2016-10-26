package eu.cloudwave.wp5.feedback.eclipse.performance.extension;

import eu.cloudwave.wp5.feedback.eclipse.base.infrastructure.template.TemplateHandler;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.FeedbackProject;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaFile;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.feedbackhandler.FeedbackHandlerEclipseClient;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.MethodDeclaration;

//Will Provide Marker Add utility stuff
public interface ProgrammMarkerContext {

	FeedbackProject getProject();
	FeedbackHandlerEclipseClient getFeedBackClient();
	TemplateHandler getTemplateHandler();
	FeedbackJavaFile getFile();
	MethodDeclaration getCurrentMethode();
	int getLine(int start);

}
