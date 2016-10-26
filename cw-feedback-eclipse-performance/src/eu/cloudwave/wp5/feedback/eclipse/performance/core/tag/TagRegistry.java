package eu.cloudwave.wp5.feedback.eclipse.performance.core.tag;

//Todo: make one per File/Compilation Unit
public interface TagRegistry {
	public void addMethodTag(MethodLocator method, String tagName, Object tagValue);
	public void addParameterTag(MethodLocator method, int paramPosition, String tagName, Object tagValue);
	public void addMethodeAnnotationTag(MethodLocator method, String anotationName, /*Simple for now*/ String[] paramValues, String tagName, Object tagValue);

	//Read Methods open
	
}
