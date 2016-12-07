package eu.cloudwave.wp5.feedback.eclipse.performance.core.tag;

import java.util.Collection;

import org.eclipse.jdt.core.dom.ASTNode;

public interface TagProvider {
	public Collection<Object> getTagsForNode(ASTNode node, String tagName);
	public Object getTagsForNode(String pluginId, ASTNode node, String tagName);

	//public List<Object> getTagsForField(FieldLocator loc, String tagName);
	public Collection<Object> getTagsForMethod(MethodLocator loc, String tagName);
	public Object getTagsForMethod(String pluginId, MethodLocator loc, String tagName);


}
