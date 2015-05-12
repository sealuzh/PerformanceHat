package eu.cloudwave.wp5.feedback.eclipse.performance.core.ast.extensions;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.MethodInvocation;

/**
 * Provides complementary functionality for {@link MethodInvocation}'s. It wraps a {@link MethodInvocation} similar to a
 * decorator but does not implement the same interface.
 */
public class MethodInvocationExtension extends AbstractMethodExtension<MethodInvocation> {

  private final MethodInvocation methodInvocation;

  public MethodInvocationExtension(final MethodInvocation methodInvocation) {
    this.methodInvocation = methodInvocation;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public MethodInvocation get() {
    return methodInvocation;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getStartPosition() {
    final Expression expression = methodInvocation.getExpression();
    return expression != null ? expression.getStartPosition() + expression.getLength() + 1 : methodInvocation.getName().getStartPosition();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getEndPosition() {
    final ASTNode parentExpression = methodInvocation.getName().getParent();
    return parentExpression.getStartPosition() + parentExpression.getLength();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IMethodBinding getMethodBinding() {
    return methodInvocation.resolveMethodBinding().getMethodDeclaration();
  }

}
