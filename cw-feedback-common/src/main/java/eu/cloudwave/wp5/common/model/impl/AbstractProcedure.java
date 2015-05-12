package eu.cloudwave.wp5.common.model.impl;

import eu.cloudwave.wp5.common.model.Procedure;
import eu.cloudwave.wp5.common.util.HashCodes;
import eu.cloudwave.wp5.common.util.Joiners;

/**
 * Abstract base implementation of {@link Procedure}.
 */
public abstract class AbstractProcedure implements Procedure {

  private static final String EMPTY = "";
  private static final String ARGUMENTS_SEPARATOR = ", ";
  private static final String SEPARATOR = ".";
  private static final String OPENING_BRACKET = "(";
  private static final String CLOSING_BRACKET = ")";

  /**
   * {@inheritDoc}
   */
  @Override
  public String getQualifier() {
    return getClassName() + SEPARATOR + getName() + OPENING_BRACKET + argumentsToString() + CLOSING_BRACKET;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final String argumentsToString() {
    String arguments = EMPTY;
    for (int i = 0; i < getArguments().size(); i++) {
      arguments += getArguments().get(i);
      if (i + 1 < getArguments().size()) {
        arguments += ARGUMENTS_SEPARATOR;
      }
    }
    return arguments;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    String annotations = getAnnotations().isEmpty() ? "" : Joiners.onComma(getAnnotations().toArray()) + " / ";
    return annotations + getQualifier();
  }

  /**
   * {@inheritDoc}
   */
  // TODO: add annotations into comparison of equality
  @Override
  public boolean equals(final Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj instanceof Procedure) {
      final Procedure procedure = (Procedure) obj;
      if (procedure.getClassName().equals(this.getClassName()) && procedure.getName().equals(this.getName()) && procedure.getArguments().equals(this.getArguments())) {
        return true;
      }
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    return HashCodes.combinedHashCode(getClassName().hashCode(), getName().hashCode(), getArguments().hashCode());
  }

}
