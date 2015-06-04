/*******************************************************************************
 * Copyright 2015 Software Evolution and Architecture Lab, University of Zurich
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
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
