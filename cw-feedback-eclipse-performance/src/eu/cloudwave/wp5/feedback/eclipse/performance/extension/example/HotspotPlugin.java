package eu.cloudwave.wp5.feedback.eclipse.performance.extension.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.internal.corext.callhierarchy.CallHierarchy;
import org.eclipse.jdt.internal.corext.callhierarchy.MethodCall;
import org.eclipse.jdt.internal.corext.callhierarchy.MethodWrapper;
import org.eclipse.ui.actions.GlobalBuildAction;
import org.eclipse.ui.internal.ide.actions.BuildUtilities;

import com.google.common.collect.Maps;

import eu.cloudwave.wp5.common.util.Joiners;
import eu.cloudwave.wp5.common.util.Numbers;
import eu.cloudwave.wp5.common.util.TimeValues;
import eu.cloudwave.wp5.feedback.eclipse.base.infrastructure.template.TemplateHandler;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.FeedbackMarkerType;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.MarkerAttributes;
import eu.cloudwave.wp5.feedback.eclipse.performance.Ids;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.markers.PerformanceMarkerTypes;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.properties.PerformanceFeedbackProperties;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.tag.MethodLocator;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.PerformancePlugin;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.AstContext;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.prediction.BlockPrediction;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.prediction.LoopPrediction;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.PredictionNode;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.IAstNode;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.Loop;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.MethodDeclaration;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.MethodOccurence;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.visitor.PerformanceVisitor;
import eu.cloudwave.wp5.feedback.eclipse.performance.infrastructure.config.PerformanceConfigs;

public class HotspotPlugin  implements  PerformancePlugin{
	
	  private static final String AVG_EXECUTION_TIME = "avgExecutionTime";
	  private static final String NAME = "name";
	  private static final String KIND = "kind";
	  private static final String HOTSPOT = "hotspot";
	  private static final String MESSAGE_PATTERN = "Hotspot %s: Average Execution Time of %s is %s.";
	  private static final int DECIMAL_PLACES = 3;
	  private static final String AVG_EXEC_TIME_TAG = "AvgExcecutionTime";

	  private static final String PROCEDURE_EXECUTIONS = "procedureExecutions";
	  private static final String AVG_TIME_PER_ITERATION = "avgTimePerIteration";
	  private static final String AVG_INTERATIONS = "avgInterations";
	  private static final String AVG_TOTAL = "avgTotal";
	  private static final String LOOP = "loop";
	  private static final String METHOD = "method";

	  private static final String LOOP_MESSAGE_PATTERN = "Critical Loop: Average Total Time is %s (Average Iterations: %s).";
	  private static final String METHOD_MESSAGE_PATTERN = "Critical method: Average Total Time is %s.";


	  @Override
	  public List<String> getRequiredTags() {
		  	return Collections.singletonList(AVG_EXEC_TIME_TAG);
	  }
	  
	  @Override
	  public List<String> getOptionalRequiredTags() {
		  	return Collections.singletonList(BlockPredictionPlugin.AVG_PRED_TIME_TAG);
	  }
	  
	@Override
	public PerformanceVisitor createPerformanceVisitor(final AstContext rootContext) {
		return new PerformanceVisitor() {
			
			@Override
			public PerformanceVisitor visit(Loop loop) {
				List<Object> entry = loop.getTags(BlockPredictionPlugin.AVG_PRED_TIME_TAG);
				if(entry.size() == 1){ //for now exactly one
					 PredictionNode n = (PredictionNode) entry.get(0);
					 if(n instanceof LoopPrediction){
						LoopPrediction ln = (LoopPrediction)n;
						final double predThreshold = rootContext.getProject().getFeedbackProperties().getDouble(PerformanceFeedbackProperties.TRESHOLD__LOOPS, PerformanceConfigs.DEFAULT_THRESHOLD_LOOPS);
						if (ln.getPredictedTime() >= predThreshold) createCriticalLoopMarker(loop,rootContext.getTemplateHandler(),ln);
					 }
				 }
				return CONTINUE;
			}

			
			
			@Override
			public PerformanceVisitor visit(MethodOccurence method) {
				 final double threshold = rootContext.getProject().getFeedbackProperties().getDouble(PerformanceFeedbackProperties.TRESHOLD__HOTSPOTS, PerformanceConfigs.DEFAULT_THRESHOLD_HOTSPOTS);
				 double averagedTime = 0;
				 List<Object> entry = method.getTags(BlockPredictionPlugin.AVG_PRED_TIME_TAG);
				 int i = 0;
				 for (double avgExecutionTime :method.getDoubleTags(AVG_EXEC_TIME_TAG)) {
					 averagedTime+=avgExecutionTime;
					 i++;
			     }
				 averagedTime /= i;
				 if(averagedTime >= threshold){
					 final MethodLocator loc = method.createCorrespondingMethodLocation();
					 final String valueAsText = TimeValues.toText(averagedTime, DECIMAL_PLACES);
					 final String kind = method.getMethodKind();
					 final String message = String.format(MESSAGE_PATTERN, kind, loc.methodName, valueAsText);
					 final Map<String, Object> context = Maps.newHashMap();
				     context.put(KIND, kind);
				     context.put(NAME,loc.methodName);
				     context.put(AVG_EXECUTION_TIME, valueAsText);
				     final String description = rootContext.getTemplateHandler().getContent(HOTSPOT, context);
					 final Map<String, Object> additionalAttributes = Maps.newHashMap();
					 additionalAttributes.put(MarkerAttributes.DESCRIPTION, description);
					 additionalAttributes.put(MarkerAttributes.CLASS_NAME, loc.className);
					 additionalAttributes.put(MarkerAttributes.PROCEDURE_NAME, loc.methodName);
					 additionalAttributes.put(MarkerAttributes.ARGUMENTS, Joiners.onComma(loc.argumentTypes));
					 method.markWarning(Ids.PERFORMANCE_MARKER,PerformanceMarkerTypes.HOTSPOT,message, additionalAttributes);
				 }else if(entry.size() == 1){ //for now exactly one
					 PredictionNode n = (PredictionNode) entry.get(0);
					 if(n instanceof BlockPrediction){
						BlockPrediction bn = (BlockPrediction)n;
						final double predThreshold = rootContext.getProject().getFeedbackProperties().getDouble(PerformanceFeedbackProperties.TRESHOLD__LOOPS, PerformanceConfigs.DEFAULT_THRESHOLD_LOOPS);
						if (bn.getPredictedTime() >= predThreshold) {
							createCriticalMetodMarker(method,rootContext.getTemplateHandler(),bn);
								
							 if(method instanceof MethodDeclaration){
								 MethodDeclaration decl = (MethodDeclaration)method;
								 IMember mem = (IMember)((org.eclipse.jdt.core.dom.MethodDeclaration)method.getEclipseAstNode()).resolveBinding().getJavaElement();
									//todo: new tag called average Prediction Time Tag + Build Member

							 }
						}
					 
					 }
					
				 }
				 //Todo: Not nice at all look what we can evacuate
				 
				 return CONTINUE;
			}

		};
		
	}
	
	private static void createMarker(IAstNode node, String message, String desc, FeedbackMarkerType type){
        final Map<String, Object> additionalAttributes = Maps.newHashMap();
		additionalAttributes.put(MarkerAttributes.DESCRIPTION, desc);
		node.markWarning(Ids.PERFORMANCE_MARKER,type,message, additionalAttributes);
	}

	private static void createCriticalLoopMarker(Loop loop, TemplateHandler template, LoopPrediction loopExecutionSummary ){
          final String avgIterationsText = new Double(Numbers.round(loopExecutionSummary.avgIters, DECIMAL_PLACES)).toString();
          final String avgTotalExecTimeText = TimeValues.toText(loopExecutionSummary.getPredictedTime(), DECIMAL_PLACES);
          final String msg = String.format(LOOP_MESSAGE_PATTERN, avgTotalExecTimeText, avgIterationsText);
          final Map<String, Object> context = Maps.newHashMap();
          context.put(AVG_TOTAL, avgTotalExecTimeText);
          context.put(AVG_INTERATIONS, avgIterationsText);
          context.put(AVG_TIME_PER_ITERATION, TimeValues.toText(loopExecutionSummary.body.getPredictedTime(), DECIMAL_PLACES));
          context.put(PROCEDURE_EXECUTIONS,loopExecutionSummary);
          final String desc = template.getContent(LOOP, context);
          createMarker(loop,msg,desc,PerformanceMarkerTypes.COLLECTION_SIZE);
	}
	
	private static void createCriticalMetodMarker(MethodOccurence decl, TemplateHandler template,PredictionNode procedureExecutionSummary ){
        final String avgTotalExecTimeText = TimeValues.toText(procedureExecutionSummary.getPredictedTime(), DECIMAL_PLACES);
        final String msg = String.format(METHOD_MESSAGE_PATTERN, avgTotalExecTimeText);
        final Map<String, Object> context = Maps.newHashMap();
        context.put(AVG_TOTAL, avgTotalExecTimeText);
        context.put(PROCEDURE_EXECUTIONS,procedureExecutionSummary);
        final String desc = template.getContent(METHOD, context);
        createMarker(decl,msg,desc,PerformanceMarkerTypes.COLLECTION_SIZE);
	 }

}
