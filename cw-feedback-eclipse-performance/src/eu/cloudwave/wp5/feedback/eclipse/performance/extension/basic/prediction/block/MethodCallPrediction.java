package eu.cloudwave.wp5.feedback.eclipse.performance.extension.basic.prediction.block;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import eu.cloudwave.wp5.feedback.eclipse.performance.core.tag.MethodLocator;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.PredictionNode;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.PredictionNodeHeader;

/**
 * A PredictionNode implementation for method call leafs
 * @see PredictionNode
 * @author Markus Knecht
 *
 */
public class MethodCallPrediction extends APrediction{
	private final MethodLocator loc;
	
	public MethodCallPrediction(MethodLocator loc, double avgTimePred, double avgTimeMes) {
		super(avgTimePred,avgTimeMes);
		this.loc = loc;
	}

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getText(){
		return loc.methodName;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<PredictionNode> getChildren(){
		return Collections.emptyList();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<String> getPredictedText() {
		return getPredictedTime().stream().map(p -> round(p)+"ms").collect(Collectors.toList());
	}

}
