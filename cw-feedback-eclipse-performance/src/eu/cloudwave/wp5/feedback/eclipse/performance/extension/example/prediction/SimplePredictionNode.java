package eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.prediction;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

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
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.MethodOccurence;

public class SimplePredictionNode implements PredictionNode{

	private final Double prediction;
	private final String predictionName;

	public SimplePredictionNode(Double prediction, String predictionName) {
		this.prediction = prediction;
		this.predictionName = predictionName;
	}

	@Override
	public PredictionNodeHeader getHeader() {
		return new PredictionNodeHeader() {
			@Override
			public Collection<String> getText() {
				return Collections.singleton("");
			}
		};
	}

	

	@Override
	public boolean isDataNode() {
		return false;
	}

	@Override
	public String getText() {
		return predictionName;
	}

	@Override
	public Collection<Double> getPredictedTime() {
		return Collections.singleton(prediction);
	}

	//Override if something else is preferred
	protected double round(double value){
		if (getDecimalPlaces() < 0) throw new IllegalArgumentException();
	    return Numbers.round(value,getDecimalPlaces());
	}
	
	//Override if something else is preferred
	protected int getDecimalPlaces(){
		return 3;
	}
	
	//Override if something else is preferred
	@Override
	public Collection<String> getPredictedText() {
		return Collections.singleton(round(prediction)+"ms"); 
	}

	@Override
	public Collection<PredictionNode> getChildren() {
		return Collections.emptyList();
	}

	private static final String METHOD_MESSAGE_PATTERN = "Critical %s: Average total time is %s.";
	private static final String AVG_TOTAL = "avgTotal";
	private static final String NODE_NAME = "nodeName";
	private static final String PROCEDURE_EXECUTIONS = "procedureExecutions";
	private static final String SINGLE_LINE_MODE = "singleLineMode";
	private static final String SIMPLE = "simple";
	
	
	@Override
	public void createCriticalMarker(IAstNode target, AstContext astContext) {
		  if(!(target instanceof MethodOccurence)) return;

		  //build the parts of the marker info
		  final String avgTotalExecTimeText = TimeValues.toText(prediction, getDecimalPlaces());
		  final String msg = String.format(METHOD_MESSAGE_PATTERN, "method", avgTotalExecTimeText);
		  final Map<String, Object> context = Maps.newHashMap();
		  context.put(NODE_NAME, "method");
		  context.put(AVG_TOTAL, avgTotalExecTimeText);
		  context.put(PROCEDURE_EXECUTIONS,this);
		  context.put(SINGLE_LINE_MODE, true);

		  //put them together
		  final String desc = astContext.getTemplateHandler().getContent(SIMPLE, context);
		  //Create the Marker
		
		  final Map<String, Object> additionalAttributes = Maps.newHashMap();
		  additionalAttributes.put(MarkerAttributes.DESCRIPTION, desc);
		  target.markWarning(Ids.PERFORMANCE_MARKER,PerformanceMarkerTypes.HOTSPOT,msg, additionalAttributes);
	

	}
}
