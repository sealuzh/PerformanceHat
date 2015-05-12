package eu.cloudwave.wp5.feedbackhandler.model.impl.converter;

import org.springframework.stereotype.Service;

import eu.cloudwave.wp5.common.model.Procedure;
import eu.cloudwave.wp5.common.model.impl.ProcedureImpl;

/**
 * Implementation of {@link ModelConverter}.
 */
@Service
public class ModelConverterImpl implements ModelConverter {

  /**
   * {@inheritDoc}
   */
  @Override
  public ProcedureImpl convert(final Procedure procedure) {
    return new ProcedureImpl(procedure.getClassName(), procedure.getName(), procedure.getKind(), procedure.getArguments(), procedure.getAnnotations());
  }

}
