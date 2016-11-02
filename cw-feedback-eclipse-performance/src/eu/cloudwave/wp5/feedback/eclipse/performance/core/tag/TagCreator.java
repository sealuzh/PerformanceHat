package eu.cloudwave.wp5.feedback.eclipse.performance.core.tag;

import org.eclipse.jdt.core.dom.ASTNode;

public interface TagCreator {
	public void addMethodTag(MethodLocator method, String tagName, Object tagValue);
	public void addParameterTag(MethodLocator method, int paramPosition, String tagName, Object tagValue);
	//public void addMethodAnnotationTag(MethodLocator method, String anotationName, /*Simple for now*/ String[] paramValues, String tagName, Object tagValue);
	public void addAstNodeTag(ASTNode node, String tagName, Object tagValue);
	public void clearAssosiatedTags();
}
