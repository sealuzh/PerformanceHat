package eu.cloudwave.wp5.common.model.impl;

import java.util.List;

import eu.cloudwave.wp5.common.model.Annotation;
import eu.cloudwave.wp5.common.model.Procedure;
import eu.cloudwave.wp5.common.model.ProcedureKind;

public class ProcedureImpl extends AbstractProcedure implements Procedure {

  private String className;
  private String name;
  private ProcedureKind kind;
  private List<String> arguments;
  private List<Annotation> annotations;

  public ProcedureImpl(final String className, final String name, final ProcedureKind kind, final List<String> arguments, final List<Annotation> annotations) {
    this.className = className;
    this.name = name;
    this.kind = kind;
    this.arguments = arguments;
    this.annotations = annotations;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getClassName() {
    return className;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<String> getArguments() {
    return arguments;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ProcedureKind getKind() {
    return kind;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Annotation> getAnnotations() {
    return this.annotations;
  }
}
