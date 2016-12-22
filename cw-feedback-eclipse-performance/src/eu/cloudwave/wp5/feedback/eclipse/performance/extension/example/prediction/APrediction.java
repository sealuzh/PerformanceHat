package eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.prediction;

import java.util.Collection;

import com.google.common.collect.Lists;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.PredictionNode;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.PredictionNodeHeader;

public abstract class APrediction implements PredictionNode{
	public final double avgTimeMes;
	public final double avgTimePred;
	
	public APrediction(double avgTimePred, double avgTimeMes) {
		this.avgTimeMes = avgTimeMes;
		this.avgTimePred = avgTimePred;
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
}
