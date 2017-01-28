package eu.cloudwave.wp5.feedback.eclipse.performance.extension.basic.prediction.block;

import java.util.Collection;
import java.util.Collections;

import com.google.common.collect.Lists;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.PredictionNodeHeader;

public class PredictionHeader implements PredictionNodeHeader{

	public static PredictionHeader instance = new PredictionHeader();
	
	@Override
	public Collection<String> getText() {
		return Lists.newArrayList("predictions preferred", "measurements preferred");
	}

	
}
