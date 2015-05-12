package eu.cloudwave.wp5.common.model;

import java.util.Map;

public interface Annotation {

  public Map<String, Object> getMembers();

  public String getName();

  public Object getMember(String key);
}
