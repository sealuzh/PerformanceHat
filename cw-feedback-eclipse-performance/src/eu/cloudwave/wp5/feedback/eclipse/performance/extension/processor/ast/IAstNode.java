package eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.jdt.core.dom.ASTNode;

import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.FeedbackMarkerType;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.AstContext;

/**
 * The root interface presenting the Ast Structure for Performance Plugins
 * For creating instances the StaticAstFactory should be used.
 * @author Markus Knecht
 */
public interface IAstNode {
	
	/**
	 * Gets the currently active MethodeDeclaration node
	 * @return MethodeDeclaration node
	 */
	public MethodDeclaration getCurrentMethod();
	
	/**
	 * Get backing Eclipse Ast node
	 * @return eclipse ASTNode
	 */
	public ASTNode getEclipseAstNode();
	
	/**
	 * Attach a tag to the node so it can later be read by getTags
	 * @param name of the tag
	 * @param value of the tag
	 */
	public void attachTag(String name, Object value);
	
	/**
	 * Get the tags attached by attachTag
	 * @param name of the tag
	 * @return one value per tag (their can be one value per plugin)
	 */
    public Collection<Object> getTags(String name);
	
    /**
     * A convenient way to get the tags as double if the receiver nows that they are doubles
     * @see getTags
	 * @param name of the tag
	 * @return one double value per tag (their can be one value per plugin)
     */
	default public Collection<Double> getDoubleTags(String name){
		return getTags(name).stream().map(t -> {
			if(t instanceof Double) return (Double) t;		//if already is double just return
			else return Double.parseDouble(t.toString());	//else try to interpret as double
		}).collect(Collectors.toList());
	}
	
	/**
     * A convenient way to get the tags as int if the receiver nows that they are doubles
     * @see getTags
	 * @param name of the tag
	 * @return one int value per tag (their can be one value per plugin)
     */
	default public Collection<Integer> getIntTags(String name){
		return getTags(name).stream().map(t -> {
			if(t instanceof Integer) return (Integer) t;	//if already is int just return
			else return Integer.parseInt(t.toString());		//else try to interpret as int
		}).collect(Collectors.toList());
	}
	
	/**
	 * Attaches an eclipse warning to the Ast Node, displayed in the ui
	 * @param id of the marker
	 * @param type of the marker
	 * @param message the primary message
	 * @param additionalAttributes used to render additional infos
	 */
	public void markWarning(String id, FeedbackMarkerType type, String message, Map<String, Object> additionalAttributes);

	/**
	 * Helper Class to allow arbitary eclipse Ast nodes tobe IAstNodes
	 * Should only be used if their is no concrete node
	 * @see StaticAstFactory
	 * @author Markus Knecht
	 */
	static class NodeWrapper extends AAstNode<org.eclipse.jdt.core.dom.ASTNode>{
		public NodeWrapper(org.eclipse.jdt.core.dom.ASTNode inner, AstContext ctx) {
			super(inner, ctx);
		}
	}
}
