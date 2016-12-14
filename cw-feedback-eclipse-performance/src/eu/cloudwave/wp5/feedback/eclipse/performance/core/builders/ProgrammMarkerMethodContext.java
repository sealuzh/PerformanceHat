package eu.cloudwave.wp5.feedback.eclipse.performance.core.builders;

import eu.cloudwave.wp5.feedback.eclipse.base.infrastructure.template.TemplateHandler;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaFile;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaProject;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.tag.TagCreator;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.tag.TagProvider;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.AstContext;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.MethodDeclaration;

/**
 * An extension wrapper that fills the current MethodGetter of a AstContext
 * @author Markus Knecht
 *
 */
public class ProgrammMarkerMethodContext implements AstContext{
	private final  MethodDeclaration decl;
	final AstContext base;
	
	public ProgrammMarkerMethodContext(AstContext base, MethodDeclaration decl) {
		this.base = base;
		this.decl = decl;
	}

	@Override
	public FeedbackJavaProject getProject() {
		return base.getProject();
	}

	@Override
	public TagProvider getTagProvider() {
		return base.getTagProvider();
	}

	@Override
	public TagCreator getTagCreator() {
		return base.getTagCreator();
	}

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
	public MethodDeclaration getCurrentMethod() {
		return decl;
	}
	
	

}
