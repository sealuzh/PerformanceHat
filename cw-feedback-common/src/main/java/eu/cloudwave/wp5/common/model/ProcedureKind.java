package eu.cloudwave.wp5.common.model;

/**
 * Represents the type of a procedure. A procedure can either be a (non-static) method, a static method or a
 * constructor.
 */
public enum ProcedureKind {

  METHOD,
  STATIC_METHOD,
  CONSTRUCTOR,
  UNKNOWN;

  public static ProcedureKind of(final String textualKind) {
    for (final ProcedureKind kind : ProcedureKind.values()) {
      if (textualKind.toUpperCase().equals(kind.toString())) {
        return kind;
      }
    }
    return UNKNOWN;
  }

}
