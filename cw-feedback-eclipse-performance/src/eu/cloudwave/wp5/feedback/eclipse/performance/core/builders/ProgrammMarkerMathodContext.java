package eu.cloudwave.wp5.feedback.eclipse.performance.core.builders;

import org.eclipse.jdt.core.dom.CompilationUnit;

import eu.cloudwave.wp5.feedback.eclipse.base.infrastructure.template.TemplateHandler;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.FeedbackProject;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaFile;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.feedbackhandler.FeedbackHandlerEclipseClient;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ProgrammMarkerContext;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.MethodDeclaration;

public class ProgrammMarkerMathodContext implements ProgrammMarkerContext{
	private final  MethodDeclaration decl;
	final ProgrammMarkerContext base;
	
	public ProgrammMarkerMathodContext(ProgrammMarkerContext base, MethodDeclaration decl) {
		this.base = base;
		this.decl = decl;
	}

	@Override
	public FeedbackProject getProject() {
		return base.getProject();
	}

	//Todo: temporary will be replaced by datasource mechanism
	@Override
	public FeedbackHandlerEclipseClient getFeedBackClient() {
		return base.getFeedBackClient();
	}

	//Todo: bettersolution is needed
	@Override
	public TemplateHandler getTemplateHandler() {
		return base.getTemplateHandler();
	}

	@Override
	public FeedbackJavaFile getFile() {
		return base.getFile();
	}

	@Override
	public int getLine(int start) {
		return base.getLine(start);
	}

	@Override
	public MethodDeclaration getCurrentMethode() {
		return decl;
	}
	
	

}
