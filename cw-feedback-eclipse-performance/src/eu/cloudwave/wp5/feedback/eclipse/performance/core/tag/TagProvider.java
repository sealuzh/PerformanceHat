package eu.cloudwave.wp5.feedback.eclipse.performance.core.tag;

import java.util.Collection;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.IAstNode;

/**
 * Allows to query for Tags
 * @author Markus Knecht
 */
public interface TagProvider {
	//TODO: Add methods to get Tuple<Value,Plugin> or Triple<Value,Plugin,TagName>
	
	/**
	 * Gets the attached Tags of a node.
	 * This includes the tags from all plugins
	 * @param node is the node at which it is attached
	 * @param tagName is the name of the tag to look up
	 * @return a list of tag values
	 */
	public Collection<Object> getTagsForNode(IAstNode node, String tagName);
	
	
	/**
	 * Gets the attached Tag of a node.
	 * This returns only the tag of a specified generator
	 * @param pluginId is the id of the plugin that generated the tag
	 * @param node is the node at which it is attached
	 * @param tagName is the name of the tag to look up
	 * @return the tag value
	 */
	public Object getTagsForNode(String pluginId,IAstNode node, String tagName);

	//TODO
	//public List<Object> getTagsForClass(ClassLocator loc, String tagName);	
	//public List<Object> getTagsForField(FieldLocator loc, String tagName);
	
	/**
	 * Gets the attached Tags of a method.
	 * This includes the tags from all plugins
	 * @param loc is the method at which it is attached
	 * @param tagName is the name of the tag to look up
	 * @return a list of tag values
	 */
	public Collection<Object> getTagsForMethod(MethodLocator loc, String tagName);
	
	/**
	 * Gets the attached Tag of a method.
	 * This returns only the tag of a specified generator
	 * @param pluginId is the id of the plugin that generated the tag
	 * @param loc is the method at which it is attached
	 * @param tagName is the name of the tag to look up
	 * @return the tag value
	 */
	public Object getTagsForMethod(String pluginId, MethodLocator loc, String tagName);


}
