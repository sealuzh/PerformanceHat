package eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.prediction.block;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import eu.cloudwave.wp5.common.util.Numbers;
import eu.cloudwave.wp5.common.util.TimeValues;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.MarkerAttributes;
import eu.cloudwave.wp5.feedback.eclipse.performance.Ids;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.markers.PerformanceMarkerTypes;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.AstContext;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.PredictionNode;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.PredictionNodeHeader;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.IAstNode;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.Loop;

/**
 * A PredictionNode implementation for for and foreach blocks
 * @see PredictionNode
 * @author Markus Knecht
 *
 */
public class LoopPrediction extends APrediction{

	public final double avgIters;
	public final APrediction body;
	public final APrediction headerTime;

	public LoopPrediction(double avgTimePred, double avgTimeMes, double avgIters, APrediction body, APrediction headerTime) {
		super(avgTimePred,avgTimeMes);
		this.avgIters = avgIters;
		this.headerTime = headerTime;
		this.body = body;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getText(){
		return "loop";
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<PredictionNode> getChildren(){
		return Lists.newArrayList(headerTime,body);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<String> getPredictedText() {
		return getPredictedTime().stream().map(p -> {
			if(Double.isNaN(avgIters)){
				return round(p)+"ms = "+round(p)+"ms";
			}else {
				return avgIters+"*"+round(p/avgIters)+"ms";
			}
		}).collect(Collectors.toList());
	}

	
	private static final String METHOD_MESSAGE_PATTERN = "Critical method: Average Total Time is %s.";
	private static final String AVG_TOTAL = "avgTotal";
	private static final String PROCEDURE_EXECUTIONS = "procedureExecutions";
	private static final String SINGLE_LINE_MODE = "singleLineMode";
	private static final String METHOD = "method";
	
	private static final String AVG_TIME_PER_ITERATION = "avgTimePerIteration";
	private static final String AVG_INTERATIONS = "avgInterations";
	private static final String LOOP = "loop";

	private static final String LOOP_MESSAGE_PATTERN = "Critical Loop: Average total time is %s (Average iterations: %s).";

	@Override
	public void createCriticalMarker(IAstNode target, AstContext astContext) {
		  if(!(target instanceof Loop)) return;
		  //build the parts of the marker info
		  final String avgIterationsText =Double.isNaN(avgIters)?" - ":Numbers.round(avgIters, DECIMAL_PLACES)+"";
		  double maxLoop = Math.max(avgTimePred,avgTimeMes);
		  final String avgTotalExecTimeText = TimeValues.toText(maxLoop, DECIMAL_PLACES);
		  final String msg = String.format(LOOP_MESSAGE_PATTERN, avgTotalExecTimeText, avgIterationsText);
		  final Map<String, Object> context = Maps.newHashMap();
		  context.put(AVG_TOTAL, avgTotalExecTimeText);
		  context.put(AVG_INTERATIONS, avgIterationsText);
		  double maxLoopBody = Math.max(body.avgTimePred,body.avgTimeMes);
		  context.put(AVG_TIME_PER_ITERATION, TimeValues.toText(maxLoopBody, DECIMAL_PLACES));
		  context.put(PROCEDURE_EXECUTIONS,this);
		  context.put(SINGLE_LINE_MODE, avgTimeMes == avgTimePred);
		  //put them together
		  final String desc = astContext.getTemplateHandler().getContent(LOOP, context);
		  
		  final Map<String, Object> additionalAttributes = Maps.newHashMap();
		  additionalAttributes.put(MarkerAttributes.DESCRIPTION, desc);
		  target.markWarning(Ids.PERFORMANCE_MARKER,PerformanceMarkerTypes.HOTSPOT,msg, additionalAttributes);
	
	}
	
	
}
