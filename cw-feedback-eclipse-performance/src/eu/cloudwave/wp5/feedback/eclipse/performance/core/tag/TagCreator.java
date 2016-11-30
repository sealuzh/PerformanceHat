package eu.cloudwave.wp5.feedback.eclipse.performance.core.tag;

import org.eclipse.jdt.core.dom.ASTNode;

public interface TagCreator {
	public void addAstNodeTag(ASTNode node, String tagName, Object tagValue);
	public void addMethodTag(MethodLocator loc, String tagName, Object tagValue);
	//public void addFieldTag(FieldLocator loc, String tagName, Object tagValue);
	public void clearAssosiatedPublicTags();
	public void clearAssosiatedLocalTags();

}
