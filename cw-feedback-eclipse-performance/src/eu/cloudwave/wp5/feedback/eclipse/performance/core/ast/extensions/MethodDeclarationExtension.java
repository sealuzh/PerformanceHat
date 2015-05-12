package eu.cloudwave.wp5.feedback.eclipse.performance.core.ast.extensions;

import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;

/**
 * Provides complementary functionality for {@link MethodDeclaration}'s. It wraps a {@link MethodDeclaration} similar to
 * a decorator but does not implement the same interface.
 */
public class MethodDeclarationExtension extends AbstractMethodExtension<MethodDeclaration> {

  private final MethodDeclaration methodDeclaration;

  public MethodDeclarationExtension(final MethodDeclaration methodDeclaration) {
    this.methodDeclaration = methodDeclaration;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public MethodDeclaration get() {
    return methodDeclaration;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getStartPosition() {
    return methodDeclaration.getName().getStartPosition();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getEndPosition() {
    return getStartPosition() + methodDeclaration.getName().getLength();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IMethodBinding getMethodBinding() {
    return methodDeclaration.resolveBinding().getMethodDeclaration();
  }
}
