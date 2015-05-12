package eu.cloudwave.wp5.common.dto.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.collect.Lists;

import eu.cloudwave.wp5.common.model.Annotation;
import eu.cloudwave.wp5.common.model.Procedure;
import eu.cloudwave.wp5.common.model.ProcedureKind;
import eu.cloudwave.wp5.common.model.impl.ProcedureImpl;

/**
 * A DTO for {@link Procedure}.
 */
public class ProcedureDto extends ProcedureImpl implements Procedure {

  // default constructor is required for jackson deserialization
  public ProcedureDto() {
    this(null, null, null, Lists.newArrayList(), Lists.newArrayList());
  }

  public ProcedureDto(final String className, final String name, final ProcedureKind kind, final List<String> arguments, final List<Annotation> annotations) {
    super(className, name, kind, arguments, annotations);
  }

  /**
   * {@inheritDoc}
   */
  @JsonIgnore
  @Override
  public String getQualifier() {
    return super.getQualifier();
  }

  @Override
  @JsonDeserialize(contentAs = AnnotationDto.class)
  public List<Annotation> getAnnotations() {
    return super.getAnnotations();
  }
}
