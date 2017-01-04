package eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast;

import java.util.Collections;
import java.util.List;

import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.SimpleName;

import com.google.common.base.Optional;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.AstContext;

/**
 * A IAstNode for ForEach loops
 * @author Markus Knecht
 *
 */
public class ForEach extends AAstNode<EnhancedForStatement> implements Loop{
		
	ForEach(EnhancedForStatement foreachStatement,AstContext ctx) {
		super(foreachStatement,ctx);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected int getStartPosition() {
		return inner.getParameter().getStartPosition();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected int getEndPosition() {
		  final org.eclipse.jdt.core.dom.Expression expression = inner.getExpression();
          return expression.getStartPosition() + expression.getLength();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Optional<Integer> getIterations() {
		return Optional.absent();
	}

	/**
	   * Determines the source of the collection that is iterated in the given foreach-loop. The source is either a
	   * parameter or a return value of a procedure (method or constructor). This information is required to fetch
	   * information about the average size of the collection from the feedback handler.
	   * @return
	   */
	  //todo: do rewire CollectionSource to Something that can have tags, like a param or a variable/Vlaue and then use tag mechanism
	  public Optional<IAstNode> getSourceNode() {
	    final org.eclipse.jdt.core.dom.Expression expression = inner.getExpression();

	    // Case 1: the for loop contains a variable; 'for(Object item : items)'
	    if (expression instanceof SimpleName) {
	      return LoopAnalysisHelper.resolveName(this, (SimpleName)expression, ctx);
	    }
	    // Case 2: the for loop contains a method invocation; example: 'for(Object item : getItems())'
	    else if (expression instanceof org.eclipse.jdt.core.dom.MethodInvocation) {
	    	String accessor =  ((org.eclipse.jdt.core.dom.MethodInvocation)expression).getName().getIdentifier();
	    	//Simple Map support
	    	if(accessor.equals("keySet") || accessor.equals("entrySet") || accessor.equals("values")){
	    		final org.eclipse.jdt.core.dom.Expression reciver = ((org.eclipse.jdt.core.dom.MethodInvocation)expression).getExpression();
	    		// Case 1: the for loop contains a variable; 'for(Object item : items)'
	    	    if (reciver instanceof SimpleName) {
	    	      return LoopAnalysisHelper.resolveName(this, (SimpleName)reciver, ctx);
	    		// Case 2: the for loop contains a method invocation; example: 'for(Object item : getItems())'
	    	    } else if (reciver instanceof org.eclipse.jdt.core.dom.MethodInvocation) {
	    	    	return Optional.of(StaticAstFactory.createMethodInvocation((org.eclipse.jdt.core.dom.MethodInvocation)reciver,ctx)); 
	    	    }
	    	} 
	    	return Optional.of(StaticAstFactory.createMethodInvocation((org.eclipse.jdt.core.dom.MethodInvocation)expression,ctx));
	    }
	    return Optional.absent();
	  }
	  
	  /**
	   * {@inheritDoc}
	   */
	  @Override
	  public List<IAstNode> getInitNodes() {
		  return Collections.singletonList(StaticAstFactory.fromEclipseAstNode(inner.getExpression(),ctx));
	  }

	  /**
	   * {@inheritDoc}
	   */
	  @Override
	  public IAstNode getBody() {
		  return StaticAstFactory.fromEclipseAstNode(inner.getBody(), ctx);
	  }

	  
}
