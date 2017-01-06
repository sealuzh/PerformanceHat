package eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast;

import java.util.Collection;
import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.dom.ASTNode;

import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaFile;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.FeedbackMarkerType;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.MarkerPosition;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.MarkerSpecification;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.AstContext;

/**
 * An abstract version of the IAstNode interface that predefine some common Methods
 * @author Markus Knecht
 * @param <T> is the concrete backing node
 */
public abstract class AAstNode<T extends ASTNode> implements IAstNode {
	//the context of the node
	protected final AstContext ctx;
	//The backing ast
	protected final T inner;
	
	AAstNode(T inner, AstContext ctx) {
		this.ctx = ctx;
		this.inner = inner;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T getEclipseAstNode() {
		return inner;
	}


	/**
	 * {@inheritDoc}
	 */	
	@Override
	public MethodDeclaration getCurrentMethod(){
		return ctx.getCurrentMethod();
	}
			
	/**
	 * {@inheritDoc}
	 */	
	@Override
	public void attachTag(String name, Object value) {
		//just forward to the tag creator
		ctx.getTagCreator().addAstNodeTag(this, name, value);
	}

	/**
	 * {@inheritDoc}
	 */	
	@Override
	public Collection<Object> getTags(String name) {
		//just forward to the tag creator
		return ctx.getTagProvider().getTagsForNode(this, name);
	}

	/**
	 * {@inheritDoc}
	 */	
	@Override
	public void markWarning(String id, FeedbackMarkerType type, String message, Map<String, Object> additionalAttributes){
		 //get the file
		 final FeedbackJavaFile javaFile =  ctx.getFile();
		 //get the position
		 final int start = getStartPosition();
		 //create the marker specification
		 MarkerSpecification markerSpecification = new MarkerSpecification(id, new MarkerPosition(ctx.getLine(start), getStartPosition(), getEndPosition()), IMarker.SEVERITY_WARNING, type, message,additionalAttributes);
		 //try to generate the marker
		 try {
		      javaFile.addMarker(markerSpecification);
		    }
		    catch (final CoreException e) {
		      e.printStackTrace();
		    }
	 }
	
	/**
	  * Returns the start position (in the respective Java file) of the wrapped object.
     * 
     * @return the start position (in the respective Java file) of the wrapped object.
     */
	protected int getStartPosition() {
		return inner.getStartPosition();
	}

	  /**
	   * Returns the end position (in the respective Java file) of the wrapped object.
	   * 
	   * @return the end position (in the respective Java file) of the wrapped object.
	   */
	protected int getEndPosition() {
		return getStartPosition()+inner.getLength();
	}

	@Override
	public final int hashCode() {
		return inner.hashCode();
	}

	@SuppressWarnings("rawtypes")
	@Override
	//Equality means in our context represent same node and not same representation
	//Theoretically an own seperate quality would be better but then custom hash sets would be needed
	public final boolean equals(Object obj) {
		if(!(obj instanceof AAstNode))return false;
		//just forward to inner node: Note: This only works when same node is same instance in eclipse ast
		//the Performance builder makes sure that per compilation the ast is only generated once per file
		//		this allows equals to work as specified
		// More stabler alternatives would need to traverse the whole sub Ast of the node and compare elem by elem
		return inner.equals(((AAstNode)obj).inner);
	}
	  
	  

}
