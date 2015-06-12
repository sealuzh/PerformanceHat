package eu.cloudwave.wp5.feedback.eclipse.costs.core.cache;

import java.util.HashMap;
import java.util.Map;

import eu.cloudwave.wp5.common.dto.ApplicationDto;

public class ApplicationDtoCache {

  private static ApplicationDtoCache INSTANCE;

  private static Map<String, ApplicationDto> cache = new HashMap<String, ApplicationDto>();

  private ApplicationDtoCache() {}

  public static ApplicationDtoCache getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new ApplicationDtoCache();
    }
    return INSTANCE;
  }

  public static void clear() {
    cache.clear();
  }

  public ApplicationDto get(String applicationId) {
    return cache.get(applicationId);
  }

  public void add(String applicationId, ApplicationDto application) {
    cache.put(applicationId, application);
  }

  public ApplicationDto addAndReturn(String applicationId, ApplicationDto application) {
    add(applicationId, application);
    return application;
  }
}
