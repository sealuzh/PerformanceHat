package eu.cloudwave.wp5.feedback.eclipse.performance.core.builders.participants;

import eu.cloudwave.wp5.feedback.eclipse.base.infrastructure.template.AbstractTemplateHandlerImpl;
import eu.cloudwave.wp5.feedback.eclipse.base.infrastructure.template.TemplateHandler;
import freemarker.template.Configuration;

/**
 * Implementation of {@link TemplateHandler}.
 */
public class TemplateHandlerImpl extends AbstractTemplateHandlerImpl {

  /**
   * {@inheritDoc}
   */
  @Override
  protected void initFreemarkerConfig() {
    this.freemarkerConfig = new Configuration();
    freemarkerConfig.setClassForTemplateLoading(this.getClass(), SLASH);
  }
}
