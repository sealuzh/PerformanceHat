package eu.cloudwave.wp5.common.model;

import java.util.List;

/**
 * Holds the information about a procedure. A procedure is either a method (static or non-static) or a constructor.
 */
public interface Procedure {

  /**
   * Returns the qualified name of the class of the procedure.
   * 
   * @return
   */
  public String getClassName();

  /**
   * Returns the name of the procedure.
   * 
   * @return the name of the procedure
   */
  public String getName();

  /**
   * Returns a {@link List} containing the qualified class names of the arguments.
   * 
   * @return a {@link List} containing the qualified class names of the arguments
   */
  public List<String> getArguments();

  /**
   * Returns the unique qualifier of the current {@link Procedure} which is the concatenation of the qualified class
   * name and the complete method signature.
   * 
   * @return the unique qualifier of the current {@link Procedure}
   */
  public String getQualifier();

  /**
   * Returns the kind of the procedure.
   * 
   * @return the kind of the procedure
   */
  public ProcedureKind getKind();

  /**
   * Returns all parameters as concatenated {@link String}.
   * 
   * @return all parameters as concatenated {@link String}.
   */
  public String argumentsToString();

  /**
   * Returns a {@link List} of {@link Annotation}
   * 
   * @return a {@link List} of {@link Annotation}.
   */
  public List<Annotation> getAnnotations();

}
