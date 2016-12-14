package eu.cloudwave.wp5.feedback.eclipse.performance.core.tag;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.IAstNode;

/**
 * A TagCreator thats specific to a File and Plugin
 * It allows to create tags which then could be queried over the corresponding TagProvider
 * Created tags can be cleared when no longer needed
 * @author Markus Knecht
 *
 */
public interface TagCreator {
	/**
	 * Attaches a Tag to an AstNode
	 * @param node is the node to which the tag is attached
	 * @param tagName is the name of the tag
	 * @param tagValue is the value of the tag
	 */
	public void addAstNodeTag(IAstNode node, String tagName, Object tagValue);
	
	/**
	 * Attaches a Tag to a Method (This tag is Global and will not be cleared by clearAssosiatedLocalTags)
	 * @param loc is the location of the method
	 * @param tagName is the name of the tag
	 * @param tagValue is the value of the tag
	 */
	public void addMethodTag(MethodLocator loc, String tagName, Object tagValue);
	
	/**
	 * Removes all local (addAstNodeTag) tags
	 */
	public void clearAssosiatedLocalTags();

}
