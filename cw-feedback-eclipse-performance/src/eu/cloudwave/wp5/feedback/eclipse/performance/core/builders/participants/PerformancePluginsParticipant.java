package eu.cloudwave.wp5.feedback.eclipse.performance.core.builders.participants;

import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.dom.CompilationUnit;

import eu.cloudwave.wp5.feedback.eclipse.base.core.builders.participants.AbstractFeedbackBuilderParticipant;
import eu.cloudwave.wp5.feedback.eclipse.base.core.builders.participants.FeedbackBuilderParticipant;
import eu.cloudwave.wp5.feedback.eclipse.base.infrastructure.template.TemplateHandler;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaFile;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaProject;
import eu.cloudwave.wp5.feedback.eclipse.performance.PerformancePluginActivator;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.builders.AstDelegator;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.builders.ProgrammMarkerContextBase;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.tag.TagCreator;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.tag.TagRegistry;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.AstContext;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.PerformancePlugin;

public class PerformancePluginsParticipant extends AbstractFeedbackBuilderParticipant implements FeedbackBuilderParticipant{

	 private TemplateHandler templateHandler;
	 private final PerformancePlugin ext;

	 public PerformancePluginsParticipant(PerformancePlugin ext) {
		this.ext = ext;
		this.templateHandler = PerformancePluginActivator.instance(TemplateHandler.class);
	  }

	@Override
	public void buildFile(FeedbackJavaProject project, FeedbackJavaFile javaFile, CompilationUnit astRoot) {
		TagRegistry reg = TagRegistry.getProjectTagRegistry(project);
			TagCreator crea = reg.getCreatorFor(javaFile, ext);
			AstContext rootContext = new ProgrammMarkerContextBase(project, javaFile, astRoot, reg, crea ,templateHandler);
			astRoot.accept(new AstDelegator(ext.createPerformanceVisitor(rootContext),rootContext) );
	}
	
	@Override
	public void cleanup(FeedbackJavaProject project, Set<FeedbackJavaFile> javaFiles) throws CoreException {
		TagRegistry reg = TagRegistry.getProjectTagRegistry(project);
		for(FeedbackJavaFile javaFile:javaFiles){
			reg.getCreatorFor(javaFile, ext).clearAssosiatedLocalTags();
		}
	}
	
	

}
