package eu.cloudwave.wp5.feedback.eclipse.performance.extension;

public class MethodLocator {
	public final String className;
	public final String methodeName;
	public final String[] argumentTypes;
	
	
	public MethodLocator(String className, String methodeName, String[] argumentTypes) {
		super();
		this.className = className;
		this.methodeName = methodeName;
		this.argumentTypes = argumentTypes;
	}
	
}
