package eu.cloudwave.wp5.feedback.eclipse.performance.core.builders;

import org.eclipse.jdt.core.dom.CompilationUnit;

import eu.cloudwave.wp5.feedback.eclipse.base.infrastructure.template.TemplateHandler;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaFile;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaProject;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.tag.TagCreator;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.tag.TagProvider;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.AstContext;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.MethodDeclaration;

/**
 * This class captures all the Dependency needed by the Performance Ast to work properly
 * @author Markus Knecht
 *
 */
public class ProgrammMarkerContextBase implements AstContext{
	private final FeedbackJavaProject project;		//Current Project
	private final FeedbackJavaFile file;			//Current File
	private final CompilationUnit unit;				//Current Compilation Unit
	private final TagProvider tagProv;				//Current TagProvider
	private final TagCreator tagCrea;				//Current TagCreator
	private final TemplateHandler template;			//Current TemplateHandler (TODO:do not like this one, if time find better solution (no priority thoug))

	public ProgrammMarkerContextBase(FeedbackJavaProject project, FeedbackJavaFile file, CompilationUnit unit, TagProvider tagProv, TagCreator tagCrea, TemplateHandler template) {
		this.project = project;
		this.file = file;
		this.unit = unit;
		this.tagCrea = tagCrea;
		this.tagProv = tagProv;
		this.template = template;
	}

	@Override
	public FeedbackJavaProject getProject() {
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

	//TODO: better solution prefered
	@Override
	public TemplateHandler getTemplateHandler() {
		return template;
	}

	@Override
	public FeedbackJavaFile getFile() {
		return file;
	}

	//Helper to transfer fileposition to line
	@Override
	public int getLine(int start) {
		return unit.getLineNumber(start);
	}

	//Is only provided by the MethodBased Subclass
	@Override
	public MethodDeclaration getCurrentMethod() {
		return null;
	}
	
	

}
