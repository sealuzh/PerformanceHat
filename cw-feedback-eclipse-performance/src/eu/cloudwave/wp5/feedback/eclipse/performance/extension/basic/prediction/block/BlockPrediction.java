package eu.cloudwave.wp5.feedback.eclipse.performance.extension.basic.prediction.block;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import eu.cloudwave.wp5.common.util.TimeValues;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.MarkerAttributes;
import eu.cloudwave.wp5.feedback.eclipse.performance.Ids;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.markers.PerformanceMarkerTypes;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.AstContext;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.PredictionNode;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.IAstNode;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.MethodDeclaration;

/**
 * A PredictionNode implementation for a basic unspecified Block
 * @see PredictionNode
 * @author Markus Knecht
 *
 */
public class BlockPrediction extends APrediction{

	private final String text;
	private final Collection<PredictionNode> childs;
	
	public BlockPrediction(String text, double avgTimePred, double avgTimeMes, Collection<PredictionNode> childs) {
		super(avgTimePred, avgTimeMes);
		this.text = text;
		this.childs = childs;
	}
	
	public BlockPrediction(String text, double avgTimeMes, double avgTimePred,  PredictionNode... childs) {
		this(text,avgTimeMes,avgTimePred,Lists.newArrayList(childs));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getText(){
		return text;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<PredictionNode> getChildren(){
		return childs;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<String> getPredictedText() {
		return getPredictedTime().stream().map(p -> round(p)+"ms").collect(Collectors.toList());
	}


	
	
}
