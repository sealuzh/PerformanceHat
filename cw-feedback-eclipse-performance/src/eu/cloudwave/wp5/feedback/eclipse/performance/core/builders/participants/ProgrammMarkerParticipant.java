package eu.cloudwave.wp5.feedback.eclipse.performance.core.builders.participants;

import org.eclipse.jdt.core.dom.CompilationUnit;

import eu.cloudwave.wp5.feedback.eclipse.base.core.builders.participants.AbstractFeedbackBuilderParticipant;
import eu.cloudwave.wp5.feedback.eclipse.base.core.builders.participants.FeedbackBuilderParticipant;
import eu.cloudwave.wp5.feedback.eclipse.base.infrastructure.template.TemplateHandler;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaFile;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaProject;
import eu.cloudwave.wp5.feedback.eclipse.performance.PerformancePluginActivator;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.builders.AstDelegator;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.builders.ProgrammMarkerContextBase;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.feedbackhandler.FeedbackHandlerEclipseClient;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ProgrammMarkerContext;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ProgrammMarker;

public class ProgrammMarkerParticipant extends AbstractFeedbackBuilderParticipant implements FeedbackBuilderParticipant{

	 private FeedbackHandlerEclipseClient feedbackHandlerClient;
	 private TemplateHandler templateHandler;
	 private final ProgrammMarker ext;

	 public ProgrammMarkerParticipant(ProgrammMarker ext) {
	    this.ext = ext;
		this.feedbackHandlerClient = PerformancePluginActivator.instance(FeedbackHandlerEclipseClient.class);
		this.templateHandler = PerformancePluginActivator.instance(TemplateHandler.class);
	  }


	@Override
	protected void buildFile(FeedbackJavaProject project, FeedbackJavaFile javaFile, CompilationUnit astRoot) {
		ProgrammMarkerContext rootContext = new ProgrammMarkerContextBase(project, javaFile, astRoot, feedbackHandlerClient,templateHandler);
		astRoot.accept( new AstDelegator(ext.createFileVisitor(rootContext),rootContext)) ;
	}

}
