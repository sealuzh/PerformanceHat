package eu.cloudwave.wp5.feedback.eclipse.performance.core.builders.participants;

import java.util.Scanner;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
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
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.PerformanceHatExtension;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.AstRoot;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.StaticAstFactory;

/**
 * A FeedbackBuilderParticipant that enables PerformancePlugins to process FeedbackJavaFile's
 * used to integrate the PerformancePlugins into the overall PerformanceHat framework
 * @author Markus Knecht
 */
public class PerformancePluginsParticipant extends AbstractFeedbackBuilderParticipant implements FeedbackBuilderParticipant{

	 private TemplateHandler templateHandler;
	 private final PerformanceHatExtension ext;

	 public PerformancePluginsParticipant(PerformanceHatExtension ext) {
		this.ext = ext;
		this.templateHandler = PerformancePluginActivator.instance(TemplateHandler.class);
	  }
	 
	 private AstRoot createRoot(Object id, CompilationUnit root, FeedbackJavaProject project, FeedbackJavaFile javaFile){
		 if(root == null) return null;
		 //Find the Registry where the Plugin can read and attach tags to the ast
		 TagRegistry reg = TagRegistry.getProjectTagRegistry(project);
		 //Create the tag creator for this file and plugin, so entries can be differentiated by the creating plugin
		 TagCreator crea = reg.getCreatorFor(id, ext);
		 //create the context classe needed by the ast delegator to interact with its enviroment
		 AstContext rootContext = new ProgrammMarkerContextBase(project, javaFile, root, reg, crea ,templateHandler);
		 return StaticAstFactory.createAstRoot(root, rootContext);	
	 }

	 /**
	  * Delegates buildFile calls from the PerformanceHat framework to the PerformancePlugin.
	  * This processes the javaFile with the PerformancePlugin
	  * It is called once per file during a compilation
	  * @param project is the project the javaFile belongs to
	  * @param javaFile is the file to process
	  * @param astRoot is abstract syntax tree corresponding to the javaFile
	  */
	@Override
	public void buildFile(FeedbackJavaProject project, FeedbackJavaFile javaFile, CompilationUnit astRoot/*, CompilationUnit oldAstRoot*/) {
				
		AstRoot newRoot = createRoot(javaFile.getFullPath(), astRoot, project, javaFile);
		// Vom Gescheiterten Versuch einen zweiten Ast zu erhalten um differenz basierte analysen zu machen
		//AstRoot oldRoot = createRoot(new HistoryKey(javaFile.getFullPath()), oldAstRoot, project, null); //History has no file asosiated
		
		//Create the AstDelegator which allows the PerformanceVisitor to process the AST and interact with its context
		//Start the processing
		ext.processPerformanceAst(newRoot/* oldRoot*/);
	}
	
	/**
	 * After a compilation, this method is responsible for cleaning up and deallocating no longer needed data
	 * All the local tag are removed only global tags survive a compilation process
	 * @param project is the project the javaFiles belong to
	 * @param javaFiles are all the files built during this compilation
	 */
	@Override
	public void cleanup(FeedbackJavaProject project, Set<FeedbackJavaFile> javaFiles) throws CoreException {
		//Find the Registry where the Plugin can read and attach tags to the ast
		TagRegistry reg = TagRegistry.getProjectTagRegistry(project);
		//Clear the local tags of each file
		for(FeedbackJavaFile javaFile:javaFiles){
			reg.getCreatorFor(javaFile.getFullPath(), ext).clearAssosiatedLocalTags();
    		// Vom Gescheiterten Versuch einen zweiten Ast zu erhalten um differenz basierte analysen zu machen
			//reg.getCreatorFor(new HistoryKey(javaFile.getFullPath()), ext).clearAssosiatedLocalTags();

		}
	}
}
