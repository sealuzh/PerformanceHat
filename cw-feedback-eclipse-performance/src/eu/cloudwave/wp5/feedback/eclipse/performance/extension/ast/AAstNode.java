package eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast;

import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.dom.ASTNode;

import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaFile;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.FeedbackMarkerType;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.MarkerPosition;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.MarkerSpecification;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ProgrammMarkerContext;

public abstract class AAstNode<T extends ASTNode> implements IAstNode {
	
	protected final ProgrammMarkerContext ctx;
	protected final T inner;
	
	public AAstNode(T inner, ProgrammMarkerContext ctx) {
		this.ctx = ctx;
		this.inner = inner;
	}

	public T getEclipseAstNode() {
		return inner;
	}

	public MethodDeclaration getCurrentMethode(){
		return ctx.getCurrentMethode();
	}
			
	@Override
	public void markWarning(String id, FeedbackMarkerType type, String message, Map<String, Object> additionalAttributes){
		 final FeedbackJavaFile javaFile =  ctx.getFile();
		 final int start = getStartPosition();
		 MarkerSpecification markerSpecification = new MarkerSpecification(id, new MarkerPosition(ctx.getLine(start), getStartPosition(), getEndPosition()), IMarker.SEVERITY_WARNING, type, message,additionalAttributes);
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
	  protected abstract int getStartPosition();

	  /**
	   * Returns the end position (in the respective Java file) of the wrapped object.
	   * 
	   * @return the end position (in the respective Java file) of the wrapped object.
	   */
	  protected abstract int getEndPosition();

	@Override
	public int hashCode() {
		return inner.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof AAstNode))return false;
		return inner.equals(((AAstNode)obj).inner);
	}
	  
	  

}
