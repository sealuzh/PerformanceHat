package eu.cloudwave.wp5.feedback.eclipse.performance.extension;

import eu.cloudwave.wp5.feedback.eclipse.base.infrastructure.template.TemplateHandler;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.FeedbackProject;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaFile;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.tag.TagCreator;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.tag.TagProvider;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.MethodDeclaration;

//Will Provide Marker Add utility stuff
public interface AstContext {

	TagProvider getTagProvider();
	TagCreator getTagCreator();
	FeedbackProject getProject();
	
	TemplateHandler getTemplateHandler();
	FeedbackJavaFile getFile();
	MethodDeclaration getCurrentMethode();
	int getLine(int start);

}
