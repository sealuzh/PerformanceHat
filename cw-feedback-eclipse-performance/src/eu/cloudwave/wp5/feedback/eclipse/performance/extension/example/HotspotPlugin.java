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
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.prediction.block.APrediction;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.prediction.block.BlockPrediction;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.prediction.block.LoopPrediction;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.PredictionNode;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.AstRoot;
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
//Todo: Note not Flexibel Enough, needs way to present arbitary tags
public class HotspotPlugin  implements  PerformancePlugin{
	
	  private static final String ID = "eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.HotspotPlugin";
	
	  private static final String AVG_EXECUTION_TIME = "avgExecutionTime";

	  private static final String NAME = "name";
	  private static final String KIND = "kind";
	  private static final String HOTSPOT = "hotspot";
	  private static final String MESSAGE_PATTERN = "Hotspot %s: Average Execution Time of %s is %s.";
	  private static final int DECIMAL_PLACES = 3;
	  private static final String AVG_EXEC_TIME_TAG = "AvgExcecutionTime";
	  public static final String AVG_PRED_TIME_TAG = "AvgPredictionTime";

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
		  	return Collections.singletonList(AVG_PRED_TIME_TAG);
	  }
	  
	  
	  
	 /**
	  * {@inheritDoc}
	  */
	  @Override
	  public void processPerformanceAst(AstRoot ast/*, AstRoot ignoreOldRoot*/) {
		  ast.accept(createPerformanceVisitor(ast.getContext()));
	  }

	  private PerformanceVisitor createPerformanceVisitor(final AstContext rootContext) {
		  //the visitor that gens the marker
		  return new PerformanceVisitor() {
			  
			  private void genericMarkerHandling(IAstNode node, double threshold){
				//Get prediction times for loop (we do not yet measure time for loops so no need to get Measured Time
				  Collection<Object> entry = node.getTags(AVG_PRED_TIME_TAG);
				  //if their is something find highest (for now we show highest prediction)
				  double max = threshold;
				  PredictionNode maxNode = null;
				  for(Object o: entry){
					  if(o instanceof PredictionNode){
						  for(Double time:((PredictionNode) o).getPredictedTime()){
							  if(time >= max){
								  max = time;
								  maxNode = (PredictionNode) o;
							  }
						  }
					  }
				  }
				  
				  //is their at least one appropriate result
				  if(maxNode != null){
					  maxNode.createCriticalMarker(node,rootContext);
				  }
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
				  } else {
					  genericMarkerHandling(method,threshold);
				  }
				  return CONTINUE;		 
			  }

			@Override
			public PerformanceVisitor visit(IAstNode node) {
				  genericMarkerHandling(node,rootContext.getProject().getFeedbackProperties().getDouble(PerformanceFeedbackProperties.TRESHOLD__HOTSPOTS, PerformanceConfigs.DEFAULT_THRESHOLD_HOTSPOTS));
				  return CONTINUE;
			}

			@Override
			public PerformanceVisitor visit(Loop loop) {
				genericMarkerHandling(loop,rootContext.getProject().getFeedbackProperties().getDouble(PerformanceFeedbackProperties.TRESHOLD__LOOPS, PerformanceConfigs.DEFAULT_THRESHOLD_LOOPS));
				return CONTINUE;
			}
			
			
			  
			  
	
		  };
			
	  }
}
