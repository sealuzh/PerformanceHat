package eu.cloudwave.wp5.feedback.eclipse.performance.extension;

public interface TagRegistry {
	public void addMethodTag(MethodLocator method, MeasurementTag tag);
	public void addParameterTag(MethodLocator method, int paramPosition, MeasurementTag tag);
	public void addMethodeAnnotationTag(MethodLocator method, String anotationName, /*Simple for now*/ String[] paramValues, MeasurementTag tag);

	//Read Methods open
	
}
