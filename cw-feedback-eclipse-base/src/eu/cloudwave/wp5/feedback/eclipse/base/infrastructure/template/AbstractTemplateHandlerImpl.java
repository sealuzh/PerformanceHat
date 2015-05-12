package eu.cloudwave.wp5.feedback.eclipse.base.infrastructure.template;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Abstract implementation of {@link TemplateHandler}.
 */
public abstract class AbstractTemplateHandlerImpl implements TemplateHandler {

  private static final String FTL = ".ftl";
  private static final String ERROR = "<ERROR INITIALIZING HOVER CONTENT>";
  protected static final String SLASH = "/";
  protected static final String TEMPLATE_LOCATION = "OSGI-INF/l10n/templates/";

  protected Configuration freemarkerConfig;

  /**
   * Freemarker {@link Configuration} freemarkerConfig of parent has to be initialized and template paths has to be set
   * according to {@see http://freemarker.org/docs/pgui_config_templateloading.html}
   */
  protected abstract void initFreemarkerConfig();

  /**
   * {@inheritDoc}
   */
  @Override
  public String getContent(final String templateName, final Map<String, Object> context) {
    try {
      if (freemarkerConfig == null) {
        initFreemarkerConfig();
      }
      final Template template = freemarkerConfig.getTemplate(TEMPLATE_LOCATION + templateName + FTL);
      final StringWriter writer = new StringWriter();
      template.process(context, writer);
      return writer.toString();
    }
    catch (final IOException | TemplateException e) {
      e.printStackTrace();
    }
    return ERROR;
  }

}
