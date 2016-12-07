package eu.cloudwave.wp5.feedback.eclipse.performance.extension.example;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import eu.cloudwave.wp5.feedback.eclipse.performance.PerformancePluginActivator;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.tag.MethodLocator;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.AstContext;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.PerformancePlugin;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.feedbackhandler.FeedbackHandlerEclipseClient;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.MethodOccurence;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.ParameterDeclaration;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.visitor.PerformanceVisitor;

public class FeedbackHandlerPlugin implements PerformancePlugin{
	 
	private static final String ID = "eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.FeedbackHandlerPlugin";
	private static final String COLLECTION_SIZE_TAG = "CollectionSize";
	private static final String AVG_EXEC_TIME_TAG = "AvgExcecutionTime";
	  
	private final FeedbackHandlerEclipseClient fddClient = PerformancePluginActivator.instance(FeedbackHandlerEclipseClient.class);
	
	@Override
	public String getId() {
		return ID;
	}	  
	
	public List<String> getProvidedTags(){
		return Lists.asList(COLLECTION_SIZE_TAG,AVG_EXEC_TIME_TAG, new String[]{});
	}


	@Override
	public PerformanceVisitor createPerformanceVisitor(AstContext rootContext) {
		return new PerformanceVisitor() {

			@Override
			public PerformanceVisitor visit(MethodOccurence method) {
				MethodLocator loc = method.createCorrespondingMethodLocation();
				Double measure = fddClient.avgExecTime(rootContext.getProject(),loc.className, loc.methodName, loc.argumentTypes);
				if(measure != null) method.attachTag(AVG_EXEC_TIME_TAG, measure);
			    Double averageSize = fddClient.collectionSize(rootContext.getProject(),loc.className, loc.methodName, loc.argumentTypes, "");
			    if(averageSize != null) method.attachTag(COLLECTION_SIZE_TAG, averageSize);

			    return CONTINUE;
			}

			@Override
			public PerformanceVisitor visit(ParameterDeclaration decl) {
				MethodLocator loc = decl.getCurrentMethode().createCorrespondingMethodLocation();
				Double averageSize = fddClient.collectionSize(rootContext.getProject(),loc.className, loc.methodName, loc.argumentTypes, decl.getPosition()+"");
			    if(averageSize != null) decl.attachTag(COLLECTION_SIZE_TAG, averageSize); 
			    return CONTINUE;
			}
			
			
		};
	}
	  
}
