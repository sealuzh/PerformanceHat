package eu.cloudwave.wp5.feedbackhandler.messages;

import java.util.ResourceBundle;

public class Config {

  public static final String NEW_RELIC__URL = getMessage("new_relic.url");

  private static String getMessage(final String key) {
    return ResourceBundle.getBundle("config").getString(key);
  }

}
