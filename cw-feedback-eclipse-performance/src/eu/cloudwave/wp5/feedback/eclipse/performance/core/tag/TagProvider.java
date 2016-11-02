package eu.cloudwave.wp5.feedback.eclipse.performance.core.tag;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;

public interface TagProvider {
	public List<Object> getTagsForMethod(MethodLocator method, String tagName);
	public List<Object> getTagsForParam(MethodLocator method,  int paramPosition, String tagName);
	//public Object[] getTagsForAnnotation(MethodLocator method,  int paramPosition, String tagName);
	public List<Object> getTagsForNode(ASTNode node, String tagName);
}
