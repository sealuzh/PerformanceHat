package eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.prediction;

import java.util.Collection;

import com.google.common.collect.Lists;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.PredictionNode;

/**
 * A PredictionNode implementation for a basic unspecified Block
 * @see PredictionNode
 * @author Markus Knecht
 *
 */
public class BlockPrediction implements PredictionNode{

	private final String text;
	private final double avgTime;
	private final Collection<PredictionNode> childs;
	
	public BlockPrediction(String text, double avgTime, Collection<PredictionNode> childs) {
		this.text = text;
		this.avgTime = avgTime;
		this.childs = childs;
	}
	
	public BlockPrediction(String text, double avgTime, PredictionNode... childs) {
		this(text,avgTime,Lists.newArrayList(childs));
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
	public String getText(){
		return text;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getPredictedTime(){
		return avgTime;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<PredictionNode> getChildren(){
		return childs;
	}
}
