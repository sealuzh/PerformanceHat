package eu.cloudwave.wp5.feedback.eclipse.performance.core.tag;

/**
 * A simply ADT class with all the boilerplate needed from java
 * Represents the Immutable Tuple Tag name, MethodLocator
 * @author Markus Knecht
 */
final class MethodKey implements CompositeKey{
	public final String tagName;
	public final MethodLocator loc;
	public MethodKey(String tagName, MethodLocator loc) {
		this.tagName = tagName;
		this.loc = loc;
	}
	
	//extra info about the ADT see CompositeKey
	@Override
	public boolean isGlobalKey() {
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + loc.hashCode();
		result = prime * result + tagName.hashCode();
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof MethodKey)) return false;
		MethodKey other = (MethodKey) obj;
		if (!loc.equals(other.loc)) return false;
		if (!tagName.equals(other.tagName)) return false;
		return true;
	}
}