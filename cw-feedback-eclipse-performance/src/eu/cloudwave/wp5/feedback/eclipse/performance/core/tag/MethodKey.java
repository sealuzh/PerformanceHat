package eu.cloudwave.wp5.feedback.eclipse.performance.core.tag;

import org.eclipse.jdt.core.dom.ASTNode;

final class MethodKey implements CompositeKey{
	public final String tagName;
	public final MethodLocator loc;
	public MethodKey(String tagName, MethodLocator loc) {
		this.tagName = tagName;
		this.loc = loc;
	}
	
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