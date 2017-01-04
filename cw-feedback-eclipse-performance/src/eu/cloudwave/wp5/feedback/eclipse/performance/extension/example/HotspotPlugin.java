package eu.cloudwave.wp5.feedback.eclipse.performance.extension.example;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.AstContext;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.PerformancePlugin;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.prediction.APrediction;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.prediction.BlockPrediction;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.prediction.LoopPrediction;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.IAstNode;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.Loop;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.MethodOccurence;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.visitor.PerformanceVisitor;
import eu.cloudwave.wp5.feedback.eclipse.performance.infrastructure.config.PerformanceConfigs;

/**
 * A Marker Plugin, that reads Average Excecution Time and Average Prediction Time Tags
 * and generates a eclipse Marker if it is over a specific Threshhold
 * it does this for Loops, MethodDeclarations, MethodCalls and Constructor invocations
 * @author Markus
 *
 */
public class HotspotPlugin  implements  PerformancePlugin{
	
	  private static final String ID = "eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.HotspotPlugin";
	
	  private static final String AVG_EXECUTION_TIME = "avgExecutionTime";
	  private static final String SINGLE_LINE_MODE = "singleLineMode";

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

	  /**
	   * {@inheritDoc}
	   */	
	  @Override
	  public String getId() {
		  	return ID;
	  }

	  /**
	   * {@inheritDoc}
	   */	  
	  @Override
	  public List<String> getRequiredTags() {
		  	return Collections.singletonList(AVG_EXEC_TIME_TAG);
	  }
	  
	  /**
	   * {@inheritDoc}
	   */	  
	  @Override
	  public List<String> getOptionalRequiredTags() {
		  	return Collections.singletonList(BlockPredictionPlugin.AVG_PRED_TIME_TAG);
	  }
	  /**
	   * {@inheritDoc}
	   */ 
	  @Override
	  public PerformanceVisitor createPerformanceVisitor(final AstContext rootContext) {
		  //the visitor that gens the marker
		  return new PerformanceVisitor() {
			  
			  @Override
			  public PerformanceVisitor visit(Loop loop) {
				  //Get prediction times for loop (we do not yet measure time for loops so no need to get Measured Time
				  Collection<Object> entry = loop.getTags(BlockPredictionPlugin.AVG_PRED_TIME_TAG);
				  //if their is something find highest (for now we show highest prediction)
				  double max = rootContext.getProject().getFeedbackProperties().getDouble(PerformanceFeedbackProperties.TRESHOLD__LOOPS, PerformanceConfigs.DEFAULT_THRESHOLD_LOOPS);
				  LoopPrediction maxNode = null;
				  for(Object o: entry){
					  //get the correct type
					  if(o instanceof LoopPrediction){
						  LoopPrediction ln = (LoopPrediction)o;
						  //check if its better then best and threshold
						  if(Math.max(ln.avgTimePred, ln.avgTimeMes) >= max){
							  maxNode = ln;
							  max = Math.max(ln.avgTimePred, ln.avgTimeMes);
						  }
					  }
				  }
				  
				  //is their at least one appropriate result
				  if(maxNode != null){
					  //Create the marker
					  createCriticalLoopMarker(loop,rootContext.getTemplateHandler(),maxNode);
				  }
				  return CONTINUE;
			  }

			@Override
			  public PerformanceVisitor visit(MethodOccurence method) {
				  
				  //find the maximum measured time for now (their will max be one measurement per Datasource)
				  double maxAvgTime = Double.MIN_VALUE;
				  for (double avgExecutionTime :method.getDoubleTags(AVG_EXEC_TIME_TAG)) {
					  if(avgExecutionTime > maxAvgTime){
						  maxAvgTime =  avgExecutionTime; 
					  }
				  }
				  //we prefer Mesurement over prediction
				  //get the threshhold
				  final double threshold = rootContext.getProject().getFeedbackProperties().getDouble(PerformanceFeedbackProperties.TRESHOLD__HOTSPOTS, PerformanceConfigs.DEFAULT_THRESHOLD_HOTSPOTS);
				  if(maxAvgTime >= threshold){ //note  Double.MIN_VALUE; always < threshold (if nothing was found)
					  //TODO: Make own Method
					  //get the method
					  final MethodLocator loc = method.createCorrespondingMethodLocation();
					  //build the parts of the marker info
					  final String valueAsText = TimeValues.toText(maxAvgTime, DECIMAL_PLACES);
					  final String kind = method.getMethodKind();
					  final String message = String.format(MESSAGE_PATTERN, kind, loc.methodName, valueAsText);
					  final Map<String, Object> context = Maps.newHashMap();
					  context.put(KIND, kind);
					  context.put(NAME,loc.methodName);
					  context.put(AVG_EXECUTION_TIME, valueAsText);
					  //put them together
					  final String description = rootContext.getTemplateHandler().getContent(HOTSPOT, context);
					  //add extra infos
					  final Map<String, Object> additionalAttributes = Maps.newHashMap();
					  additionalAttributes.put(MarkerAttributes.DESCRIPTION, description);
					  additionalAttributes.put(MarkerAttributes.CLASS_NAME, loc.className);
					  additionalAttributes.put(MarkerAttributes.PROCEDURE_NAME, loc.methodName);
					  additionalAttributes.put(MarkerAttributes.ARGUMENTS, Joiners.onComma(loc.argumentTypes));
					  //create the Marker
					  method.markWarning(Ids.PERFORMANCE_MARKER,PerformanceMarkerTypes.HOTSPOT,message, additionalAttributes);
					  return CONTINUE;
				  }
				  
				  //Get prediction times for loop (we do not yet measure time for loops so no need to get Measured Time
				  Collection<Object> entry = method.getTags(BlockPredictionPlugin.AVG_PRED_TIME_TAG);
				  //if their is something find highest (for now we show highest prediction)
				  double max = rootContext.getProject().getFeedbackProperties().getDouble(PerformanceFeedbackProperties.TRESHOLD__LOOPS, PerformanceConfigs.DEFAULT_THRESHOLD_LOOPS);
				  BlockPrediction maxNode = null;
				  for(Object o: entry){
					  //get the correct type
					  if(o instanceof BlockPrediction){
						  BlockPrediction bn = (BlockPrediction)o;
					  
						  //check if its better
						  if(Math.max(bn.avgTimePred, bn.avgTimeMes) >= max){
							  maxNode = bn;
							  max = Math.max(bn.avgTimePred, bn.avgTimeMes);
						  }
					  }
				  }
				  
				  //is their at least one appropriate result
				  if(maxNode != null){
					  //Create the marker
					  createCriticalMethodMarker(method,rootContext.getTemplateHandler(),maxNode);
				  }
				  return CONTINUE;				 
			  }
	
		  };
			
	  }
	  
	  //Helper to generate an unspecific Marker
	  private static void createMarker(IAstNode node, String message, String desc, FeedbackMarkerType type){
		  final Map<String, Object> additionalAttributes = Maps.newHashMap();
		  additionalAttributes.put(MarkerAttributes.DESCRIPTION, desc);
		  node.markWarning(Ids.PERFORMANCE_MARKER,type,message, additionalAttributes);
	  }

	  //Helper to collect the Loop info data
	  private static void createCriticalLoopMarker(Loop loop, TemplateHandler template, LoopPrediction loopExecutionSummary ){
		  //build the parts of the marker info
		  final String avgIterationsText = new Double(Numbers.round(loopExecutionSummary.avgIters, DECIMAL_PLACES)).toString();
		  double maxLoop = Math.max(loopExecutionSummary.avgTimePred,loopExecutionSummary.avgTimeMes);
		  final String avgTotalExecTimeText = TimeValues.toText(maxLoop, DECIMAL_PLACES);
		  final String msg = String.format(LOOP_MESSAGE_PATTERN, avgTotalExecTimeText, avgIterationsText);
		  final Map<String, Object> context = Maps.newHashMap();
		  context.put(AVG_TOTAL, avgTotalExecTimeText);
		  context.put(AVG_INTERATIONS, avgIterationsText);
		  double maxLoopBody = Math.max(loopExecutionSummary.body.avgTimePred,loopExecutionSummary.body.avgTimeMes);
		  context.put(AVG_TIME_PER_ITERATION, TimeValues.toText(maxLoopBody, DECIMAL_PLACES));
		  context.put(PROCEDURE_EXECUTIONS,loopExecutionSummary);
		  context.put(SINGLE_LINE_MODE, loopExecutionSummary.avgTimeMes == loopExecutionSummary.avgTimePred);
		  //put them together
		  final String desc = template.getContent(LOOP, context);
		  //Create the Marker
		  createMarker(loop,msg,desc,PerformanceMarkerTypes.COLLECTION_SIZE);
	  }
	  
	  //Helper to collect the Method info data		
	  private static void createCriticalMethodMarker(MethodOccurence decl, TemplateHandler template, APrediction procedureExecutionSummary ){
		  //build the parts of the marker info
		  double maxProcedur = Math.max(procedureExecutionSummary.avgTimePred,procedureExecutionSummary.avgTimeMes);
		  final String avgTotalExecTimeText = TimeValues.toText(maxProcedur, DECIMAL_PLACES);
		  final String msg = String.format(METHOD_MESSAGE_PATTERN, avgTotalExecTimeText);
		  final Map<String, Object> context = Maps.newHashMap();
		  context.put(AVG_TOTAL, avgTotalExecTimeText);
		  context.put(PROCEDURE_EXECUTIONS,procedureExecutionSummary);
		  context.put(SINGLE_LINE_MODE, procedureExecutionSummary.avgTimeMes == procedureExecutionSummary.avgTimePred);

		  //put them together
		  final String desc = template.getContent(METHOD, context);
		  //Create the Marker
		  createMarker(decl,msg,desc,PerformanceMarkerTypes.COLLECTION_SIZE);
	  }

}
