package eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast;

import java.util.List;

import com.google.common.base.Optional;

public interface Loop extends IAstNode{
	
	
	/**
	   * Determines the source of the collection that is iterated in the given foreach-loop. The source is either a
	   * parameter or a return value of a procedure (method or constructor). This information is required to fetch
	   * information about the average size of the collection from the feedback handler.
	   * 
	   * @return the node representing the collection over which we loop (or none if it can't be found or is not a collection)
	   */
	  public Optional<IAstNode> getSourceNode();
	  
	  /**
	   * Gets the number of iteration if they are statically known and the algorithm can figure it out
	   * @return the number of iterations or none if they can't be figured out
	   */
	  public Optional<Integer> getIterations();

	  /**
	   * Gets all the nodes representing the initialization part (runned once)
	   * @return
	   */
	  public List<IAstNode> getInitNodes();
	  
	  /**
	   * Gets the Loop body node
	   * @return the loop body node
	   */
	  //TODO: Figure out if its always a block
	  public IAstNode getBody();
}
