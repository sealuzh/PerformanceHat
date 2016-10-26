package eu.cloudwave.wp5.feedback.eclipse.performance.core.builders;

import org.eclipse.jdt.core.dom.CompilationUnit;

import eu.cloudwave.wp5.feedback.eclipse.base.infrastructure.template.TemplateHandler;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.FeedbackProject;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaFile;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.feedbackhandler.FeedbackHandlerEclipseClient;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ProgrammMarkerContext;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.MethodDeclaration;

public class ProgrammMarkerContextBase implements ProgrammMarkerContext{
	private final FeedbackProject project;
	private final FeedbackJavaFile file;
	private final CompilationUnit unit;
	private final FeedbackHandlerEclipseClient fddHandler;
	private final TemplateHandler template;

	
	public ProgrammMarkerContextBase(FeedbackProject project, FeedbackJavaFile file, CompilationUnit unit,
			FeedbackHandlerEclipseClient fddHandler, TemplateHandler template) {
		this.project = project;
		this.file = file;
		this.unit = unit;
		this.fddHandler = fddHandler;
		this.template = template;
	}

	@Override
	public FeedbackProject getProject() {
		return project;
	}

	//Todo: temporary will be replaced by datasource mechanism
	@Override
	public FeedbackHandlerEclipseClient getFeedBackClient() {
		return fddHandler;
	}

	//Todo: bettersolution is needed
	@Override
	public TemplateHandler getTemplateHandler() {
		return template;
	}

	@Override
	public FeedbackJavaFile getFile() {
		return file;
	}

	@Override
	public int getLine(int start) {
		return unit.getLineNumber(start);
	}

	@Override
	public MethodDeclaration getCurrentMethode() {
		return null;
	}
	
	

}
