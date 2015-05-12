package eu.cloudwave.wp5.feedback.eclipse.performance.core.ast.extensions;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;

import eu.cloudwave.wp5.common.model.ProcedureKind;

/**
 * Provides complementary functionality for {@link ClassInstanceCreation}'s (i.e. constructors). It wraps a
 * {@link ClassInstanceCreation} similar to a decorator but does not implement the same interface.
 */
public class ClassInstanceCreationExtension extends AbstractMethodExtension<ClassInstanceCreation> {

  private static final String EMPTY = "";
  private static final String INIT = "<init>";

  private final ClassInstanceCreation classInstanceCreation;

  public ClassInstanceCreationExtension(final ClassInstanceCreation classInstanceCreation) {
    this.classInstanceCreation = classInstanceCreation;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ClassInstanceCreation get() {
    return classInstanceCreation;
  }

  @Override
  public String getQualifiedClassName() {
    final String regularName = super.getQualifiedClassName();
    // if the name is empty, this is the constructor of anonymous inner class
    // Therefore, no name exists and the name of the implementing interface or the extending class ha to be considered.
    if (regularName.equals(EMPTY)) {
      final ITypeBinding[] interfaces = getMethodBinding().getDeclaringClass().getInterfaces();
      if (interfaces.length > 0) {
        return interfaces[0].getQualifiedName();
      }
      return getMethodBinding().getDeclaringClass().getSuperclass().getQualifiedName();
    }
    return regularName;
  }

  @Override
  public String getMethodName() {
    final String regularName = super.getMethodName();
    if (regularName.equals(EMPTY)) {
      return INIT;
    }
    return regularName;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IMethodBinding getMethodBinding() {
    return classInstanceCreation.resolveConstructorBinding().getMethodDeclaration();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getStartPosition() {
    final Expression expression = classInstanceCreation.getExpression();
    return expression != null ? expression.getStartPosition() + expression.getLength() + 1 : classInstanceCreation.getType().getStartPosition();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getEndPosition() {
    final ASTNode parentExpression = classInstanceCreation.getType().getParent();
    return parentExpression.getStartPosition() + parentExpression.getLength();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected ProcedureKind getProcedureKind() {
    return ProcedureKind.CONSTRUCTOR;
  }

}
