package eu.cloudwave.wp5.feedback.eclipse.base.infrastructure.template;

import java.util.Map;

/**
 * Provides methods to deal with FreeMarker templates.
 */
public interface TemplateHandler {

  /**
   * Loads the template with the given name and merges it with the given context.
   * 
   * The template has to be located at {@value #TEMPLATE_LOCATION} and have the following format: [templateName].ftl.
   * 
   * @param templateName
   *          the name of the template (without the .ftl file ending)
   * @return the merged content
   */
  public String getContent(String templateName, Map<String, Object> context);

}
