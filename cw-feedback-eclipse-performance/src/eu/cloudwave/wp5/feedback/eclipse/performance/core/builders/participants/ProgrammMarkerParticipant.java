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
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ProgrammMarker;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ProgrammMarkerContext;

public class ProgrammMarkerParticipant extends AbstractFeedbackBuilderParticipant implements FeedbackBuilderParticipant{

	 private TemplateHandler templateHandler;
	 private final ProgrammMarker ext;

	 public ProgrammMarkerParticipant(ProgrammMarker ext) {
	    this.ext = ext;
		this.templateHandler = PerformancePluginActivator.instance(TemplateHandler.class);
	  }

	@Override
	public void buildFile(FeedbackJavaProject project, FeedbackJavaFile javaFile, CompilationUnit astRoot) {
		TagRegistry reg = TagRegistry.getProjectTagRegistry(project);
		TagCreator crea = reg.getCreatorFor(javaFile);
		ProgrammMarkerContext rootContext = new ProgrammMarkerContextBase(project, javaFile, astRoot, reg, crea ,templateHandler);
		astRoot.accept( new AstDelegator(ext.createFileVisitor(rootContext),rootContext)) ;
	}
	
	@Override
	public void prepare(FeedbackJavaProject project, Set<FeedbackJavaFile> javaFiles) throws CoreException {
		TagRegistry reg = TagRegistry.getProjectTagRegistry(project);
		for(FeedbackJavaFile javaFile:javaFiles){
			TagCreator crea = reg.getCreatorFor(javaFile);
			//crea.clearAssosiatedTags();
		}
	}
	
	

}
