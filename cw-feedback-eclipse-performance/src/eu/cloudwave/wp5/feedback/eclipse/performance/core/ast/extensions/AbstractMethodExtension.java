package eu.cloudwave.wp5.feedback.eclipse.performance.core.ast.extensions;

import java.util.Arrays;
import java.util.List;

import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;

import com.google.common.collect.Lists;

import eu.cloudwave.wp5.common.model.Procedure;
import eu.cloudwave.wp5.common.model.ProcedureKind;
import eu.cloudwave.wp5.common.model.impl.ProcedureImpl;

/**
 * Abstract base functionality for method invocation/declaration extension classes. An extension class wraps an existing
 * object and acts similar to a decorator but does not implement the same interface.
 * 
 * @param <M>
 *          the type of the wrapped object
 */
public abstract class AbstractMethodExtension<M> {

  /**
   * Returns the qualified name of the declaring class of the method.
   * 
   * @return the qualified name of the declaring class of the method
   */
  public String getQualifiedClassName() {
    return getMethodBinding().getDeclaringClass().getQualifiedName();
  }

  /**
   * Returns the name of the method.
   * 
   * @return the name of the method
   */
  public String getMethodName() {
    return getMethodBinding().getName();
  }

  /**
   * Returns an array containing the qualified names of the types of the given arguments.
   * 
   * @param methodBinding
   *          the {@link IMethodBinding}
   * @return an array containing the qualified names of the types of the given arguments
   */
  public String[] getArguments() {
    final ITypeBinding[] argumentTypes = getMethodBinding().getParameterTypes();
    final String[] argumentNames = new String[argumentTypes.length];
    for (int i = 0; i < argumentTypes.length; i++) {
      argumentNames[i] = getArgumentWihtoutGenericPart(argumentTypes[i]);
    }
    return argumentNames;
  }

  private String getArgumentWihtoutGenericPart(final ITypeBinding argumentType) {
    final String argument = argumentType.getQualifiedName();
    final int genericBeginIndex = argument.indexOf("<");
    if (genericBeginIndex != -1) {
      return argument.substring(0, genericBeginIndex);
    }
    return argument;
  }

  /**
   * Checks whether the wrapped object (static procedure information) correlates with the given {@link Procedure}
   * (runtime procedure information).
   * 
   * @param procedure
   *          the {@link Procedure} (runtime data)
   * @return <code>true</code> if the wrapped object (static procedure information) correlates with the given
   *         {@link Procedure}, <code>false</code> otherwise
   */
  public boolean correlatesWith(final Procedure procedure) {
    return correlatesClassName(procedure) && correlatesMethodName(procedure) && correlateArguments(procedure);
  }

  private boolean correlatesClassName(final Procedure procedure) {
    return getQualifiedClassName().equals(procedure.getClassName());
  }

  private boolean correlatesMethodName(final Procedure procedure) {
    return getMethodName().equals(procedure.getName());
  }

  private boolean correlateArguments(final Procedure procedure) {
    return Arrays.equals(getArguments(), procedure.getArguments().toArray());
  }

  public Procedure createCorrelatingProcedure() {
    final List<String> arguments = Lists.newArrayList(getArguments());
    return new ProcedureImpl(getQualifiedClassName(), getMethodName(), getProcedureKind(), arguments, Lists.newArrayList());
  }

  /**
   * Returns the procedure kind of the correlating {@link Procedure}. {@link ProcedureKind#UNKNOWN} is returned as
   * default value. Can be overridden by subclasses to return a specific value.
   * 
   * @return the procedure kind of the correlating {@link Procedure}
   */
  protected ProcedureKind getProcedureKind() {
    return ProcedureKind.UNKNOWN;
  }

  /**
   * Returns the wrapped object.
   * 
   * @return the wrapped object
   */
  public abstract M get();

  /**
   * Returns the corresponding {@link IMethodBinding} of the wrapped object.
   * 
   * @return the corresponding {@link IMethodBinding} of the wrapped object.
   */
  public abstract IMethodBinding getMethodBinding();

  /**
   * Returns the start position (in the respective Java file) of the wrapped object.
   * 
   * @return the start position (in the respective Java file) of the wrapped object.
   */
  public abstract int getStartPosition();

  /**
   * Returns the end position (in the respective Java file) of the wrapped object.
   * 
   * @return the end position (in the respective Java file) of the wrapped object.
   */
  public abstract int getEndPosition();

}
