package eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor;

import java.util.Collection;

/**
 * A node to represent a Prediction made by the BlockTimePrediction Framework
 * Each Prediction has a name/text a predicted average Time as well as some Child nodes from which the prediction was made
 * @author Markus Knecht
 */
public interface PredictionNodeHeader {
	
	/**
	 * The Prediction column texts
	 * @return the column header
	 */
	public Collection<String> getText();
}
