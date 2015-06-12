package eu.cloudwave.wp5.feedback.eclipse.costs.ui.hovers;

import java.util.Map;

import com.google.common.collect.Maps;

public class ContextBuilder {

  protected Map<String, Object> context;

  /**
   * Private Contructor, static init method should be used instead
   */
  protected ContextBuilder() {
    this.context = Maps.newHashMap();
  }

  /**
   * Creates a new builder that can be used in a fluent way
   * 
   * @return {@link ContextBuilder}
   */
  public static ContextBuilder init() {
    return new ContextBuilder();
  }

  /**
   * Adds a key/value pair to the context and returns the builder in a fluent style
   * 
   * @param key
   * @param value
   * 
   * @return {@link ContextBuilder}
   */
  public ContextBuilder add(String key, Object value) {
    this.context.put(key, value);
    return this;
  }

  /**
   * Add the alternative object to the context if the first object is null
   * 
   * @param key
   * @param value
   * @param alternative
   * 
   * @return {@link ContextBuilder}
   */
  public ContextBuilder addIfNotNull(String key, Object value, Object alternative) {
    if (value != null) {
      this.context.put(key, value);
    }
    else if (alternative != null) {
      this.context.put(key, alternative);
    }
    return this;
  }

  /**
   * Add object to context if it is not null
   * 
   * @param key
   * @param value
   * 
   * @return {@link ContextBuilder}
   */
  public ContextBuilder addIfNotNull(String key, Object value) {
    return addIfNotNull(key, value, null);
  }

  /**
   * Builds context map for {@link TemplateHandler}
   * 
   * @return context
   */
  public Map<String, Object> build() {
    return context;
  }
}
