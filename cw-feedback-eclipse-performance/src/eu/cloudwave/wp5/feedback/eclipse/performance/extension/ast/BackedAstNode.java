package eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast;

public interface BackedAstNode<E> extends IAstNode {

	public E getEclipseAstNode();
	
}
