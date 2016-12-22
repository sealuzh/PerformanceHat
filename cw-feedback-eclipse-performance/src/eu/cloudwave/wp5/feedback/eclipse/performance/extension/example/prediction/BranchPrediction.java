package eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.prediction;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.stream.Collectors;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.PredictionNode;

/**
 * A PredictionNode implementation for if, else, case and conditional block
 * @see PredictionNode
 * @author Markus Knecht
 *
 */
public class BranchPrediction extends APrediction{

	private final int index;
	private final double fraction;
	private final Collection<PredictionNode> childs;
	
	public BranchPrediction(int index, double fraction,  double avgTimePred, double avgTimeMes, Collection<PredictionNode> childs) {
		super(avgTimePred,avgTimeMes);
		this.index = index;
		this.fraction = fraction;
		this.childs = childs;
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getText(){
		return "branch "+index;
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
		return getPredictedTime().stream().map(p -> {
			double part = new BigDecimal((fraction*100)).setScale(2, RoundingMode.HALF_UP).doubleValue();
			double t = (p/1000);
			return part+"% of "+(t/fraction)+"s = "+ t+"s";
		}).collect(Collectors.toList());
	}
}
