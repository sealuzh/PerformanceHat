package eu.cloudwave.wp5.feedbackhandler.model.impl.converter;

import eu.cloudwave.wp5.common.model.Procedure;
import eu.cloudwave.wp5.common.model.impl.ProcedureImpl;

/**
 * Provides methods to convert any objects implementing one of the common model interfaces (e.g. DTO's) into concrete
 * model entities.
 */
public interface ModelConverter {

  /**
   * Converts any {@link Procedure} to a {@link ProcedureImpl}.
   * 
   * @param procedure
   *          the {@link Procedure} to be converted
   * @return the created {@link ProcedureImpl}.
   */
  public ProcedureImpl convert(Procedure procedure);

}
