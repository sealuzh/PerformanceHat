package eu.cloudwave.wp5.feedback.eclipse.performance.extension.example;

import eu.cloudwave.wp5.common.dto.AggregatedProcedureMetricsDto;
import eu.cloudwave.wp5.common.model.Procedure;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.MeasurementTag;

public class CollectionSizeTag implements MeasurementTag{

	private final AggregatedProcedureMetricsDto measure;
	
	public CollectionSizeTag(AggregatedProcedureMetricsDto measure) {
		this.measure = measure;
	}

	public Procedure getProcedure() {
		return measure.getProcedure();
	}

	public double getAverageExecutionTime() {
		return measure.getAverageExecutionTime();
	}

}


