package eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast;

import com.google.common.base.Optional;

import eu.cloudwave.wp5.common.model.Procedure;

public interface LoopStatement extends IAstNode{
	
	 /**
	   * Holds the information about the source of a collection.
	   */
	  public static class CollectionSource {
	    private static final String EMPTY = "";

	    private Procedure procedure;
	    private String position;

	    public CollectionSource(final Procedure procedure) {
	      this.procedure = procedure;
	      this.position = EMPTY;
	    }

	    public CollectionSource(final Procedure procedure, final int position) {
	      this.procedure = procedure;
	      this.position = String.valueOf(position);
	    }

	    public Procedure getProcedure() {
	      return procedure;
	    }

	    public String getPosition() {
	      return position;
	    }
	  }
	
	/**
	   * Determines the source of the collection that is iterated in the given foreach-loop. The source is either a
	   * parameter or a return value of a procedure (method or constructor). This information is required to fetch
	   * information about the average size of the collection from the feedback handler.
	   * 
	   * @return
	   */
	  public Optional<CollectionSource> getSourceCollection();
}
