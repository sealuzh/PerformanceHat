package eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast;

import java.util.Collections;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.IMethodBinding;

import com.google.common.collect.Lists;

import eu.cloudwave.wp5.common.model.Procedure;
import eu.cloudwave.wp5.common.model.ProcedureKind;
import eu.cloudwave.wp5.common.model.impl.ProcedureImpl;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ProgrammMarkerContext;

public class MethodInvocation extends AMethodRelated<org.eclipse.jdt.core.dom.MethodInvocation> implements Invocation, MethodOccurence {

	public MethodInvocation(org.eclipse.jdt.core.dom.MethodInvocation methodInvocation, ProgrammMarkerContext ctx) {
		super(methodInvocation,ctx);
	}

	  /**
	   * {@inheritDoc}
	   */
	  @Override
	  public int getStartPosition() {
	    final Expression expression = inner.getExpression();
	    return expression != null ? expression.getStartPosition() + expression.getLength() + 1 : inner.getName().getStartPosition();
	  }

	  /**
	   * {@inheritDoc}
	   */
	  @Override
	  public int getEndPosition() {
	    final ASTNode parentExpression = inner.getName().getParent();
	    return parentExpression.getStartPosition() + parentExpression.getLength();
	  }

	  /**
	   * {@inheritDoc}
	   */
	  @Override
	  public IMethodBinding getTargetMethodBinding() {
	    return inner.resolveMethodBinding().getMethodDeclaration();
	  }
	
	@Override
	public ProcedureKind getProcedureKind() {
		return ProcedureKind.METHOD;
	}
	
	//sadly we hae to repeat because defualts cant implement interface other then own
	 public Procedure createCorrelatingProcedure() {
	    final List<String> arguments = Lists.newArrayList(getTargetArguments());
	    //todo: pass in correct, but now this also is always UNKNOWN so at least ok
	    return new ProcedureImpl(getTargetQualifiedClassName(), getTargetMethodName(), getProcedureKind(), arguments, Lists.newArrayList());
	  }
	
	 private List<Double> metrics = null;

	 @Override
		public List<Double> getDoubleTags(String name) {
			if(name.equals("CollectionSize")){
				if (metrics == null) {
					 Procedure procedure = createCorrelatingProcedure();
					 String[] arguments = procedure.getArguments().toArray(new String[procedure.getArguments().size()]);
				     Double averageSize = ctx.getFeedBackClient().collectionSize(ctx.getProject(), procedure.getClassName(), procedure.getName(), arguments, "");
				     metrics = Collections.singletonList(averageSize);
		        }
		        return metrics;
			} else {
				return super.getDoubleTags(name);
			}
		}
	
	 
}
