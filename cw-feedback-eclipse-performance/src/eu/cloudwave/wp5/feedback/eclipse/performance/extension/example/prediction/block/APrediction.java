package eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.prediction.block;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import eu.cloudwave.wp5.common.util.Numbers;
import eu.cloudwave.wp5.common.util.TimeValues;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.FeedbackMarkerType;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.MarkerAttributes;
import eu.cloudwave.wp5.feedback.eclipse.performance.Ids;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.markers.PerformanceMarkerTypes;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.AstContext;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.PredictionNode;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.PredictionNodeHeader;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.IAstNode;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.Loop;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.MethodDeclaration;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.MethodOccurence;

public abstract class APrediction implements PredictionNode{
	public final double avgTimeMes;
	public final double avgTimePred;
	
	public APrediction(double avgTimePred, double avgTimeMes) {
		this.avgTimeMes = avgTimeMes;
		this.avgTimePred = avgTimePred;
	}
	
	protected static final int DECIMAL_PLACES = 3;
	protected static double round(double value){
		return round(value,DECIMAL_PLACES);
	}
	protected static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();
	    return Numbers.round(value,places);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isDataNode(){
		return true;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Double> getPredictedTime(){
		return Lists.newArrayList(avgTimePred, avgTimeMes);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PredictionNodeHeader getHeader() {
		return PredictionHeader.instance;
	}
	
	private static final String METHOD_MESSAGE_PATTERN = "Critical %s: Average total time is %s.";
	private static final String AVG_TOTAL = "avgTotal";
	private static final String NODE_NAME = "nodeName";
	private static final String PROCEDURE_EXECUTIONS = "procedureExecutions";
	private static final String SINGLE_LINE_MODE = "singleLineMode";
	private static final String STRUCTURAL = "structural";
	
	
	@Override
	public void createCriticalMarker(IAstNode target, AstContext astContext) {
		  if(!(target instanceof MethodOccurence)) return;

		  //build the parts of the marker info
		  double maxProcedur = Math.max(avgTimePred,avgTimeMes);
		  final String avgTotalExecTimeText = TimeValues.toText(maxProcedur, DECIMAL_PLACES);
		  final String msg = String.format(METHOD_MESSAGE_PATTERN, "method", avgTotalExecTimeText);
		  final Map<String, Object> context = Maps.newHashMap();
		  context.put(NODE_NAME, "method");
		  context.put(AVG_TOTAL, avgTotalExecTimeText);
		  context.put(PROCEDURE_EXECUTIONS,this);
		  context.put(SINGLE_LINE_MODE, avgTimeMes == avgTimePred);

		  //put them together
		  final String desc = astContext.getTemplateHandler().getContent(STRUCTURAL, context);
		  //Create the Marker
		
		  final Map<String, Object> additionalAttributes = Maps.newHashMap();
		  additionalAttributes.put(MarkerAttributes.DESCRIPTION, desc);
		  target.markWarning(Ids.PERFORMANCE_MARKER,PerformanceMarkerTypes.HOTSPOT,msg, additionalAttributes);
	

	}
	
}
