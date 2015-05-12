package eu.cloudwave.wp5.common.model.impl;

import java.util.Map;

import eu.cloudwave.wp5.common.model.Annotation;

public class AnnotationImpl implements Annotation {

  private String name;

  private Map<String, Object> members;

  public AnnotationImpl(String name, Map<String, Object> members) {
    this.name = name;
    this.members = members;
  }

  @Override
  public Map<String, Object> getMembers() {
    return this.members;
  }

  @Override
  public Object getMember(String key) {
    return this.members.get(key);
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public String toString() {

    String members = "";

    for (Map.Entry<String, Object> entry : getMembers().entrySet()) {
      members += entry.getKey() + ":" + entry.getValue() + ", ";
    }

    return "@" + getName() + "[" + members + "]";
  }
}
