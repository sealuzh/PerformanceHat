package eu.cloudwave.wp5.feedback.eclipse.performance.core.tag;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;

public interface TagProvider {
	public List<Object> getTagsForNode(ASTNode node, String tagName);
	//public List<Object> getTagsForField(FieldLocator loc, String tagName);
	public List<Object> getTagsForMethod(MethodLocator loc, String tagName);

}
