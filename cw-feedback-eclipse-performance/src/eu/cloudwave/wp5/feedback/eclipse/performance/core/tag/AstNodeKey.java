package eu.cloudwave.wp5.feedback.eclipse.performance.core.tag;

import org.eclipse.jdt.core.dom.ASTNode;

/**
 * A simply ADT class with all the boilerplate needed from java
 * Represents the Immutable Tuple Tag name, AtsNode
 * @author Markus Knecht
 */
final class AstNodeKey implements CompositeKey{
	public final String tagName;
	public final ASTNode node;
	public AstNodeKey(String tagName, ASTNode node) {
		this.tagName = tagName;
		this.node = node;
	}
	
	//extra info about the ADT see CompositeKey
	@Override
	public boolean isGlobalKey() {
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + node.hashCode();
		result = prime * result + tagName.hashCode();
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof AstNodeKey)) return false;
		AstNodeKey other = (AstNodeKey) obj;
		if (!node.equals(other.node)) return false;
		if (!tagName.equals(other.tagName)) return false;
		return true;
	}
}