package eu.cloudwave.wp5.feedback.eclipse.performance.core.tag;

import org.eclipse.jdt.core.dom.ASTNode;

//todo: track source and delete only same participant source
public interface TagCreator {
	public void addAstNodeTag(ASTNode node, String tagName, Object tagValue);
	public void addMethodTag(MethodLocator loc, String tagName, Object tagValue);
	public void clearAssosiatedLocalTags();

}
