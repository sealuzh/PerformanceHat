package eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.prediction;

import java.util.Collection;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.PredictionNode;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.PredictionNodeHeader;

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
}
