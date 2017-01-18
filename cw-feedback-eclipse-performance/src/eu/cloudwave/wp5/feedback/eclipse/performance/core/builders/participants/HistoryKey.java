/*    		// Vom Gescheiterten Versuch einen zweiten Ast zu erhalten um differenz basierte analysen zu machen

package eu.cloudwave.wp5.feedback.eclipse.performance.core.builders.participants;

import org.eclipse.core.runtime.IPath;

public final class HistoryKey {
	private final IPath origPath;

	public HistoryKey(IPath origPath) {
		this.origPath = origPath;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((origPath == null) ? 0 : origPath.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof HistoryKey))
			return false;
		HistoryKey other = (HistoryKey) obj;
		if (origPath == null) {
			if (other.origPath != null)
				return false;
		} else if (!origPath.equals(other.origPath))
			return false;
		return true;
	}
}
*/