package eu.cloudwave.wp5.feedback.eclipse.performance.core.builders;

import org.eclipse.jdt.core.dom.CompilationUnit;

import eu.cloudwave.wp5.feedback.eclipse.base.infrastructure.template.TemplateHandler;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.FeedbackProject;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaFile;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.tag.TagCreator;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.tag.TagProvider;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.AstContext;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.MethodDeclaration;

public class ProgrammMarkerContextBase implements AstContext{
	private final FeedbackProject project;
	private final FeedbackJavaFile file;
	private final CompilationUnit unit;
	private final TagProvider tagProv;
	private final TagCreator tagCrea;	
	private final TemplateHandler template;

	
	public ProgrammMarkerContextBase(FeedbackProject project, FeedbackJavaFile file, CompilationUnit unit,
			TagProvider tagProv, TagCreator tagCrea, TemplateHandler template) {
		this.project = project;
		this.file = file;
		this.unit = unit;
		this.tagCrea = tagCrea;
		this.tagProv = tagProv;

		this.template = template;
	}

	@Override
	public FeedbackProject getProject() {
		return project;
	}

	
	
	
	@Override
	public TagProvider getTagProvider() {
		return tagProv;
	}

	@Override
	public TagCreator getTagCreator() {
		return tagCrea;
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
