package eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.prediction;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
	
	protected static final int DECIMAL_PLACES = 3;
	protected static double round(double value){
		return round(value,DECIMAL_PLACES);
	}
	protected static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
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
