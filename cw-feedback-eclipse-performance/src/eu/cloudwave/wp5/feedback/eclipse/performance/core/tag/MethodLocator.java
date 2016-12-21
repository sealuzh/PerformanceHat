package eu.cloudwave.wp5.feedback.eclipse.performance.core.tag;

import java.util.Arrays;

/**
 * An ADT for MethodLocations
 * By Class Name, Method Name, Argument Types (because of overloads)
 * @author Markus Knecht
 *
 */
public class MethodLocator {
	public final String className;
	public final String methodName;
	public final String[] argumentTypes;
	
	
	public MethodLocator(String className, String methodeName, String[] argumentTypes) {
		this.className = className;
		this.methodName = methodeName;
		this.argumentTypes = argumentTypes;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(argumentTypes);
		result = prime * result + ((className == null) ? 0 : className.hashCode());
		result = prime * result + ((methodName == null) ? 0 : methodName.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MethodLocator other = (MethodLocator) obj;
		if (!Arrays.equals(argumentTypes, other.argumentTypes))
			return false;
		if (className == null) {
			if (other.className != null)
				return false;
		} else if (!className.equals(other.className))
			return false;
		if (methodName == null) {
			if (other.methodName != null)
				return false;
		} else if (!methodName.equals(other.methodName))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "MethodLocator [className=" + className + ", methodName=" + methodName + ", argumentTypes="
				+ Arrays.toString(argumentTypes) + "]";
	}
	
	
	
}
