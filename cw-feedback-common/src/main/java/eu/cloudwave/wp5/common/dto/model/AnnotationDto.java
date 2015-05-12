package eu.cloudwave.wp5.common.dto.model;

import java.util.Map;

import com.google.common.collect.Maps;

import eu.cloudwave.wp5.common.model.Annotation;
import eu.cloudwave.wp5.common.model.impl.AnnotationImpl;

public class AnnotationDto extends AnnotationImpl implements Annotation {

  public AnnotationDto() {
    this(null, Maps.newHashMap());
  }

  public AnnotationDto(String name, Map<String, Object> members) {
    super(name, members);
  }
}
